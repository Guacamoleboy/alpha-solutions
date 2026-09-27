// Pathing
// _______
// src/features/owner-events/OwnerEventDashboard.jsx

import {Link} from 'react-router-dom'
import DashboardComponent from '@/shared/components/dashboard/DashboardComponent'
import InputText from '@/shared/components/input-text/InputText'
import {useOwnerEventDashboard, useOwnerEventDetails} from './OwnerEventDashboard.hooks'
import EventHeroImage from '@/features/event-page/event-hero/EventHeroImage'
import styles from './OwnerEventDashboard.module.css'

// ------------------------------------------------------------------------------------------------------

const statusLabels = {PENDING: 'Afventer', ACCEPTED: 'Bekræftet', PASSED: 'Afholdt', DENIED: 'Afvist'}
const statusFilters = [
    {key: 'ALL', label: 'Alle'},
    {key: 'ACCEPTED', label: 'Aktive'},
    {key: 'PENDING', label: 'Afventer'},
    {key: 'PASSED', label: 'Afholdt'},
    {key: 'DENIED', label: 'Afvist'},
]

// ------------------------------------------------------------------------------------------------------

const OwnerEventDashboard = () => {

    const {
        events, filter, setFilter, selectedEvent, selectEvent, loading,
        replaceEvent, activeStatus, setActiveStatus,
    } = useOwnerEventDashboard()

    return (
        <>
            <DashboardComponent className={styles.header} columns={2} rows={1}>
                <div className={styles.heading}>
                    <div className={styles.heroCopy}>
                        <h1 className="heroTitle">Eventoverblik</h1>
                        <p>Gennemse forespørgsler, bekræft events og administrér banereservationer.</p>
                        <Link className={styles.createLink} to="/dashboard/events/opret">Opret event</Link>
                    </div>
                </div>
            </DashboardComponent>
            <EventHeroImage />

            <DashboardComponent className={styles.list} columns={3} rows={2}>
                <div className={styles.listHead}>
                    <strong>Events</strong>
                    <span>{events.length} resultater</span>
                </div>
                <div className={styles.items}>
                    {loading && <p>Henter events…</p>}
                    {!loading && events.length === 0 && <p>Ingen events matcher filtreringen.</p>}
                    {events.map((event) => {
                        const isExpanded = selectedEvent?.id === event.id
                        const startTime = new Date(event.start_time)
                        const endTime = new Date(event.end_time)
                        return (
                            <article className={styles.eventEntry} key={event.id}>
                                <button
                                    aria-expanded={isExpanded}
                                    className={`${styles.eventRow} ${isExpanded ? styles.selected : ''}`}
                                    onClick={() => selectEvent(isExpanded ? null : event)}
                                    type="button"
                                >
                                    <strong>{event.name}</strong>
                                    <span>{startTime.toLocaleDateString('da-DK')}</span>
                                    <span>{startTime.toLocaleTimeString('da-DK', {hour: '2-digit', minute: '2-digit'})}–{endTime.toLocaleTimeString('da-DK', {hour: '2-digit', minute: '2-digit'})}</span>
                                    <span className={`${styles.status} ${styles[event.status?.toLowerCase()]}`}>{statusLabels[event.status] || event.status}</span>
                                    <i className={`fa ${isExpanded ? 'fa-chevron-up' : 'fa-chevron-down'}`} aria-hidden="true" />
                                </button>
                                {isExpanded && <EventDetails event={event} onEventUpdated={replaceEvent} />}
                            </article>
                        )
                    })}
                </div>
            </DashboardComponent>

            <aside className={styles.sidebar}>
                <DashboardComponent className={styles.filter} columns={1} rows={1}>
                    <strong>Filtrér efter…</strong>
                    <InputText aria-label="Filtrér events" onChange={(event) => setFilter(event.target.value)} placeholder="Titel eller email" value={filter} />
                    <div className={styles.statusFilters}>
                        {statusFilters.map((tab) => (
                            <button
                                className={`${styles.tab} ${activeStatus === tab.key ? styles.activeTab : ''}`}
                                key={tab.key}
                                onClick={() => setActiveStatus(tab.key)}
                                type="button"
                            >
                                {tab.label}
                            </button>
                        ))}
                    </div>
                </DashboardComponent>
                <DashboardComponent className={styles.locked} columns={1} rows={1}>
                    <div className={styles.lockedText}>
                        <strong>Kommer snart</strong>
                        <i className="fa fa-lock" aria-hidden="true" />
                    </div>
                </DashboardComponent>
            </aside>
        </>
    )
}

