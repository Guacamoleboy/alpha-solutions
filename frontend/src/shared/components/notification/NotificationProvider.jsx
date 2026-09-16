// Pathing
// _______
// src/shared/components/notification/NotificationProvider.jsx

import { useCallback, useState } from 'react'
import NotificationContext from './NotificationContext'
import Notification from './Notification'

// Visual Render Time
const DEFAULT_DURATION = 4000

export const NotificationProvider = ({ children }) => {

    // State
    const [notification, setNotification] = useState(null)

    // Notification
    const notify = useCallback((message, type = 'success', duration = DEFAULT_DURATION) => {
        
        // State set
        setNotification({message, type})

        // Timeout
        if (duration > 0) {
            setTimeout(() => {
                setNotification(null)
            }, duration)
        }

    }, []) // Empty

    // 
    const dismiss = useCallback(() => {
        setNotification(null)
    }, [])

    return (
        <NotificationContext.Provider value={{notification, notify, dismiss}}>
            {children}
            <Notification />
        </NotificationContext.Provider>
    )
}