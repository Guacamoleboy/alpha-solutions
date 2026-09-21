// Pathing
// _______
// src/features/owner-dashboard/courts/Courts.hooks.js

import {useEffect, useState} from 'react'
import {createCourt, deleteCourt, getCourts, updateCourt} from '@/api/endpoints/court'
import useNotification from '@/shared/hooks/useNotification'

// TODO: Need to find a better way to do this. Probably a JSON map using data/ instead.
const fields = [
    {key: 'name', itemKey: 'name', label: 'Navn', required: true, updateRequired: false},
    {
        key: 'active',
        itemKey: 'active',
        label: 'Aktiv',
        required: true,
        updateRequired: false,
        options: [
            {value: '', label: 'Vælg status'},
            {value: 'true', label: 'Ja'},
            {value: 'false', label: 'Nej'},
        ],
    },
    {
        key: 'surface',
        updateRequired: false,
        itemKey: 'surface',
        label: 'Underlag',
        options: [
            {value: '', label: 'Vælg underlag'},
            ...['CONCRETE', 'ASPHALT', 'SYNTHETIC', 'INDOOR', 'ACRYLIC', 'CLAY', 'CARPET', 'GRASS', 'TURF', 'OTHER']
                .map((surface) => ({value: surface, label: surface})),
        ],
    },
    {
        key: 'required_membership_id',
        updateRequired: false,
        itemKey: 'required_membership_id',
        label: 'Påkrævet medlemskab',
        options: [
            {value: '', label: 'Ingen krav'},
            {value: '1', label: 'Free'},
            {value: '2', label: 'Basic'},
            {value: '3', label: 'Premium'},
            {value: '4', label: 'Super Premium'},
        ],
    },
    {key: 'latitude', itemKey: 'latitude', label: 'Breddegrad', type: 'number', updateRequired: false},
    {key: 'longitude', itemKey: 'longitude', label: 'Længdegrad', type: 'number', updateRequired: false},
    {key: 'orientation_degrees', itemKey: 'orientation_degrees', label: 'Orientering', type: 'number', updateRequired: false},
    {key: 'elevation', itemKey: 'elevation', label: 'Højde', type: 'number', updateRequired: false},
]

// ------------------------------------------------------------------------------------------------------

// TODO: Need to find a better way to do this.
const toPayload = (item, values) => ({
    id: item?.id,
    name: values.name ?? item?.name,
    active: values.active !== undefined && values.active !== ''
        ? values.active === 'true'
        : item?.active,
    surface: values.surface || item?.surface,
    latitude: values.latitude ?? item?.latitude,
    longitude: values.longitude ?? item?.longitude,
    orientation_degrees: values.orientation_degrees ?? item?.orientation_degrees,
    elevation: values.elevation ?? item?.elevation,
    required_membership_id: values.required_membership_id !== undefined
        ? (values.required_membership_id === '' ? null : Number(values.required_membership_id))
        : item?.required_membership_id ?? null,
})

// ------------------------------------------------------------------------------------------------------

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