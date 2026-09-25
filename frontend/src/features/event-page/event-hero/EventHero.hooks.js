// Pathing
// _______
// src/features/event-page/event-hero/EventHero.hooks.js

import {useEffect, useState} from 'react'
import {getMemberEventRequests} from '@/api/endpoints/eventRequest'
import useNotification from '@/shared/hooks/useNotification'

// ------------------------------------------------------------------------------------------------------

export const useEventHero = () => ({
    columns: 4,
    rows: 1,
    ...useUpcomingEvent(),
})

// ------------------------------------------------------------------------------------------------------

const useUpcomingEvent = () => {

    const [nextEvent, setNextEvent] = useState(null)
    const {notify} = useNotification()

    useEffect(() => {
        let mounted = true

        getMemberEventRequests()
            .then((response) => {
                const now = new Date()
                const upcoming = (response?.data || [])
                    .filter((event) => event.status === 'ACCEPTED' && event.start_time && new Date(event.start_time) > now)
                    .sort((left, right) => new Date(left.start_time) - new Date(right.start_time))

                if (mounted) {
                    setNextEvent(upcoming[0] || null)
                }
            })
            .catch((error) => {
                if (mounted) {
                    notify(error.message || 'Kommende events kunne ikke hentes.', 'error')
                }
            })

        return () => {
            mounted = false
        }
    }, [notify])

    const daysUntilNextEvent = nextEvent
        ? Math.max(0, Math.ceil((new Date(nextEvent.start_time) - new Date()) / (1000 * 60 * 60 * 24)))
        : null

    return {
        upcomingEventText: daysUntilNextEvent === null
            ? 'Du har ingen kommende events.'
            : `${daysUntilNextEvent} ${daysUntilNextEvent === 1 ? 'dag' : 'dage'}`,
    }

}