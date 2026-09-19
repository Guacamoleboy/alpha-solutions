// Pathing
// _______
// src/shared/hooks/useAuth.js

// ------------------------------------------------------------------------------------------------

// What's the purpose of this class?
// _________________________________
//
//      -   Provides easy access to the AuthContext.
//      -   Uses useAuth() instead of useContext(authContext).
//

// ------------------------------------------------------------------------------------------------

import {useContext} from "react"
import {AuthContext} from "@/shared/context/authContext"

export function useAuth() {
    const context = useContext(AuthContext)

    if (!context) {
        throw new Error("useAuth must be used within an AuthProvider")
    }

    return context
}