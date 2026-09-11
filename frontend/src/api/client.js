// Pathing
// _______
// src/api/client.js

// Development only for now.
const BASE_URL = 'http://localhost:7070/v1'

export async function client(endpoint, options = {}) {

    // ---- SETUP ----------------------------------------------------------------------------------------------------------

    const url = `${BASE_URL}${endpoint}`
    const token = localStorage.getItem('access_token')

    console.log('[API REQUEST]')
    console.log('URL:', url)
    console.log('METHOD:', options.method || 'GET')
    console.log('BODY:', options.body || null)

    // ---- CONFIG --------------------------------------------------------------------------------------------------------

    const config = {
        headers: {
            'Content-Type': 'application/json',
            ...(token && {
                Authorization: `Bearer ${token}`,
            }),
            ...(options.headers || {}),
        },
        ...options,
    }

    // ---- REQUEST -------------------------------------------------------------------------------------------------------

    const response = await fetch(url, config)

    const data = await response
        .json()
        .catch(() => null)

    // ---- ERROR HANDLE --------------------------------------------------------------------------------------------------

    if (!response.ok) {

        if (response.status === 401) {
            localStorage.removeItem('access_token')
            localStorage.removeItem('refresh_token')
            window.location.href = '/'
        }

        const error = new Error(
            data?.message || 'API request failed'
        )

        error.status = response.status
        error.data = data

        console.error(error)

        throw error
    }

    return data
}