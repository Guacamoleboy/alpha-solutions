// Pathing
// _______
// src/app/pages/MemberPage.hooks.js

import { useEffect, useState } from 'react'
import { getMyBookings } from '@/api/endpoints/booking'

export const useMemberBookingStats = () => {

    const [stats, setStats] = useState({
        upcomingBookings: 0,
        favoriteCourt: 'Bane Navn',
        playedCourts: 0,
    })

    useEffect(() => {
        const loadBookingStats = async () => {
            try {
                const response = await getMyBookings()
                const bookings = response.data || []
                const now = new Date()
                const validBookings = bookings.filter((booking) => booking.status !== 'CANCELLED')
                const upcomingBookings = validBookings.filter((booking) => (
                    new Date(booking.start_time) > now
                ))
                const playedCourts = validBookings.filter((booking) => (
                    new Date(booking.end_time) < now
                ))
                const courtCounts = validBookings.reduce((counts, booking) => {
                    if (booking.court_name) {
                        counts[booking.court_name] = (counts[booking.court_name] || 0) + 1
                    }
                    return counts
                }, {})
                const favoriteCourt = Object.entries(courtCounts)
                    .sort(([, countA], [, countB]) => countB - countA)[0]?.[0]

                setStats({
                    upcomingBookings: upcomingBookings.length,
                    favoriteCourt: favoriteCourt || 'Bane Navn',
                    playedCourts: playedCourts.length,
                })
            } catch (error) {
                console.error('Failed to load member booking stats:', error)
            }
        }

        loadBookingStats()
    }, [])

    return stats
}