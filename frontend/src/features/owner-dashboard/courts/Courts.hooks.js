// Pathing
// _______
// src/features/owner-dashboard/courts/Courts.hooks.js

import {useEffect, useState} from 'react'
import {createCourt, deleteCourt, getCourts, updateCourt} from '@/api/endpoints/court'
import useNotification from '@/shared/hooks/useNotification'

const fields = [
    {key: 'name', itemKey: 'name', label: 'Navn', required: true},
    {
        key: 'active',
        itemKey: 'active',
        label: 'Aktiv',
        required: true,
        options: [
            {value: '', label: 'Vælg status'},
            {value: 'true', label: 'Ja'},
            {value: 'false', label: 'Nej'},
        ],
    },
    {
        key: 'surface',
        itemKey: 'surface',
        label: 'Underlag',
        options: [
            {value: '', label: 'Vælg underlag'},
            ...['CONCRETE', 'ASPHALT', 'SYNTHETIC', 'INDOOR', 'ACRYLIC', 'CLAY', 'CARPET', 'GRASS', 'TURF', 'OTHER']
                .map((surface) => ({value: surface, label: surface})),
        ],
    },
    {key: 'latitude', itemKey: 'latitude', label: 'Breddegrad', type: 'number'},
    {key: 'longitude', itemKey: 'longitude', label: 'Længdegrad', type: 'number'},
    {key: 'orientation_degrees', itemKey: 'orientation_degrees', label: 'Orientering', type: 'number'},
    {key: 'elevation', itemKey: 'elevation', label: 'Højde', type: 'number'},
]

const toPayload = (item, values) => ({
    id: item?.id,
    name: values.name ?? item?.name,
    active: values.active !== undefined && values.active !== ''
        ? values.active === 'true'
        : item?.active,
    surface: values.surface || item?.surface,
    latitude: values.latitude ?? item?.latitude,
    longitude: values.longitude ?? item?.longitude,
    orientationDegrees: values.orientation_degrees ?? item?.orientation_degrees,
    elevation: values.elevation ?? item?.elevation,
})

export const useCourts = () => {
    const [courts, setCourts] = useState([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(null)
    const [view, setView] = useState('home')
    const {notify} = useNotification()

    useEffect(() => {
        getCourts()
            .then((response) => setCourts(response.data || []))
            .catch((loadError) => {
                setError(loadError)
                notify(loadError.message || 'Baner kunne ikke hentes', 'error')
            })
            .finally(() => setLoading(false))
    }, [notify])

    const handleCreate = async (values) => {
        const response = await createCourt(toPayload(null, values))
        setCourts((current) => [...current, response.data])
        return response
    }

    const handleUpdate = async (item, values) => {
        const response = await updateCourt(item.id, values)
        setCourts((current) => current.map((court) => (
            court.id === item.id ? response.data : court
        )))
        return response
    }

    const handleDelete = async (item) => {
        await deleteCourt(item.id)
        setCourts((current) => current.filter((court) => court.id !== item.id))
    }

    return {
        courts,
        error,
        fields,
        handleCreate,
        handleDelete,
        handleUpdate,
        loading,
        setView,
        toPayload,
        view,
    }
}
