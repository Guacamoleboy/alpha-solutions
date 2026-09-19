// Pathing
// _______
// src/app/routes/ProtectedRoutes.jsx

import {Navigate, Outlet} from "react-router-dom"
import {useEffect} from "react"
import {useAuth} from "@/shared/hooks/useAuth"
import useNotification from "@/shared/hooks/useNotification"

const ProtectedRoutes = ({allowedRoles}) => {

    // ---- SETUP ----------------------------------------------------------------------------------------------------------

    const {user, authLoading} = useAuth()
    const {notify} = useNotification()
    const hasAccess = !user || !allowedRoles || allowedRoles.includes(user.role)

    useEffect(() => {
        if (!authLoading && user && !hasAccess) {
            notify('Du har ikke adgang til denne side.', 'warning')
        }
    }, [authLoading, hasAccess, notify, user])

    // ---- MOUNT ----------------------------------------------------------------------------------------------------------

    if (authLoading) {
        return null
    }

    // ---- AUTHENTICATION -------------------------------------------------------------------------------------------------

    if (!user) {
        return (
            <Navigate
                to="/"
                replace
            />
        )
    }

    // ---- AUTHORIZATION --------------------------------------------------------------------------------------------------

    if (!hasAccess) {
        return (
            <Navigate
                to={user.role === 'OWNER' ? '/dashboard' : '/member'}
                replace
            />
        )
    }

    // ---- RENDER ---------------------------------------------------------------------------------------------------------

    return <Outlet />

}

export default ProtectedRoutes