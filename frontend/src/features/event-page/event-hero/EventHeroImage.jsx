// Pathing
// _______
// src/features/event-page/event-hero/EventHeroImage.jsx

import DashboardComponent from '@/shared/components/dashboard/DashboardComponent'
import styles from './EventHeroImage.module.css'

const EventHeroImage = () => (
    <DashboardComponent className={styles.image} columns={2} rows={1} aria-hidden="true">
        <div />
    </DashboardComponent>
)

export default EventHeroImage