// Pathing
// _______
// src/features/login-page/login/Login.hooks.js

import { useNavigate } from 'react-router-dom'
import { login } from '@/api/endpoints/login'
import useForm from '@/shared/hooks/useForm'

export const useLogin = () => {

    // Navigation
    const navigate = useNavigate()

    // useForm
    const {
        values,
        registerField,
    } = useForm()

    // Handle
    const handleSubmit = async (e) => {
        e.preventDefault()

        try {
            const response = await login(values)

            localStorage.setItem('access_token', response.data.access_token)
            localStorage.setItem('refresh_token', response.data.refresh_token)

            navigate('/member')
        } catch (error) {
            console.error('Login failed:', error)
        }
    }

    return {
        navigate,
        registerField,
        handleSubmit,
    }
}