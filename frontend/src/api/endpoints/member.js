// Pathing
// _______
// src/api/endpoints/member.js

import { client } from '@/api/client'

// ------------------------------------------------------------------------------------------------------
// PUT

export const updateMember = (data) => (
    client('/member/update', {
        method: 'PUT',
        body: JSON.stringify(data),
    })
)

export const updateMemberPassword = (data) => (
    client('/member/update-password', {
        method: 'PUT',
        body: JSON.stringify(data),
    })
)

// ------------------------------------------------------------------------------------------------------
// GET

export const getMember = (id) => (
    client(`/member/${id}`, {
        method: 'GET',
    })
)

// ------------------------------------------------------------------------------------------------------
// DELETE

export const deleteMember = () => (
    client('/member/delete', {
        method: 'DELETE',
    })
)

