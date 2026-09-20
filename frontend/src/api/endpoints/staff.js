// Pathing
// _______
// src/api/endpoints/staff.js

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

const pathing = 'staff'

// ------------------------------------------------------------------------------------------------------
// GET

export const getStaff = () => (
    getAll(pathing)
)

export const getStaffMember = (id) => (
    getById(pathing, id)
)

// ------------------------------------------------------------------------------------------------------
// POST

export const createStaff = (data) => (
    create(pathing, data)
)

// ------------------------------------------------------------------------------------------------------
// PUT

export const updateStaff = (id, data) => (
    update(pathing, id, data)
)

// ------------------------------------------------------------------------------------------------------
// DELETE

export const deleteStaff = (id) => (
    deleteById(pathing, id)
)

export const deleteStaffMembers = () => (
    deleteAll(pathing)
)

export const deleteStaffMembersSafe = () => (
    deleteAllSafe(pathing)
)