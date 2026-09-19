// Pathing
// _______
// src/features/member-booking-page/court-booking/bookingTime.js

// TODO: Due to JSON Mapping Javalin implementation this probably needs a refactor as it used an old setup without.

const days = [
    'SUNDAY',
    'MONDAY',
    'TUESDAY',
    'WEDNESDAY',
    'THURSDAY',
    'FRIDAY',
    'SATURDAY',
]

// ------------------------------------------------------------------------------------------------------

export const getDayOfWeek = (date) => {
    const [year, month, day] = date.split('-').map(Number)
    return days[
        new Date(year, month - 1, day).getDay()
    ]
}

// ------------------------------------------------------------------------------------------------------

export const getBookingEndTime = (date, time) => {
    const [hours, minutes] = time
        .split(':')
        .map(Number)

    const endDate = new Date(
        2000,
        0,
        1,
        hours,
        minutes
    )

    endDate.setHours(
        endDate.getHours() + 1
    )

    const endHours = String(
        endDate.getHours()
    ).padStart(2, '0')

    const endMinutes = String(
        endDate.getMinutes()
    ).padStart(2, '0')

    return `${date}T${endHours}:${endMinutes}:00`
}

// ------------------------------------------------------------------------------------------------------

const getTimeInMinutes = (time) => {
    if (!time) {
        return null
    }

    const parts = Array.isArray(time)
        ? time
        : String(time).split(':')

    const hours = Number(parts[0])
    const minutes = Number(parts[1])

    if (!Number.isFinite(hours) || !Number.isFinite(minutes)) {
        return null
    }

    return hours * 60 + minutes
}

// ------------------------------------------------------------------------------------------------------

export const getAvailableTimes = (date, operatingHour) => {

    if (!operatingHour || operatingHour.closed) {
        return []
    }

    const openMinutes = getTimeInMinutes(operatingHour.open_time)
    const closeMinutes = getTimeInMinutes(operatingHour.close_time)

    if (openMinutes === null || closeMinutes === null || openMinutes >= closeMinutes) {
        return []
    }

    // Get current time in minutes
    const now = new Date()
    const currentMinutes =
        now.getHours() * 60 +
        now.getMinutes()

    // Get todays date
    const today = [
        now.getFullYear(),
        String(now.getMonth() + 1).padStart(2, '0'),
        String(now.getDate()).padStart(2, '0'),
    ].join('-')

    const isToday = date === today

    // Generate available booking start times
    const times = []

    for (let minutes = openMinutes; minutes < closeMinutes; minutes += 60) {

        // Skip times that have already passed today
        if (isToday && minutes <= currentMinutes) {
            continue
        }
        
        // Formatting
        const hours = String(Math.floor(minutes / 60)).padStart(2, '0')
        const minute = String(minutes % 60).padStart(2, '0')
        const time = `${hours}:${minute}`
        
        // Final times addition (Last hour isn't added due to 1 hour playtime)
        times.push({value: time,label: time})
    }

    return times
}