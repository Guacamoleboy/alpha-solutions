// Pathing
// _______
// src/features/login-page/login/Login.hooks.js

import { useNavigate } from 'react-router-dom'
import useForm from '@/shared/hooks/useForm'
import { useAuth } from '@/shared/hooks/useAuth'
import useNotification from '@/shared/hooks/useNotification'

export const useLogin = () => {

    // Navigation
    const navigate = useNavigate()
    const { login: loginUser } = useAuth()
    const { notify } = useNotification()

    // useForm
    const {
        values,
        registerField,
    } = useForm()

    // Handle
    const handleSubmit = async (e) => {
        e.preventDefault()

        try {
            await loginUser(values)
        } catch (error) {
            console.error('Login failed:', error)
            notify(error.message || 'Login kunne ikke gennemføres', 'error')
        }
    }

    return {
        navigate,
        registerField,
        handleSubmit,
    }
}