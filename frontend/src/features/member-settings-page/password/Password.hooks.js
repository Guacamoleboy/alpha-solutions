// Pathing
// _______
// src/features/member-settings-page/password/Password.hooks.js

import { updateMemberPassword } from '@/api/endpoints/member'
import useForm from '@/shared/hooks/useForm'
import useNotification from '@/shared/hooks/useNotification'

export const usePassword = () => {

    // Form hook setup
    const {
        values,
        registerField,
        reset,
    } = useForm()

    // Notification setup
    const {
        notify,
    } = useNotification()

    // Handle
    const handlePassword = async (e) => {
        e.preventDefault()
        try {
            await updateMemberPassword({
                current_password: values.currentPassword,
                new_password: values.newPassword,
                confirm_password: values.confirmPassword,
            })
            notify('Password blev ændret', 'success')
        } catch (error) {
            console.error('Failed to update password:', error)
            notify('Password kunne ikke ændres', 'error')
        } finally {
            reset() // Resets values no matter the result
        }
    }

    return {
        registerField,
        handlePassword,
    }

}