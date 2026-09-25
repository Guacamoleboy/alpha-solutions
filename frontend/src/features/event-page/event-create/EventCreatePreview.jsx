// Pathing
// _______
// src/features/event-page/event-create/EventCreatePreview.jsx

import DashboardComponent from '@/shared/components/dashboard/DashboardComponent'
import {useEventPreview} from './EventCreatePreview.hooks'
import styles from './EventCreatePreview.module.css'

const EventCreatePreview = ({values}) => {
    
    const {date, details, organizers, title} = useEventPreview(values)

    return (
        <aside className={styles.sidebar}>

            {/* COMPONENT 1 */}
            <DashboardComponent columns={1} rows={1} className={styles.organizers}>
                <strong>Co-arrangører</strong>
                {organizers.length > 0 ? (
                    <ul>{organizers.map((organizer) => <li key={organizer}>{organizer}</li>)}</ul>
                ) : (
                    <span>Ingen co-arrangører tilføjet.</span>
                )}
            </DashboardComponent>
            
            {/* COMPONENT 2 */}
            <DashboardComponent
                backgroundImage="/images/shared/background2.jpg"
                columns={1}
                rows={2}
                className={styles.preview}
            >
                <div className={styles.previewContent}>
                    {details.map((detail) => <span key={detail}>{detail}</span>)}
                    <span>{date}</span>
                    <strong>{title}</strong>
                </div>
            </DashboardComponent>

        </aside>
    )
}

export default EventCreatePreview