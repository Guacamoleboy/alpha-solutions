// Pathing
// _______
// src/shared/utils/dateTime.js

// Format date
export const formatDate = (dateTime) => {
    const date = new Date(dateTime)
    return date.toLocaleDateString('da-DK', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
    })
}

// Format time
export const formatTime = (dateTime) => {
    const date = new Date(dateTime)
    return date.toLocaleTimeString('da-DK', {
        hour: '2-digit',
        minute: '2-digit',
    })
}