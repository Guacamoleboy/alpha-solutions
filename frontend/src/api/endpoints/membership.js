// Pathing
// _______
// src/api/endpoints/membership.js

import { client } from '@/api/client'

// ------------------------------------------------------------------------------------------------------
// PUT

export const updateMembership = (data) => (
    client('/membership/update', {
        method: 'PUT',
        body: JSON.stringify(data),
    })
)

// ------------------------------------------------------------------------------------------------------
// GET

export const getMember = (id) => (
    client(`/membership/${id}`, {
        method: 'GET',
    })
)