// Pathing
// _______
// src/features/owner-dashboard/operating-hours/OperatingHours.hooks.js

import {useEffect, useState} from 'react'
import {createOperatingHour, deleteOperatingHour, getOperatingHours, updateOperatingHour} from '@/api/endpoints/operating-hour'
import useNotification from '@/shared/hooks/useNotification'

const dayOptions = [
    {value: '', label: 'Vælg dag'},
    ...['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY']
        .map((day) => ({value: day, label: day})),
]

const fields = [
    {key: 'day_of_week', itemKey: 'day_of_week', label: 'Dag', options: dayOptions, required: true},
    {key: 'open_time', itemKey: 'open_time', label: 'Åbner', type: 'time', showValue: true},
    {key: 'close_time', itemKey: 'close_time', label: 'Lukker', type: 'time', showValue: true},
    {
        key: 'closed',
        itemKey: 'closed',
        label: 'Lukket',
        required: true,
        options: [
            {value: '', label: 'Vælg status'},
            {value: 'true', label: 'Ja'},
            {value: 'false', label: 'Nej'},
        ],
    },
]

const toPayload = (item, values) => ({
    id: item?.id,
    dayOfWeek: values.day_of_week || item?.day_of_week,
    openTime: values.open_time ?? item?.open_time,
    closeTime: values.close_time ?? item?.close_time,
    closed: values.closed !== undefined && values.closed !== ''
        ? values.closed === 'true'
        : item?.closed,
})

export const useOperatingHours = () => {
    const [operatingHours, setOperatingHours] = useState([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(null)
    const [view, setView] = useState('home')
    const {notify} = useNotification()

    useEffect(() => {
        getOperatingHours()
            .then((response) => setOperatingHours(response.data || []))
            .catch((loadError) => {
                setError(loadError)
                notify(loadError.message || 'Åbningstider kunne ikke hentes', 'error')
            })
            .finally(() => setLoading(false))
    }, [notify])

    const handleCreate = async (values) => {
        const response = await createOperatingHour(toPayload(null, values))
        setOperatingHours((current) => [...current, response.data])
        return response
    }

    const handleUpdate = async (item, values) => {
        const response = await updateOperatingHour(item.id, values)
        setOperatingHours((current) => current.map((operatingHour) => (
            operatingHour.id === item.id ? response.data : operatingHour
        )))
        return response
    }

    const handleDelete = async (item) => {
        await deleteOperatingHour(item.id)
        setOperatingHours((current) => current.filter((operatingHour) => operatingHour.id !== item.id))
    }

    return {
        error,
        fields,
        handleCreate,
        handleDelete,
        handleUpdate,
        loading,
        operatingHours,
        setView,
        toPayload,
        view,
    }
}
