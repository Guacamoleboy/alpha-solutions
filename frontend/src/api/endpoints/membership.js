// Pathing
// _______
// src/api/endpoints/membership.js

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

const pathing = 'membership'

// ------------------------------------------------------------------------------------------------------
// GET

export const getMemberships = () => (
    getAll(pathing)
)

export const getMembership = (id) => (
    getById(pathing, id)
)

// ------------------------------------------------------------------------------------------------------
// POST

export const createMembership = (data) => (
    create(pathing, data)
)

// ------------------------------------------------------------------------------------------------------
// PUT

export const updateMembership = (id, data) => (
    update(pathing, id, data)
)

// ------------------------------------------------------------------------------------------------------
// DELETE

export const deleteMembership = (id) => (
    deleteById(pathing, id)
)

export const deleteMemberships = () => (
    deleteAll(pathing)
)

export const deleteMembershipsSafe = () => (
    deleteAllSafe(pathing)
)