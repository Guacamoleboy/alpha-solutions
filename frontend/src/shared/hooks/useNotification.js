// Pathing
// _______
// src/shared/hooks/useNotification.js

import { useContext } from 'react'
import NotificationContext from '@/shared/components/notification/NotificationContext'

export default function useNotification() {

    // Context
    const context = useContext(NotificationContext)

    // Context wrapping check
    if (!context) {
        throw new Error(
            'useNotification must be used within a NotificationProvider'
        )
    }

    return context
    
}