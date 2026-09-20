// Pathing
// _______
// src/shared/components/resource-create/ResourceCreate.hooks.js

import {useState} from 'react'
import useNotification from '@/shared/hooks/useNotification'
import useForm from '@/shared/hooks/useForm'

export const useResourceCreate = ({title, onCreate, onCreated, toPayload}) => {
    const [busy, setBusy] = useState(false)
    const {notify} = useNotification()
    const {registerField, reset, values} = useForm()

    const handleCreate = async (event) => {
        event.preventDefault()
        setBusy(true)

        try {
            const response = await onCreate(toPayload(values))
            notify(`${title} blev oprettet`, 'success')
            reset()
            onCreated?.(response)
        } catch (createError) {
            console.error(`Failed to create ${title}:`, createError)
            notify(createError.message || `${title} kunne ikke oprettes`, 'error')
        } finally {
            setBusy(false)
        }
    }

    return {
        busy,
        handleCreate,
        registerField,
    }
}
