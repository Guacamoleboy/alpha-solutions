// Pathing
// _______
// src/features/member-settings-page/update/Update.hooks.js

import { updateMember } from '@/api/endpoints/member'
import useMember from '@/shared/hooks/useMember'
import useForm from '@/shared/hooks/useForm'

export const useUpdate = () => {

    const { member, updateMemberState } = useMember()

    const {
        values,
        registerField,
        reset,
    } = useForm()

    const handleUpdate = async (e) => {
        e.preventDefault()

        try {
            await updateMember(member.id, values)
            updateMemberState(values)
            reset()
        } catch (error) {
            console.error('Failed to update member:', error)
        }
    }

    return {
        member,
        registerField,
        handleUpdate,
    }
}