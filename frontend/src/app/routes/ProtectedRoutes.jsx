// Pathing
// _______
// src/app/routes/ProtectedRoutes.jsx

import {Navigate, Outlet} from "react-router-dom"
import {useAuth} from "@/shared/hooks/useAuth"

const ProtectedRoutes = ({allowedRoles}) => {

    // ---- SETUP ----------------------------------------------------------------------------------------------------------

    const {user, authLoading} = useAuth()

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

    if (allowedRoles && !allowedRoles.includes(user.role)) {
        return (
            <Navigate
                to="/"
                replace
            />
        )
    }

    // ---- RENDER ---------------------------------------------------------------------------------------------------------

    return <Outlet />

}

export default ProtectedRoutes