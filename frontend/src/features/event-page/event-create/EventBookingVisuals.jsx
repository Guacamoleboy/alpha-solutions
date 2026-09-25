// Pathing
// _______
// src/features/event-page/event-create/EventBookingVisuals.jsx

import DashboardComponent from '@/shared/components/dashboard/DashboardComponent'
import {useEventBookingVisuals} from './EventBookingVisuals.hooks'
import styles from './EventBookingVisuals.module.css'

const EventBookingVisuals = () => {
    
    const {columns, rows} = useEventBookingVisuals()

    return (
        <DashboardComponent columns={columns} rows={rows} className={styles.visuals}>
            <span className={styles.comingSoon}>
                Kommer snart <i className="fa fa-lock" aria-hidden="true" />
            </span>
        </DashboardComponent>
    )
}

export default EventBookingVisuals