// Pathing
// _______
// src/features/member-page/hero/Hero.hooks.js

import { useEffect, useState } from 'react'
import { getMyBookings } from '@/api/endpoints/booking'

export const useLatestBooking = () => {

    const [latestBookingDate, setLatestBookingDate] = useState(null)
    const [bookingsLoaded, setBookingsLoaded] = useState(false)

    useEffect(() => {
        const loadLatestBooking = async () => {
            try {
                const response = await getMyBookings()
                const latestBooking = response.data
                    .filter((booking) =>
                        new Date(booking.end_time) < new Date()
                        && booking.status !== 'CANCELLED'
                    )
                    .sort((a, b) =>
                        new Date(b.start_time) - new Date(a.start_time)
                    )[0]

                setLatestBookingDate(latestBooking?.start_time || null)
            } catch (error) {
                console.error('Failed to load latest booking:', error)
            } finally {
                setBookingsLoaded(true)
            }
        }

        loadLatestBooking()
    }, [])

    return {
        latestBookingDate,
        bookingsLoaded,
    }

}