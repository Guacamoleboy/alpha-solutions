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
import {authContext} from "@shared/context/authContext"

export function useAuth() {
    return useContext(authContext)
}