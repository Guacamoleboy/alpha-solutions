// Pathing
// _______
// src/api/endpoints/register.js

import { client } from '@/api/client'

export const register = (data) => (
    client('/auth/register', {
        method: 'POST',
        body: JSON.stringify(data),
    })
)