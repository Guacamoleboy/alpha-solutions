// Pathing
// _______
// src/features/member-page/hours-today/HoursToday.hooks.js

import { useEffect, useState } from 'react'
import { useOperatingHours } from '@/shared/hooks/useOperatingHours'

// ------------------------------------------------------------------------------------------------------

const dayNames = [
    'SUNDAY',
    'MONDAY',
    'TUESDAY',
    'WEDNESDAY',
    'THURSDAY',
    'FRIDAY',
    'SATURDAY',
]


// ------------------------------------------------------------------------------------------------------

const getTimeRemaining = (closeTime) => {
    const [hours, minutes] = closeTime.split(':').map(Number)
    const close = new Date()
    close.setHours(hours, minutes, 0, 0)
    const difference = Math.max(0, close.getTime() - Date.now())
    const totalSeconds = Math.floor(difference / 1000)
    return `${String(Math.floor(totalSeconds / 60)).padStart(2, '0')}:${String(totalSeconds % 60).padStart(2, '0')}`
}


// ------------------------------------------------------------------------------------------------------

const getTimeInMinutes = (time) => {
    const [hours, minutes] = time.split(':').map(Number)
    return hours * 60 + minutes
}


// ------------------------------------------------------------------------------------------------------

const formatTime = (time) => time.slice(0, 5)


// ------------------------------------------------------------------------------------------------------

export const useHoursToday = () => {

    const { operatingHours, loading } = useOperatingHours()
    const [currentTime, setCurrentTime] = useState(() => new Date())
    const today = dayNames[new Date().getDay()]
    const todayHours = operatingHours.find((hours) => hours.day_of_week === today)
    const currentMinutes = currentTime.getHours() * 60 + currentTime.getMinutes()
    const openingMinutes = todayHours?.open_time ? getTimeInMinutes(todayHours.open_time) : null
    const closingMinutes = todayHours?.close_time ? getTimeInMinutes(todayHours.close_time) : null
    const beforeOpening = openingMinutes !== null && currentMinutes < openingMinutes
    const currentlyOpen = openingMinutes !== null
        && closingMinutes !== null
        && currentMinutes >= openingMinutes
        && currentMinutes < closingMinutes
    const todayLabel = new Intl.DateTimeFormat('da-DK', {
        weekday: 'long',
        day: 'numeric',
        month: 'long',
    }).format(new Date())

    useEffect(() => {
        if (!todayHours?.open_time || !todayHours?.close_time || todayHours.closed) {
            return undefined
        }

        const interval = setInterval(() => setCurrentTime(new Date()), 1000)

        return () => clearInterval(interval)
    }, [todayHours])

    const statusLabel = todayHours?.closed
        ? 'Lukket'
        : beforeOpening
            ? 'Åbner igen'
            : currentlyOpen
                ? 'Vi lukker om'
                : 'Lukket'
    const statusTime = beforeOpening
        ? formatTime(todayHours.open_time)
        : currentlyOpen
            ? getTimeRemaining(todayHours.close_time)
            : null

    return {
        loading,
        statusLabel,
        statusTime,
        todayLabel,
    }
    
}