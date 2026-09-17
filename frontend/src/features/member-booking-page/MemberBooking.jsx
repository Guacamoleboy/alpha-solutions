// Pathing
// _______
// src/features/member-booking-page/MemberBooking.jsx

import { useEffect, useState } from 'react'
import QuickNavigation from '@/shared/components/quick-navigation/QuickNavigation'
import CourtBooking from './court-booking/CourtBooking'
import ActiveBookings from './active-bookings/ActiveBookings'
import { getMyBookings } from '@/api/endpoints/booking'
import PreviousBookings from './previous-bookings/PreviousBookings'

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

    // State
    const [bookings, setBookings] = useState([])
    const [activeSection, setActiveSection] = useState('court-booking')

    // Fetch bookings
    useEffect(() => {

        const loadBookings = async () => {
            try {
                const response = await getMyBookings()

                console.log('My bookings response:', response)
                console.log('My bookings data:', response.data)

                setBookings(response.data)

            } catch (error) {
                console.error(
                    'Failed to load bookings:',
                    error
                )
            }
        }

        loadBookings()

    }, [])

    // Handle navigation
    const handleNavigation = (targetId) => {
        setActiveSection(targetId)
    }

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