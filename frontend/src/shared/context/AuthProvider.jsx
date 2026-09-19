// Pathing
// _______
// src/shared/context/AuthProvider.jsx

// ------------------------------------------------------------------------------------------------

// What's the purpose of this class?
// _________________________________
//
//      -   The core authentication system for the entire application.
//      -   Maintains global authentication state.
//      -   Provides login, logout and role information.
//

// ------------------------------------------------------------------------------------------------

import {useEffect, useState} from "react"
import {useNavigate} from "react-router-dom"
import {AuthContext} from "./authContext"
import {login} from "@/api/endpoints/login"
import {decodeToken} from "@/shared/utils/token"

const normalizeUser = (decodedUser, member) => ({
    ...decodedUser,
    role: String(decodedUser.role ?? member?.role ?? '').toUpperCase(),
})

// ---- PROVIDER --------------------------------------------------------------

export function AuthProvider({children}) {

    const [user, setUser] = useState(null)
    const [authLoading, setAuthLoading] = useState(true)
    const [loginLoading, setLoginLoading] = useState(false)
    const [error, setError] = useState(null)

    const navigate = useNavigate()

    // ---- INITIAL AUTH CHECK ------------------------------------------------

    useEffect(() => {
        const validateUser = () => {
            const token = localStorage.getItem("access_token")
            // Validation
            if (!token) {
                setAuthLoading(false)
                return
            }
            // Try or fail
            try {
                const decodedUser = decodeToken(token)
                if (!decodedUser || decodedUser.type !== "access") {
                    throw new Error("Invalid access token")
                }
                setUser(normalizeUser(decodedUser))
            } catch {
                localStorage.removeItem("access_token")
                localStorage.removeItem("refresh_token")
                setUser(null)
            } finally {
                setAuthLoading(false)
            }
        }
        validateUser()
    }, [])

    // ---- LOGIN -------------------------------------------------------------

    const loginUser = async (credentials) => {
        setLoginLoading(true)
        setError(null)
        try {
            const response = await login(credentials)
            const {access_token, refresh_token} = response.data
            localStorage.setItem("access_token", access_token)
            localStorage.setItem("refresh_token", refresh_token)
            const decodedUser = decodeToken(access_token)

            if (!decodedUser || decodedUser.type !== "access") {
                throw new Error("Invalid access token")
            }

            const authenticatedUser = normalizeUser(decodedUser, response.data.member)
            setUser(authenticatedUser)

            // Navigate according to role
            if (authenticatedUser.role === "MEMBER") {
                navigate("/member")
            }
            if (authenticatedUser.role === "OWNER") {
                navigate("/dashboard")
            }
            
        } catch (err) {
            setError(err.message)
            throw err
        } finally {
            setLoginLoading(false)
        }

    }

    // ---- LOGOUT ------------------------------------------------------------

    const logout = () => {
        localStorage.removeItem("access_token")
        localStorage.removeItem("refresh_token")
        setUser(null)
        navigate("/")
    }

    // ---- CONTEXT VALUE -----------------------------------------------------

    const value = {
        user,
        role: user?.role ?? null,
        error,
        login: loginUser,
        logout,
        authLoading,
        loginLoading,
        isAuthenticated: !!user
    }

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    )

}