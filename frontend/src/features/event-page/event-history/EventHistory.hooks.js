// Pathing
// _______
// src/features/event-page/event-history/EventHistory.hooks.js

import {useEffect, useMemo, useState} from 'react'
import {getMemberEventRequests} from '@/api/endpoints/eventRequest'
import useNotification from '@/shared/hooks/useNotification'

// ------------------------------------------------------------------------------------------------------

const tabs = [
    {key: 'active', label: 'AKTIVE'},
    {key: 'pending', label: 'AFVENTER'},
    {key: 'held', label: 'HOLDT'},
    {key: 'cancelled', label: 'ANNULERET'},
]

// ------------------------------------------------------------------------------------------------------

const emptyEvents = {
    pending: [],
    active: [],
    held: [],
    cancelled: [],
}

// ------------------------------------------------------------------------------------------------------

const statusToTab = {
    PENDING: 'pending',
    ACCEPTED: 'active',
    PASSED: 'held',
    DENIED: 'cancelled',
}

// ------------------------------------------------------------------------------------------------------

export const useEventHistory = () => {

    const [activeTab, setActiveTab] = useState('active')
    const [events, setEvents] = useState(emptyEvents)
    const {notify} = useNotification()

    useEffect(() => {

        let mounted = true

        getMemberEventRequests()
            .then((response) => {
                const groupedEvents = Object.keys(emptyEvents).reduce((groups, key) => ({
                    ...groups,
                    [key]: [],
                }), {})
                const requests = response?.data || []
                const seenEventIds = new Set()

                requests.forEach((event) => {
                    const tab = statusToTab[event.status]
                    if (tab && !seenEventIds.has(event.id)) {
                        seenEventIds.add(event.id)
                        groupedEvents[tab].push(event)
                    }
                })

                if (mounted) {
                    setEvents(groupedEvents)
                }
            })
            .catch((error) => {
                if (mounted) {
                    notify(error.message || 'Events kunne ikke hentes.', 'error')
                }
            })

        return () => {
            mounted = false
        }

    }, [notify])

    return {
        activeTab,
        events: useMemo(() => events[activeTab] || [], [activeTab, events]),
        setActiveTab,
        tabs,
    }

}