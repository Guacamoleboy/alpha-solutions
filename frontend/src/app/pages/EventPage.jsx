// Pathing
// _______
// src/app/pages/EventPage.jsx

import {EventHero, EventHeroImage} from '@/features/event-page'
import {EventHistory, EventSidebar} from '@/features/event-page'

const EventPage = () => (
    <div className="memberPage">
        <EventHero />
        <EventHeroImage />
        <EventHistory />
        <EventSidebar />
    </div>
)

export default EventPage