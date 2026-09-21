// Pathing
// _______
// src/features/member-page/membership/Membership.hooks.js

import { updateMember } from '@/api/endpoints/member'
import useMember from '@/shared/hooks/useMember'
import useNotification from '@/shared/hooks/useNotification'

export const useMembership = () => {

    const { member, updateMemberState } = useMember()
    const { notify } = useNotification()

    const handleMembershipChange = async (membershipId) => {
        if (!member?.id || membershipId === member.membership_id) {
            return
        }

        try {
            const response = await updateMember(member.id, {
                membership_id: membershipId,
            })
            const updatedMember = response?.data || response

            updateMemberState({
                membership_id: updatedMember.membership_id ?? membershipId,
                membership_name: updatedMember.membership_name,
            })
            notify('Medlemskab blev ændret', 'success')
        } catch (error) {
            console.error('Failed to update membership:', error)
            notify(error.message || 'Medlemskab kunne ikke ændres', 'error')
        }
    }

    return {
        member,
        handleMembershipChange,
    }
    
}