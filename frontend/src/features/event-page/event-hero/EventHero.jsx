// Pathing
// _______
// src/features/event-page/event-hero/EventHero.jsx

import DashboardComponent from '@/shared/components/dashboard/DashboardComponent'
import {useEventHero} from './EventHero.hooks'
import styles from './EventHero.module.css'

const EventHero = () => {
    
    const {columns, rows, upcomingEventText} = useEventHero()

    return (

        // COMPONENT 1
        <DashboardComponent className={styles.hero} columns={columns} rows={rows}>
            <div className={styles.copy}>
                <h1 className="heroTitle">Events hos Alpha Pickleball</h1>
                <p className={styles.content}>Opret og deltag i events sammen med andre medlemmer.</p>
                <p className={styles.upcoming}>
                    <span>Næste kommende event</span>
                    <span>{upcomingEventText}</span>
                </p>
            </div>
            <div className={styles.image} aria-hidden="true" />
        </DashboardComponent>
    )
}

export default EventHero