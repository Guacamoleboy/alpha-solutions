import {
    getAll,
    getById,
    create,
    update,
    deleteById,
    deleteAll,
    deleteAllSafe,
} from '@/api/crud'

const pathing = 'event-organizers'

// ------------------------------------------------------------------------------------------------------
// GET

export const getEventOrganizers = () => (
    getAll(pathing)
)

export const getEventOrganizer = (id) => (
    getById(pathing, id)
)

// ------------------------------------------------------------------------------------------------------
// POST

export const createEventOrganizer = (data) => (
    create(pathing, data)
)

// ------------------------------------------------------------------------------------------------------
// PUT

export const updateEventOrganizer = (id, data) => (
    update(pathing, id, data)
)

// ------------------------------------------------------------------------------------------------------
// DELETE

export const deleteEventOrganizer = (id) => (
    deleteById(pathing, id)
)

export const deleteEventOrganizers = () => (
    deleteAll(pathing)
)

export const deleteEventOrganizersSafe = () => (
    deleteAllSafe(pathing)
)