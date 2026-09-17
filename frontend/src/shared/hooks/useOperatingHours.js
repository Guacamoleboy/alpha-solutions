// Pathing
// _______
// src/shared/hooks/useOperatingHours.js

import { useEffect, useState } from 'react'
import { getOperatingHours } from '@/api/endpoints/operating-hour'

// ------------------------------------------------------------------------------------------------------

const CACHE_DURATION = 6 * 60 * 60 * 1000

// ------------------------------------------------------------------------------------------------------

let cachedOperatingHours = null
let cachedAt = null
let fetchPromise = null

// ------------------------------------------------------------------------------------------------------

const fetchOperatingHours = async () => {

    // Return cached data if it is still valid
    if (cachedOperatingHours && cachedAt && Date.now() - cachedAt < CACHE_DURATION) {
        return cachedOperatingHours
    }

    // Reuse an existing request if another component is already fetching
    if (fetchPromise) {
        return fetchPromise
    }

    // Fetch as final solution
    fetchPromise = getOperatingHours()
        .then((response) => {
            cachedOperatingHours = response.data
            cachedAt = Date.now()
            return cachedOperatingHours
        })
        .finally(() => {
            fetchPromise = null
        })

    return fetchPromise
}

// ------------------------------------------------------------------------------------------------------

export const useOperatingHours = () => {

    // State
    const [operatingHours, setOperatingHours] = useState(cachedOperatingHours || [])

    // Loading state
    const [loading, setLoading] = useState(!cachedOperatingHours)

    // Error state
    const [error, setError] = useState(null)

    // Fetch
    useEffect(() => {
        let mounted = true
        const loadOperatingHours = async () => {
            try {
                setLoading(true)
                const data = await fetchOperatingHours()
                if (mounted) {
                    setOperatingHours(data)
                }
            } catch (error) {
                console.error( 'Failed to load operating hours:', error)
                if (mounted) {
                    setError(error)
                }
            } finally {
                if (mounted) {
                    setLoading(false)
                }
            }
        }
        loadOperatingHours()
        return () => {
            mounted = false
        }
    }, [])

    return {
        operatingHours,
        loading,
        error
    }

}