// Pathing
// _______
// src/api/endpoints/member.js

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

const pathing = 'member'

// ------------------------------------------------------------------------------------------------------
// GET

export const getMembers = () => (
    getAll(pathing)
)

export const getMember = (id) => (
    getById(pathing, id)
)

// ------------------------------------------------------------------------------------------------------
// POST

export const createMember = (data) => (
    create(pathing, data)
)

export const updateMemberPassword = (data) => (
    client(`/${pathing}/password`, { 
            method: 'POST',
            body: JSON.stringify(data),
        })
)

// ------------------------------------------------------------------------------------------------------
// PUT

export const updateMember = (id, data) => (
    update(pathing, id, data)
)

// ------------------------------------------------------------------------------------------------------
// DELETE

export const deleteMember = (id) => (
    deleteById(pathing, id)
)

export const deleteMembers = () => (
    deleteAll(pathing)
)

export const deleteMembersSafe = () => (
    deleteAllSafe(pathing)
)