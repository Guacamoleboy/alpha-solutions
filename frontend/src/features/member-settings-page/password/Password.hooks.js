// Pathing
// _______
// src/features/member-settings-page/password/Password.hooks.js

import { updateMemberPassword } from '@/api/endpoints/member'
import useForm from '@/shared/hooks/useForm'

export const usePassword = () => {

    // Form hook setup
    const {
        values,
        registerField,
    } = useForm()

    // Handle
    const handlePassword = async (e) => {
        e.preventDefault()
        try {
            await updateMemberPassword({
                current_password: values.currentPassword,
                new_password: values.newPassword,
                confirm_password: values.confirmPassword,
            })
        } catch (error) {
            console.error('Failed to update password:', error)
        }
    }

    return {
        registerField,
        handlePassword,
    }

}