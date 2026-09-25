import {
    getAll,
    getById,
    create,
    update,
    deleteById,
    deleteAll,
    deleteAllSafe,
} from '@/api/crud'

const pathing = 'event-court-reservations'

// ------------------------------------------------------------------------------------------------------
// GET

export const getEventCourtReservations = () => (
    getAll(pathing)
)

export const getEventCourtReservation = (id) => (
    getById(pathing, id)
)

// ------------------------------------------------------------------------------------------------------
// POST

export const createEventCourtReservation = (data) => (
    create(pathing, data)
)

// ------------------------------------------------------------------------------------------------------
// PUT

export const updateEventCourtReservation = (id, data) => (
    update(pathing, id, data)
)

// ------------------------------------------------------------------------------------------------------
// DELETE

export const deleteEventCourtReservation = (id) => (
    deleteById(pathing, id)
)

export const deleteEventCourtReservations = () => (
    deleteAll(pathing)
)

export const deleteEventCourtReservationsSafe = () => (
    deleteAllSafe(pathing)
)