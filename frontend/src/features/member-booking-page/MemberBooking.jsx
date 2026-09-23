// Pathing
// _______
// src/features/member-booking-page/MemberBooking.jsx

import QuickNavigation from '@/shared/components/quick-navigation/QuickNavigation'
import CourtBooking from './court-booking/CourtBooking'
import ActiveBookings from './active-bookings/ActiveBookings'
import PreviousBookings from './previous-bookings/PreviousBookings'
import { useMemberBooking } from './MemberBooking.hooks'

const navigationItems = [
    {
        label: 'Book bane',
        targetId: 'court-booking',
    },
    {
        label: 'Historik',
        targetId: 'previous-bookings',
    },
    {
        label: 'Aktive bookings',
        targetId: 'active-bookings',
    },
]

const MemberBooking = () => {

    const { activeSection, bookings, handleNavigation } = useMemberBooking()

    return (
        <>
            <QuickNavigation
                items={navigationItems}
                onNavigate={handleNavigation}
            />

            {activeSection === 'court-booking' && (
                <CourtBooking
                    targetId="court-booking"
                />
            )}

            {activeSection === 'active-bookings' && (
                <ActiveBookings
                    targetId="active-bookings"
                    bookings={bookings}
                />
            )}

            {activeSection === 'previous-bookings' && (
                <PreviousBookings
                    targetId="previous-bookings"
                    bookings={bookings}
                />
            )}
        </>
    )
}

export default MemberBooking