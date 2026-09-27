// Pathing
// _______
// src/features/event-page/event-history/EventHistory.hooks.js

import {useEffect, useMemo, useState} from 'react'
import {getMemberEventRequests} from '@/api/endpoints/eventRequest'
import {getCourts} from '@/api/endpoints/court'
import {getEventCourtReservations} from '@/api/endpoints/eventCourtReservation'
import useNotification from '@/shared/hooks/useNotification'

// ------------------------------------------------------------------------------------------------------

const tabs = [
    {key: 'active', label: 'AKTIV'},
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

        Promise.all([getMemberEventRequests(), getCourts(), getEventCourtReservations()])
            .then(([response, courtResponse, reservationResponse]) => {
                const groupedEvents = Object.keys(emptyEvents).reduce((groups, key) => ({
                    ...groups,
                    [key]: [],
                }), {})
                const requests = response?.data || []
                const courtsById = new Map((courtResponse?.data || []).map((court) => [court.id, court.name]))
                const reservationsByEventId = new Map()
                ;(reservationResponse?.data || []).forEach((reservation) => {
                    const names = reservationsByEventId.get(reservation.event_request_id) || []
                    names.push(courtsById.get(reservation.court_id) || `Bane ${reservation.court_id}`)
                    reservationsByEventId.set(reservation.event_request_id, names)
                })
                const seenEventIds = new Set()

                requests.forEach((event) => {
                    const tab = statusToTab[event.status]
                    if (tab && !seenEventIds.has(event.id)) {
                        seenEventIds.add(event.id)
                        groupedEvents[tab].push({
                            ...event,
                            courts: event.status === 'ACCEPTED' ? reservationsByEventId.get(event.id) || [] : [],
                        })
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