// Pathing
// _______
// src/features/event-page/event-sidebar/EventSidebar.jsx

import DashboardComponent from '@/shared/components/dashboard/DashboardComponent'
import InputText from '@/shared/components/input-text/InputText'
import {useEventSidebar} from './EventSidebar.hooks'
import styles from './EventSidebar.module.css'

const EventSidebar = () => {
    
    const {quickAction, tip} = useEventSidebar()

    return (
        <aside className={styles.sidebar}>
            <DashboardComponent columns={1} rows={1} className={styles.quickAction}>
                <strong>{quickAction.title}</strong>
                <span>{quickAction.description}</span>
                <div className={styles.search}>
                    <InputText placeholder="Søg..." disabled aria-label="Find et event" />
                </div>
            </DashboardComponent>
            <DashboardComponent columns={1} rows={2} className={styles.tip}>
                <strong>{tip.title}</strong>
                <span>{tip.description}</span>
            </DashboardComponent>
        </aside>
    )
}

export default EventSidebar