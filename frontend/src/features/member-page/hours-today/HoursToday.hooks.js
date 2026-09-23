// Pathing
// _______
// src/features/member-page/hours-today/HoursToday.hooks.js

import { useEffect, useState } from 'react'
import { useOperatingHours } from '@/shared/hooks/useOperatingHours'

const dayNames = [
    'SUNDAY',
    'MONDAY',
    'TUESDAY',
    'WEDNESDAY',
    'THURSDAY',
    'FRIDAY',
    'SATURDAY',
]

const getTimeRemaining = (closeTime) => {
    const [hours, minutes] = closeTime.split(':').map(Number)
    const close = new Date()
    close.setHours(hours, minutes, 0, 0)
    const difference = Math.max(0, close.getTime() - Date.now())
    const totalSeconds = Math.floor(difference / 1000)

    return `${String(Math.floor(totalSeconds / 60)).padStart(2, '0')}:${String(totalSeconds % 60).padStart(2, '0')}`
}

export const useHoursToday = () => {

    const { operatingHours, loading } = useOperatingHours()
    const [timeRemaining, setTimeRemaining] = useState(null)
    const today = dayNames[new Date().getDay()]
    const todayHours = operatingHours.find((hours) => hours.day_of_week === today)
    const todayLabel = new Intl.DateTimeFormat('da-DK', {
        weekday: 'long',
        day: 'numeric',
        month: 'long',
    }).format(new Date())

    useEffect(() => {
        if (!todayHours?.close_time || todayHours.closed) {
            return undefined
        }

        const updateCountdown = () => setTimeRemaining(getTimeRemaining(todayHours.close_time))
        updateCountdown()
        const interval = setInterval(updateCountdown, 1000)

        return () => clearInterval(interval)
    }, [todayHours])

    return {
        loading,
        timeRemaining,
        todayLabel,
    }
}