// Pathing
// _______
// src/features/member-booking-page/MemberBooking.hooks.js

import { useEffect, useState } from 'react'
import { useSearchParams } from 'react-router-dom'
import { getMyBookings } from '@/api/endpoints/booking'

export const useMemberBooking = () => {

    const [bookings, setBookings] = useState([])
    const [searchParams] = useSearchParams()
    const initialSection = searchParams.get('section') === 'history'
        ? 'previous-bookings'
        : 'court-booking'
    const [activeSection, setActiveSection] = useState(initialSection)

    useEffect(() => {
        const loadBookings = async () => {
            try {
                const response = await getMyBookings()
                setBookings(response.data || [])
            } catch (error) {
                console.error('Failed to load bookings:', error)
            }
        }

        loadBookings()
    }, [])

    const handleNavigation = (targetId) => {
        setActiveSection(targetId)
    }

    return {
        activeSection,
        bookings,
        handleNavigation,
    }
}