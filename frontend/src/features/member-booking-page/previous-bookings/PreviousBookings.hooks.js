// Pathing
// _______
// src/features/member-booking-page/previous-bookings/PreviousBookings.hooks.js

export const usePreviousBookings = (bookings) => {

    // Get previous bookings
    const previousBookings = bookings
        .filter((booking) =>
                new Date(booking.end_time) < new Date()
                && booking.status !== 'CANCELLED'
        )
        .sort((a, b) =>
                new Date(b.start_time) - new Date(a.start_time)
        )

    return {
        previousBookings,
    }
    
}