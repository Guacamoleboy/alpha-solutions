// Pathing
// _______
// src/app/routes/PremiumMemberRoute.jsx

import {Navigate, Outlet} from 'react-router-dom'
import {useEffect} from 'react'
import useMember from '@/shared/hooks/useMember'
import useNotification from '@/shared/hooks/useNotification'

const PremiumMemberRoute = () => {

    // Setup
    const {member} = useMember()
    const {notify} = useNotification()
    const canRequestEvents = member?.membership_id >= 3
    
    useEffect(() => {
        if (member && !canRequestEvents) {
            notify('Du har ikke adgang - opgradér medlemsskab', 'error')
        }
    }, [canRequestEvents, member, notify])

    // Member check
    if (!member) {
        return null
    }

    // Navigate away
    if (!canRequestEvents) {
        return <Navigate to="/membership" replace />
    }

    return <Outlet />
}

export default PremiumMemberRoute