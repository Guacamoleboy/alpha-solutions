// Pathing
// _______
// src/features/member-booking-page/active-bookings/ActiveBookings.hooks.js

import { useState } from 'react'
import { deleteBooking } from '@/api/endpoints/booking'
import useNotification from '@/shared/hooks/useNotification'

export const useActiveBookings = (bookings) => {

    // State
    const [loading, setLoading] = useState(false)

    // Notification setup
    const {notify} = useNotification()

    // Get active bookings
    const activeBookings = bookings.filter((booking) =>
        new Date(booking.end_time) > new Date()
    )

    // Handle cancellation
    const handleCancel = async (bookingId) => {
        try {
            setLoading(true)
            await deleteBooking(bookingId)
            notify('Booking aflyst','success')
        } catch (error) {
            console.error('Failed to cancel booking:', error)
            notify('Booking kunne ikke aflyses - Prøv igen eller kontakt os','error')
        } finally {
            setLoading(false)
        }
    }

    return {
        activeBookings,
        loading,
        handleCancel,
    }

}