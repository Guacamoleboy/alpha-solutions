// Pathing
// _______
// src/features/forgot-password-page/forgot-password/ForgotPassword.hooks.js

import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import {
    resetForgottenPassword,
    verifyForgottenPassword,
} from '@/api/endpoints/member'
import useForm from '@/shared/hooks/useForm'
import useNotification from '@/shared/hooks/useNotification'

export const useForgotPassword = () => {

    const navigate = useNavigate()
    const { notify } = useNotification()
    const [isVerified, setIsVerified] = useState(false)
    const [verifiedIdentity, setVerifiedIdentity] = useState({})
    const { values, registerField, reset } = useForm()

    const handleVerify = async (e) => {
        e.preventDefault()
        try {
            await verifyForgottenPassword({
                email: values.email,
                date_of_birth: values.date_of_birth,
            })
            setVerifiedIdentity({
                email: values.email,
                date_of_birth: values.date_of_birth,
            })
            setIsVerified(true)
        } catch (error) {
            console.error('Failed to verify forgotten password:', error)
            notify(error.message || 'E-mail og fødselsdato matcher ikke', 'error')
        }
    }

    const handleReset = async (e) => {
        e.preventDefault()

        if (values.new_password !== values.confirm_password) {
            notify('Adgangskoderne matcher ikke', 'error')
            return
        }

        try {
            await resetForgottenPassword({
                ...verifiedIdentity,
                password: values.new_password,
                password_again: values.confirm_password,
            })
            notify('Password blev nulstillet', 'success')
            navigate('/')
        } catch (error) {
            console.error('Failed to reset forgotten password:', error)
            notify(error.message || 'Password kunne ikke nulstilles', 'error')
        } finally {
            reset()
        }
    }

    return {
        isVerified,
        navigate,
        registerField,
        handleVerify,
        handleReset,
    }

}
