// Pathing
// _______
// src/api/endpoints/login.js

import { client } from '@/api/client'

// ------------------------------------------------------------------------------------------------------
// POST

export const login = (data) => (
    client('/auth/login', {
        method: 'POST',
        body: JSON.stringify(data),
    })
)