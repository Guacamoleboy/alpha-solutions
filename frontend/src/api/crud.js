// Pathing
// _______
// src/api/crud.js

import { client } from '@/api/client'

// ------------------------------------------------------------------------------------------------------
// GET

export const getAll = (pathing) => (
    client(`/${pathing}/all`, {
        method: 'GET',
    })
)

export const getById = (pathing, id) => (
    client(`/${pathing}/${id}`, {
        method: 'GET',
    })
)

// ------------------------------------------------------------------------------------------------------
// POST

export const create = (pathing, data) => (
    client(`/${pathing}`, {
        method: 'POST',
        body: JSON.stringify(data),
    })
)

// ------------------------------------------------------------------------------------------------------
// PUT

export const update = (pathing, id, data) => (
    client(`/${pathing}/${id}`, {
        method: 'PUT',
        body: JSON.stringify(data),
    })
)

// ------------------------------------------------------------------------------------------------------
// DELETE

export const deleteById = (pathing, id) => (
    client(`/${pathing}/${id}`, {
        method: 'DELETE',
    })
)

export const deleteAll = (pathing) => (
    client(`/${pathing}/all`, {
        method: 'DELETE',
    })
)

export const deleteAllSafe = (pathing) => (
    client(`/${pathing}/all/safe`, {
        method: 'DELETE',
    })
)