// Pathing
// _______
// src/features/court-closures/CourtClosures.jsx

import DashboardComponent from '@/shared/components/dashboard/DashboardComponent'
import InputText from '@/shared/components/input-text/InputText'
import Select from '@/shared/components/select/Select'
import Submit from '@/shared/components/submit/Submit'
import {useCourtClosures} from './CourtClosures.hooks'
import styles from './CourtClosures.module.css'

// ------------------------------------------------------------------------------------------------------

const reasonLabels = {
    MAINTENANCE: 'Vedligeholdelse',
    CLEANING: 'Rensning',
    EVENT: 'Arrangement',
    OTHER: 'Andet',
}

// ------------------------------------------------------------------------------------------------------

const CourtClosures = () => {
    const {
        busy,
        courtId,
        courts,
        closures,
        date,
        endTime,
        error,
        endTimes,
        handleCreate,
        handleDelete,
        handleStartTimeChange,
        loading,
        operatingHour,
        reason,
        setReason,
        setCourtId,
        setDate,
        setEndTime,
        setStartTime,
        startTimes,
        startTime,
        today,
    } = useCourtClosures()

    const facilityOpen = operatingHour && !operatingHour.closed
    const courtOptions = [
        {value: '', label: 'Vælg bane'},
        ...courts.map((court) => ({value: court.id, label: court.name})),
    ]
    const reasonOptions = [
        {value: '', label: 'Vælg beskrivelse'},
        {value: 'MAINTENANCE', label: 'Vedligeholdelse'},
        {value: 'CLEANING', label: 'Rensning'},
        {value: 'EVENT', label: 'Arrangement'},
        {value: 'OTHER', label: 'Andet'},
    ]

    return (
        <>
            <DashboardComponent className={styles.info} columns={1} rows={1}>
                <div className={styles.infoContent}>
                    <h1>Luk en bane</h1>
                    <p>Bloker en bane i et bestemt tidsrum.</p>
                </div>
            </DashboardComponent>

            <DashboardComponent className={styles.formCard} columns={3} rows={2}>
                <form className={styles.form} onSubmit={handleCreate}>
                    <div className={styles.fields}>
                        <Select
                            label="Bane"
                            onChange={(event) => setCourtId(event.target.value)}
                            options={courtOptions}
                            required
                            value={courtId}
                        />
                        <InputText
                            label="Dato"
                            min={today}
                            onChange={(event) => {
                                setDate(event.target.value)
                                setStartTime('')
                                setEndTime('')
                            }}
                            required
                            type="date"
                            value={date}
                        />
                        <Select
                            label="Beskrivelse"
                            onChange={(event) => setReason(event.target.value)}
                            options={reasonOptions}
                            required
                            value={reason}
                        />
                        <Select
                            disabled={loading || !facilityOpen}
                            label="Fra"
                            onChange={handleStartTimeChange}
                            options={[{value: '', label: 'Vælg starttid'}, ...startTimes]}
                            required
                            value={startTime}
                        />
                        <Select
                            disabled={loading || !facilityOpen || !startTime}
                            label="Til"
                            options={[{value: '', label: 'Vælg sluttid'}, ...endTimes]}
                            onChange={(event) => setEndTime(event.target.value)}
                            required
                            value={endTime}
                        />
                        <Submit label={busy ? 'Gemmer…' : 'Luk banen'} size="m" disabled={busy || !facilityOpen || loading} />
                    </div>

                    {date && !operatingHour && <p className={styles.formMessage}>Der er ingen åbningstider registreret for denne dag.</p>}
                    {date && operatingHour?.closed && <p className={styles.formMessage}>Vi holder lukket denne dag.</p>}
                </form>
            </DashboardComponent>

            <DashboardComponent className={styles.history} columns={1} rows={2}>
                <div className={styles.historyHeading}>
                    <div>
                        <h2>Planlagte lukninger</h2>
                        <p>Se hvilke baner der er blokeret og hvornår.</p>
                    </div>
                    <span className={styles.count}>{closures.length}</span>
                </div>

                <div className={styles.historyContent}>
                    {loading && <p>Henter lukninger…</p>}
                    {error && <p role="alert">Lukningerne kunne ikke hentes.</p>}
                    {!loading && !error && closures.length === 0 && (
                        <div className={styles.emptyState}>
                            <i className="fa fa-calendar-o" aria-hidden="true" />
                            <p>Der er ingen planlagte lukninger.</p>
                        </div>
                    )}
                    {closures.length > 0 && (
                        <ul className={styles.closureList}>
                            {closures.map((closure) => (
                                <li className={styles.closure} key={closure.id}>
                                    <strong>{closure.court_name}</strong>
                                    <span>{new Date(closure.start_time).toLocaleDateString('da-DK', {dateStyle: 'medium'})}</span>
                                    <span>
                                        {new Date(closure.start_time).toLocaleTimeString('da-DK', {hour: '2-digit', minute: '2-digit'})}
                                        {' – '}
                                        {new Date(closure.end_time).toLocaleTimeString('da-DK', {hour: '2-digit', minute: '2-digit'})}
                                    </span>
                                    <span>{reasonLabels[closure.reason] || closure.reason || 'Ikke angivet'}</span>
                                    <button type="button" onClick={() => handleDelete(closure)}>Fjern</button>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
            </DashboardComponent>
        </>
    )
}

export default CourtClosures