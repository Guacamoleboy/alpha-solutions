// Pathing
// _______
// src/features/register-page/register/Register.hooks.js

import { useNavigate } from 'react-router-dom'
import { register } from '@/api/endpoints/register'
import useForm from '@/shared/hooks/useForm'

export const useRegister = () => {

    // Navigation
    const navigate = useNavigate()

    // Form setup using shared hook
    const {
        values,
        registerField,
    } = useForm()

    // Handle
    const handleSubmit = async (e) => {
        e.preventDefault()
        
        // Password check. 
        // TODO: Move console output to UI element.
        if (values.password !== values.password_again) {
            console.error('Passwords do not match')
            return
        }

        // Need to check this out later. ESLINT error as of right now. Due to JavaScript setup instead of TypeScript.
        // Wanted to use TypeScript for Types and Interfaces.

        // eslint-disable-next-line
        const { password_again, ...request } = values

        // Try-Catch over request
        try {
            await register(request)
            navigate('/')
        } catch (error) {
            console.error('Registration failed:', error)
        }
    }

    // Data
    const inputRows = [
        [
            ['text', 'first_name', 'Fornavn', 'Indtast fornavn...'],
            ['text', 'last_name', 'Efternavn', 'Indtast efternavn...'],
        ],
        [
            ['password', 'password', 'Adgangskode', 'Indtast adgangskode...'],
            ['password', 'password_again', 'Adgangskode igen', 'Indtast adgangskode igen...'],
        ],
        [
            ['text', 'phone', 'Telefon', 'Indtast telefonnummer...'],
            ['date', 'date_of_birth', 'Fødselsdato', ''],
        ],
        [
            ['email', 'email', 'E-mail', 'Indtast e-mail...'],
            ['select', 'gender', 'Køn', ''],
        ],
    ]
    
    return {
        navigate,
        registerField,
        handleSubmit,
        inputRows,
    }
}