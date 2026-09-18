// Pathing
// _______
// src/shared/context/authContext.js

// ------------------------------------------------------------------------------------------------

// What's the purpose of this class?
// _________________________________
//
//      -   It acts as a container for global authentication state.
//

// ------------------------------------------------------------------------------------------------

import { createContext } from "react"

export const authContext = createContext(null)