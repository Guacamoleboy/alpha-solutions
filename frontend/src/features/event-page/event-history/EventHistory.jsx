// Pathing
// _______
// src/features/event-page/event-history/EventHistory.jsx

import {Link} from 'react-router-dom'
import DashboardComponent from '@/shared/components/dashboard/DashboardComponent'
import {useEventHistory} from './EventHistory.hooks'
import styles from './EventHistory.module.css'

// ------------------------------------------------------------------------------------------------------

const statusLabels = {
    PENDING: 'AFVENTER',
    ACCEPTED: 'AKTIVE',
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
                        </div>
                        {events.map((event) => {
                            const startTime = event.start_time ? new Date(event.start_time) : null
                            return (
                                <article key={event.id} className={styles.eventItem}>
                                    <strong>{event.name}</strong>
                                    <span>{startTime?.toLocaleDateString('da-DK') || ''}</span>
                                    <span>{startTime?.toLocaleTimeString('da-DK', {hour: '2-digit', minute: '2-digit'}) || ''}</span>
                                    <span className={`${styles.status} ${styles[statusClasses[event.status]]}`}>
                                        {statusLabels[event.status] || event.status}
                                    </span>
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