// Pathing
// _______
// src/features/event-page/event-history/EventHistory.jsx

import {Link} from 'react-router-dom'
import {useState} from 'react'
import DashboardComponent from '@/shared/components/dashboard/DashboardComponent'
import {useEventHistory} from './EventHistory.hooks'
import styles from './EventHistory.module.css'

// ------------------------------------------------------------------------------------------------------

const statusLabels = {
    PENDING: 'AFVENTER',
    ACCEPTED: 'AKTIV',
    PASSED: 'HOLDT',
    DENIED: 'ANNULERET',
}

// ------------------------------------------------------------------------------------------------------

const statusClasses = {
    PENDING: 'pending',
    ACCEPTED: 'active',
    PASSED: 'held',
    DENIED: 'cancelled',
}

// ------------------------------------------------------------------------------------------------------

const EventHistory = () => {

    const {activeTab, events, setActiveTab, tabs} = useEventHistory()
    const [expandedEventId, setExpandedEventId] = useState(null)

    return (

        // COMPONENT 1
        <DashboardComponent
            className={styles.history}
            columns={3}
            rows={2}
        >
            <div className={styles.toolbar}>
                <div className={styles.tabs}>
                    {tabs.map((tab) => (
                        <button
                            key={tab.key}
                            type="button"
                            className={`${styles.tab} ${activeTab === tab.key ? styles.active : ''}`}
                            onClick={() => setActiveTab(tab.key)}
                        >
                            {tab.label}
                        </button>
                    ))}
                </div>
                <Link className={styles.createLink} to="/member/events/opret">
                    OPRET EVENT
                </Link>
            </div>

            <div className={styles.eventList}>
                <hr />
                {events.length > 0 ? (
                    <>
                        <div className={styles.eventHeader}>
                            <span>Titel</span>
                            <span>Dato</span>
                            <span>Tid</span>
                            <span>Status</span>
                            <span aria-hidden="true" />
                        </div>
                        {events.map((event) => {
                            const startTime = event.start_time ? new Date(event.start_time) : null
                            const endTime = event.end_time ? new Date(event.end_time) : null
                            const canExpand = event.status === 'ACCEPTED'
                            const isExpanded = expandedEventId === event.id
                            const eventContent = <>
                                <strong>{event.name}</strong>
                                <span>{startTime?.toLocaleDateString('da-DK') || ''}</span>
                                <span>
                                    {startTime?.toLocaleTimeString('da-DK', {hour: '2-digit', minute: '2-digit'}) || ''}
                                    {endTime && ` – ${endTime.toLocaleTimeString('da-DK', {hour: '2-digit', minute: '2-digit'})}`}
                                </span>
                                <span className={`${styles.status} ${styles[statusClasses[event.status]]}`}>
                                    {statusLabels[event.status] || event.status}
                                </span>
                                {canExpand && <i className={`fa ${isExpanded ? 'fa-chevron-up' : 'fa-chevron-down'}`} aria-hidden="true" />}
                            </>
                            return (
                                <article key={event.id} className={styles.eventEntry}>
                                    {canExpand ? (
                                        <button
                                            aria-expanded={isExpanded}
                                            className={`${styles.eventItem} ${styles.eventButton}`}
                                            onClick={() => setExpandedEventId(isExpanded ? null : event.id)}
                                            type="button"
                                        >{eventContent}</button>
                                    ) : <div className={styles.eventItem}>{eventContent}</div>}
                                    {canExpand && isExpanded && (
                                        <div className={styles.courtDetails}>
                                            <strong>Tildelte baner</strong>
                                            {event.courts.length > 0
                                                ? <span>{event.courts.join(', ')}</span>
                                                : <span>Ingen baner er registreret endnu.</span>}
                                        </div>
                                    )}
                                </article>
                            )
                        })}
                    </>
                ) : (
                    <p className={styles.emptyState}>Ingen events i denne kategori endnu.</p>
                )}
            </div>
        </DashboardComponent>
    )
}

export default EventHistory