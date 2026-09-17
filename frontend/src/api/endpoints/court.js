// Pathing
// _______
// src/api/endpoints/court.js

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

const pathing = 'court'

// ------------------------------------------------------------------------------------------------------
// GET

export const getCourts = () => (
    getAll(pathing)
)

export const getCourt = (id) => (
    getById(pathing, id)
)

// ------------------------------------------------------------------------------------------------------
// POST

export const createCourt = (data) => (
    create(pathing, data)
)

// ------------------------------------------------------------------------------------------------------
// PUT

export const updateCourt = (id, data) => (
    update(pathing, id, data)
)

// ------------------------------------------------------------------------------------------------------
// DELETE

export const deleteCourt = (id) => (
    deleteById(pathing, id)
)

export const deleteCourts = () => (
    deleteAll(pathing)
)

export const deleteCourtsSafe = () => (
    deleteAllSafe(pathing)
)