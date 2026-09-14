// Pathing
// _______
// src/shared/hooks/useMember.js

import { useEffect, useState } from 'react'
import { client } from '@/api/client'

let member = null
let memberPromise = null
const listeners = new Set()

export default function useMember() {

    // State
    const [data, setData] = useState(member)

    // Use Effect for fetch and reload
    useEffect(() => {
        listeners.add(setData)

        if (member) {
            return () => listeners.delete(setData)
        }

        // If no member use /me
        if (!memberPromise) {
            memberPromise = client('/auth/me')
                .then(response => {
                    member = response.data
                    listeners.forEach(listener => listener(member))
                    return member
                })
                .catch(error => {
                    memberPromise = null
                    throw error
                })
        }

        // Set data for memberPromise if data
        memberPromise
            .then(setData)
            .catch(error => {
                console.error('Failed to fetch member:', error)
            })

        return () => listeners.delete(setData)
    }, []) // Empty

    const updateMemberState = (newData) => {
        member = { ...member, ...newData }
        listeners.forEach(listener => listener(member))
    }

    // Return data
    return {
        member: data,
        updateMemberState,
    }

}