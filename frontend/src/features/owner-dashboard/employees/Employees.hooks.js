// Pathing
// _______
// src/features/owner-dashboard/employees/Employees.hooks.js

import {useEffect, useState} from 'react'
import {createStaff, deleteStaff, getStaff, updateStaff} from '@/api/endpoints/staff'
import useNotification from '@/shared/hooks/useNotification'

const fields = [
    {key: 'first_name', itemKey: 'first_name', label: 'Fornavn', required: true, updateRequired: false},
    {key: 'last_name', itemKey: 'last_name', label: 'Efternavn', required: true, updateRequired: false},
    {key: 'email', itemKey: 'email', label: 'Email', type: 'email', required: true, updateRequired: false},
    {key: 'phone', itemKey: 'phone', label: 'Telefon'},
    {key: 'salary', itemKey: 'salary', label: 'Løn', type: 'number'},
    {key: 'working_hours_weekly', itemKey: 'working_hours_weekly', label: 'Timer pr. uge', type: 'number'},
]

// ------------------------------------------------------------------------------------------------------

export const useEmployees = () => {
    const [staff, setStaff] = useState([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(null)
    const [view, setView] = useState('home')
    const {notify} = useNotification()

    useEffect(() => {
        getStaff()
            .then((response) => setStaff(response.data || []))
            .catch((loadError) => {
                setError(loadError)
                notify(loadError.message || 'Ansatte kunne ikke hentes', 'error')
            })
            .finally(() => setLoading(false))
    }, [notify])

    const handleCreate = async (values) => {
        const response = await createStaff(values)
        setStaff((current) => [...current, response.data])
        return response
    }

    const handleUpdate = async (item, values) => {
        const response = await updateStaff(item.id, values)
        setStaff((current) => current.map((staffMember) => (
            staffMember.id === item.id ? response.data : staffMember
        )))
        return response
    }

    const handleDelete = async (item) => {
        await deleteStaff(item.id)
        setStaff((current) => current.filter((staffMember) => staffMember.id !== item.id))
    }

    return {
        error,
        fields,
        handleCreate,
        handleDelete,
        handleUpdate,
        loading,
        setView,
        staff,
        view,
    }

}