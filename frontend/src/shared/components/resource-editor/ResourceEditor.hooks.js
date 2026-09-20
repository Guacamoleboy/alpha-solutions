// Pathing
// _______
// src/shared/components/resource-editor/ResourceEditor.hooks.js

import {useState} from 'react'
import useNotification from '@/shared/hooks/useNotification'

export const useResourceEditor = ({title, onUpdate, onDelete, toPayload}) => {
    const [busyId, setBusyId] = useState(null)
    const {notify} = useNotification()

    const handleUpdate = async (event, item, values) => {
        event.preventDefault()
        setBusyId(item.id)

        try {
            await onUpdate(item, toPayload(item, values))
            notify(`${title} blev opdateret`, 'success')
        } catch (updateError) {
            console.error(`Failed to update ${title}:`, updateError)
            notify(updateError.message || `${title} kunne ikke opdateres`, 'error')
        } finally {
            setBusyId(null)
        }
    }

    const handleDelete = async (item) => {
        setBusyId(item.id)

        try {
            await onDelete(item)
            notify(`${title} blev slettet`, 'success')
        } catch (deleteError) {
            console.error(`Failed to delete ${title}:`, deleteError)
            notify(deleteError.message || `${title} kunne ikke slettes`, 'error')
        } finally {
            setBusyId(null)
        }
    }

    return {
        busyId,
        handleDelete,
        handleUpdate,
    }
}
