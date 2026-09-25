import {
    getAll,
    getById,
    create,
    update,
    deleteById,
    deleteAll,
    deleteAllSafe,
} from '@/api/crud'

import { client } from '@/api/client'

const pathing = 'event-requests'

// ------------------------------------------------------------------------------------------------------
// GET

export const getEventRequests = () => (
    getAll(pathing)
)

export const getMemberEventRequests = () => (
    client(`/${pathing}/mine`)
)

export const getEventRequest = (id) => (
    getById(pathing, id)
)

// ------------------------------------------------------------------------------------------------------
// POST

export const createEventRequest = (data) => (
    create(pathing, data)
)

// ------------------------------------------------------------------------------------------------------
// PUT

export const updateEventRequest = (id, data) => (
    update(pathing, id, data)
)

// ------------------------------------------------------------------------------------------------------
// DELETE

export const deleteEventRequest = (id) => (
    deleteById(pathing, id)
)

export const deleteEventRequests = () => (
    deleteAll(pathing)
)

export const deleteEventRequestsSafe = () => (
    deleteAllSafe(pathing)
)