// ------------------------------------------------------------------------------------------------------

const EventDetails = ({event, onEventUpdated}) => {

    const {
        reservations, courts, detailsLoading, form, isDirty, organizerInput, setOrganizerInput,
        selectedOrganizers, setSelectedOrganizers, handleAddOrganizer,
        saving, updateEvent, closeEvent,
    } = useOwnerEventDetails(event, onEventUpdated)

    return (
        <form className={styles.details} onSubmit={updateEvent}>
            <InputText label="Titel" {...form.registerField('name')} />
            <div className={styles.timeFields}>
                <InputText label="Start" type="datetime-local" {...form.registerField('startTime')} />
                <InputText label="Slut" type="datetime-local" {...form.registerField('endTime')} />
            </div>
            <div className={styles.timeFields}>
                <InputText label="Antal gæster" type="number" min="1" {...form.registerField('guestCount')} />
                <p className={styles.courtAllocation}>Baner tildeles automatisk (4 gæster pr. bane).</p>
            </div>
            <InputText label="Arrangør email" type="email" required {...form.registerField('organizerEmail')} />
            <div className={styles.organizerControls}>
                <InputText
                    label="Tilføj medarrangør via email"
                    onChange={(change) => setOrganizerInput(change.target.value)}
                    placeholder="medlem@example.dk"
                    type="email"
                    value={organizerInput}
                />
                <button className={styles.addOrganizer} onClick={handleAddOrganizer} type="button">Tilføj</button>
            </div>
            <div className={styles.organizerList}>
                {selectedOrganizers.map((organizer) => (
                    <span className={styles.organizerChip} key={organizer.memberId}>
                        {organizer.email}
                        <button
                            aria-label={`Fjern ${organizer.email}`}
                            onClick={() => setSelectedOrganizers((current) => current.filter((item) => item.memberId !== organizer.memberId))}
                            type="button"
                        >×</button>
                    </span>
                ))}
            </div>
            <label className={styles.equipmentControl}>
                <input
                    checked={Boolean(form.values.equipmentRequired)}
                    onChange={(change) => form.setValues((current) => ({...current, equipmentRequired: change.target.checked}))}
                    type="checkbox"
                />
                <span className={`${styles.equipmentBox} ${form.values.equipmentRequired ? styles.selected : ''}`}>
                    {form.values.equipmentRequired && <i className="fa fa-check-square-o" aria-hidden="true" />}
                </span>
                <span>Udstyr nødvendigt</span>
            </label>
            <InputText label="Eventkode" {...form.registerField('eventCode')} />
            {event.status === 'ACCEPTED' && <p className={styles.courtAllocation}>
                Tildelte baner: {reservations.map((reservation) => courts.find((court) => court.id === reservation.court_id)?.name || reservation.court_id).join(', ') || 'afventer godkendelse'}
            </p>}
            <div className={styles.actions}>
                <button className={styles.cancel} disabled={saving || detailsLoading} onClick={closeEvent} type="button">Afvis event</button>
                <button className={styles.save} disabled={!isDirty || saving || detailsLoading} type="submit">{saving ? 'Gemmer…' : 'Godkend'}</button>
            </div>
        </form>
    )

}

export default OwnerEventDashboard