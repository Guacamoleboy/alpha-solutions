// Pathing
// _______
// src/features/member-settings-page/delete/Delete.hooks.js

import { useNavigate } from 'react-router-dom'
import { deleteMember } from '@/api/endpoints/member'
import useMember from '@/shared/hooks/useMember'
import useForm from '@/shared/hooks/useForm'

export const useDelete = () => {

    const navigate = useNavigate()
    const { member } = useMember()

    const {
        values,
        registerField,
    } = useForm()

    const handleDelete = async (e) => {
        e.preventDefault()

        if (values.confirmation !== 'JEG ER SIKKER') {
            return
        }

        try {
            await deleteMember(member.id)

            localStorage.removeItem('access_token')
            localStorage.removeItem('refresh_token')

            navigate('/')
        } catch (error) {
            console.error('Failed to delete member:', error)
        }
    }

    return {
        registerField,
        handleDelete,
    }
}