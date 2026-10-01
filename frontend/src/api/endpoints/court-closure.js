// Pathing
// _______
// src/api/endpoints/court-closure.js

import {
    getAll,
    getById,
    create,
    update,
    deleteById,
    deleteAll,
    deleteAllSafe,
} from '@/api/crud'

const pathing = 'court-closures'

// ------------------------------------------------------------------------------------------------------
// GET

export const getCourtClosures = () => (
    getAll(pathing)
)

export const getCourtClosure = (id) => (
    getById(pathing, id)
)

// ------------------------------------------------------------------------------------------------------
// POST

export const createCourtClosure = (data) => (
    create(pathing, data)
)

// ------------------------------------------------------------------------------------------------------
// PUT

export const updateCourtClosure = (id, data) => (
    update(pathing, id, data)
)

// ------------------------------------------------------------------------------------------------------
// DELETE

export const deleteCourtClosure = (id) => (
    deleteById(pathing, id)
)

export const deleteCourtClosures = () => (
    deleteAll(pathing)
)

export const deleteCourtClosuresSafe = () => (
    deleteAllSafe(pathing)
)