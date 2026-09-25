// Pathing
// _______
// src/app/pages/EventPage.jsx

import {EventHero} from '@/features/event-page'
import {EventHistory, EventSidebar} from '@/features/event-page'

const EventPage = () => (
    <div className="memberPage">
        <EventHero />
        <EventHistory />
        <EventSidebar />
    </div>
)

export default EventPage