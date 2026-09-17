// Pathing
// _______
// src/shared/hooks/useCourt.js

import { useEffect, useState } from 'react'
import { getCourts } from '@/api/endpoints/court'

// ------------------------------------------------------------------------------------------------------

const CACHE_DURATION = 6 * 60 * 60 * 1000

// ------------------------------------------------------------------------------------------------------

let cachedCourts = null
let cachedAt = null
let fetchPromise = null

// ------------------------------------------------------------------------------------------------------

const fetchCourts = async () => {

    // Return cached data if it is still valid
    if (cachedCourts && cachedAt && Date.now() - cachedAt < CACHE_DURATION) {
        return cachedCourts
    }

    // Reuse an existing request if another component is already fetching
    if (fetchPromise) {
        return fetchPromise
    }

    // Fetch in case no data is available
    fetchPromise = getCourts()
        .then((response) => {
            cachedCourts = response.data
            cachedAt = Date.now()
            return cachedCourts
        })
        .finally(() => {
            fetchPromise = null
        })

    return fetchPromise

}

// ------------------------------------------------------------------------------------------------------

export const useCourt = () => {

    // State
    const [courts, setCourts] = useState(cachedCourts || [])

    // Loading state
    const [loading, setLoading] = useState(!cachedCourts)
    
    // Error state
    const [error, setError] = useState(null)

    // Fetch
    useEffect(() => {
        let mounted = true
        const loadCourts = async () => {
            try {
                setLoading(true)
                const data = await fetchCourts()
                if (mounted) {
                    setCourts(data)
                }
            } catch (error) {
                console.error('Failed to load courts:', error)
                if (mounted) {
                    setError(error)
                }
            } finally {
                if (mounted) {
                    setLoading(false)
                }
            }
        }
        loadCourts()
        return () => {
            mounted = false
        }
    }, [])

    return {
        courts,
        loading,
        error
    }

}