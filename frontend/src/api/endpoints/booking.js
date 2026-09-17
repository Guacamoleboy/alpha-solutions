// Pathing
// _______
// src/api/endpoints/booking.js

// Explained:
//
// This file serves as a facade. Instead of using crud.js this acts as the facade that tells how we want an entity to use the crud.js file.
//      - crud.js - "How do I make CRUD HTTP Requests?"
//      - client.js - "How do I make & setup HTTP connectivity?"
//      - endpoints/<name>.js - "How do I work with a specific entity?"


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

const pathing = 'booking'

// ------------------------------------------------------------------------------------------------------
// GET

export const getBookings = () => (
    getAll(pathing)
)

export const getBooking = (id) => (
    getById(pathing, id)
)

export const getMyBookings = () => (
    client('/booking/mine', {
        method: 'GET',
    })
)

// ------------------------------------------------------------------------------------------------------
// POST

export const createBooking = (data) => (
    create(pathing, data)
)

// ------------------------------------------------------------------------------------------------------
// PUT

export const updateBooking = (id, data) => (
    update(pathing, id, data)
)

// ------------------------------------------------------------------------------------------------------
// DELETE

export const deleteBooking = (id) => (
    deleteById(pathing, id)
)

export const deleteBookings = () => (
    deleteAll(pathing)
)

export const deleteBookingsSafe = () => (
    deleteAllSafe(pathing)
)