// Pathing
// _______
// src/api/endpoints/operating-hour.js

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

const pathing = 'settings/operating-hours'

// ------------------------------------------------------------------------------------------------------
// GET

export const getOperatingHours = () => (
    getAll(pathing)
)

export const getOperatingHour = (id) => (
    getById(pathing, id)
)

// ------------------------------------------------------------------------------------------------------
// POST

export const createOperatingHour = (data) => (
    create(pathing, data)
)

// ------------------------------------------------------------------------------------------------------
// PUT

export const updateOperatingHour = (id, data) => (
    update(pathing, id, data)
)

// ------------------------------------------------------------------------------------------------------
// DELETE

export const deleteOperatingHour = (id) => (
    deleteById(pathing, id)
)

export const deleteOperatingHours = () => (
    deleteAll(pathing)
)

export const deleteOperatingHoursSafe = () => (
    deleteAllSafe(pathing)
)