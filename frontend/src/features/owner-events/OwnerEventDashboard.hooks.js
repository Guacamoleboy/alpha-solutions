// Pathing
// _______
// src/features/owner-events/OwnerEventDashboard.hooks.js

import {useEffect, useMemo, useState} from 'react'
import {useLocation, useSearchParams} from 'react-router-dom'
import useForm from '@/shared/hooks/useForm'
import {getEventRequests, updateEventRequest} from '@/api/endpoints/eventRequest'
import {getMembers} from '@/api/endpoints/member'
import {createEventOrganizer, deleteEventOrganizer, getEventOrganizers} from '@/api/endpoints/eventOrganizer'
import {getCourts} from '@/api/endpoints/court'
import {getBookings} from '@/api/endpoints/booking'
import {getEventCourtReservations, createEventCourtReservation, deleteEventCourtReservation} from '@/api/endpoints/eventCourtReservation'
import useNotification from '@/shared/hooks/useNotification'

// ------------------------------------------------------------------------------------------------------

const unwrap = (response) => response?.data || []

// ------------------------------------------------------------------------------------------------------

const selectAvailableCourts = ({count, startTime, endTime, eventId, courts, bookings, reservations, events, excludedCourtIds = []}) => {
    const start = new Date(startTime)
    const end = new Date(endTime)
    const unavailable = new Set(excludedCourtIds.map(String))
    bookings.filter((booking) => booking.status !== 'CANCELLED'
        && new Date(booking.start_time) < end && new Date(booking.end_time) > start)
        .forEach((booking) => unavailable.add(String(booking.court_id)))
    const eventById = new Map(events.map((item) => [String(item.id), item]))
    reservations.filter((reservation) => String(reservation.event_request_id) !== String(eventId))
        .forEach((reservation) => {
            const reservedEvent = eventById.get(String(reservation.event_request_id))
            if (reservedEvent?.status === 'ACCEPTED'
                && new Date(reservedEvent.start_time) < end && new Date(reservedEvent.end_time) > start) {
                unavailable.add(String(reservation.court_id))
            }
        })
    const available = courts.filter((court) => court.active && !unavailable.has(String(court.id)))
    for (let index = available.length - 1; index > 0; index -= 1) {
        const swapIndex = Math.floor(Math.random() * (index + 1))
        ;[available[index], available[swapIndex]] = [available[swapIndex], available[index]]
    }
    if (count < 0 || available.length < count) {
        throw new Error('Eventet kan ikke afholdes - tryk venligst annuller event')
    }
    return available.slice(0, count).map((court) => court.id)
}

// ------------------------------------------------------------------------------------------------------

export const useOwnerEventDashboard = () => {

    const [events, setEvents] = useState([])
    const [filter, setFilter] = useState('')
    const [statusFilter, setStatusFilter] = useState({locationKey: null, value: 'ALL'})
    const [selectedEvent, setSelectedEvent] = useState(null)
    const [loading, setLoading] = useState(true)
    const [searchParams] = useSearchParams()
    const location = useLocation()
    const {notify} = useNotification()
    const activeStatus = statusFilter.locationKey === location.key ? statusFilter.value : 'ALL'
    const setActiveStatus = (value) => setStatusFilter({locationKey: location.key, value})

    useEffect(() => {
        let active = true
        Promise.all([getEventRequests(), getMembers()])
            .then(([eventResponse, memberResponse]) => {
                if (!active) return
                const membersById = new Map(unwrap(memberResponse).map((member) => [member.id, member]))
                const requests = unwrap(eventResponse).map((event) => ({
                    ...event,
                    requesterEmail: membersById.get(event.requester_id)?.email,
                })).sort((left, right) => new Date(left.start_time) - new Date(right.start_time))
                setEvents(requests)
                const requestedId = Number(searchParams.get('event'))
                setSelectedEvent(requests.find((event) => event.id === requestedId) || null)
            })
            .catch((error) => notify(error.message || 'Events kunne ikke hentes.', 'error'))
            .finally(() => { if (active) setLoading(false) })
        return () => { active = false }
    }, [notify, searchParams])

    const visibleEvents = useMemo(() => {
        const term = filter.trim().toLocaleLowerCase('da-DK')
        return events.filter((event) => (
            (activeStatus === 'ALL' || event.status === activeStatus)
            && (!term || `${event.name} ${event.organizer_email || event.requesterEmail || ''}`.toLocaleLowerCase('da-DK').includes(term))
        ))
    }, [activeStatus, events, filter])

    const replaceEvent = (nextEvent) => {
        setEvents((current) => current.map((event) => event.id === nextEvent.id ? nextEvent : event))
        setSelectedEvent(nextEvent)
    }

    return {
        events: visibleEvents,
        filter,
        setFilter,
        selectedEvent,
        selectEvent: setSelectedEvent,
        loading,
        replaceEvent,
        activeStatus,
        setActiveStatus,
    }
}

// ------------------------------------------------------------------------------------------------------

const toInputDate = (date) => date ? date.slice(0, 16) : ''

// ------------------------------------------------------------------------------------------------------

const toEventSummary = (values, status) => ({
    name: values.name,
    start_time: `${values.startTime}:00`,
    end_time: `${values.endTime}:00`,
    guest_count: Number(values.guestCount),
    requested_court_count: Math.ceil(Number(values.guestCount) / 4),
    organizer_email: values.organizerEmail.trim(),
    equipment_required: Boolean(values.equipmentRequired),
    event_code: values.eventCode.trim() || null,
    status,
})

// ------------------------------------------------------------------------------------------------------

export const useOwnerEventDetails = (event, onEventUpdated) => {
    const initialValues = {
        name: event.name,
        startTime: toInputDate(event.start_time),
        endTime: toInputDate(event.end_time),
        guestCount: event.guest_count,
        organizerEmail: event.organizer_email || event.requesterEmail || '',
        equipmentRequired: Boolean(event.equipment_required),
        eventCode: event.event_code || '',
    }
    const form = useForm(initialValues)
    const [courts, setCourts] = useState([])
    const [members, setMembers] = useState([])
    const [organizerRecords, setOrganizerRecords] = useState([])
    const [selectedOrganizers, setSelectedOrganizers] = useState([])
    const [organizerInput, setOrganizerInput] = useState('')
    const [reservations, setReservations] = useState([])
    const [allReservations, setAllReservations] = useState([])
    const [bookings, setBookings] = useState([])
    const [otherEvents, setOtherEvents] = useState([])
    const [detailsLoading, setDetailsLoading] = useState(true)
    const [saving, setSaving] = useState(false)
    const {notify} = useNotification()

    useEffect(() => {
        let active = true
        Promise.all([getCourts(), getEventOrganizers(), getMembers(), getEventCourtReservations(), getBookings(), getEventRequests()])
            .then(async ([courtResponse, organizerResponse, memberResponse, reservationResponse, bookingResponse, eventResponse]) => {
                if (!active) return
                const allEventReservations = unwrap(reservationResponse)
                const reservationsForEvent = allEventReservations.filter((item) => item.event_request_id === event.id)
                const allCourts = unwrap(courtResponse)
                const allBookings = unwrap(bookingResponse)
                const eventsBesidesCurrent = unwrap(eventResponse).filter((item) => item.id !== event.id)
                const allMembers = unwrap(memberResponse)
                const membersById = new Map(allMembers.map((member) => [member.id, member]))
                const eventOrganizers = unwrap(organizerResponse)
                    .filter((item) => item.event_request_id === event.id)
                    .map((item) => ({id: item.id, memberId: item.member_id, email: membersById.get(item.member_id)?.email}))
                    .filter((item) => item.email)
                setCourts(allCourts)
                setMembers(allMembers)
                setOrganizerRecords(eventOrganizers)
                setSelectedOrganizers(eventOrganizers)
                setReservations(reservationsForEvent)
                setAllReservations(allEventReservations)
                setBookings(allBookings)
                setOtherEvents(eventsBesidesCurrent)

                const requiredCourtCount = Math.ceil(Number(event.guest_count) / 4)
                const missingCourtCount = requiredCourtCount - reservationsForEvent.length
                if (event.status === 'ACCEPTED' && missingCourtCount > 0) {
                    const additionalCourtIds = selectAvailableCourts({
                        count: missingCourtCount,
                        startTime: event.start_time,
                        endTime: event.end_time,
                        eventId: event.id,
                        courts: allCourts,
                        bookings: allBookings,
                        reservations: allEventReservations,
                        events: eventsBesidesCurrent,
                        excludedCourtIds: reservationsForEvent.map((item) => item.court_id),
                    })
                    for (const courtId of additionalCourtIds) {
                        await createEventCourtReservation({event_request_id: event.id, court_id: courtId})
                    }
                    const updatedReservations = unwrap(await getEventCourtReservations())
                    if (active) {
                        setAllReservations(updatedReservations)
                        setReservations(updatedReservations.filter((item) => item.event_request_id === event.id))
                    }
                }
            })
            .catch((error) => notify(error.message || 'Eventdetaljer kunne ikke hentes.', 'error'))
            .finally(() => { if (active) setDetailsLoading(false) })
        return () => { active = false }
    }, [event.end_time, event.guest_count, event.id, event.start_time, event.status, notify])

    const normalizedValues = {
        ...form.values,
        guestCount: Number(form.values.guestCount),
        organizerEmail: form.values.organizerEmail?.trim(),
        equipmentRequired: Boolean(form.values.equipmentRequired),
    }
    const isDirty = JSON.stringify(normalizedValues) !== JSON.stringify(initialValues)
        || event.status !== 'ACCEPTED'
        || [...selectedOrganizers.map((item) => item.memberId)].sort().join(',')
            !== [...organizerRecords.map((item) => item.memberId)].sort().join(',')

    const handleAddOrganizer = () => {
        const email = organizerInput.trim().toLowerCase()
        const member = members.find((item) => item.email?.toLowerCase() === email)
        if (!member) {
            notify('Der blev ikke fundet et medlem med den email.', 'error')
            return
        }
        if (member.id === event.requester_id || selectedOrganizers.some((item) => item.memberId === member.id)) {
            notify('Medlemmet er allerede arrangør på dette event.', 'error')
            return
        }
        if (selectedOrganizers.length >= 3) {
            notify('Et event kan højst have tre medarrangører.', 'error')
            return
        }
        setSelectedOrganizers((current) => [...current, {memberId: member.id, email: member.email}])
        setOrganizerInput('')
    }

    const persist = async (nextStatus) => {
        setSaving(true)
        try {
            const requestedCourtCount = Math.ceil(Number(form.values.guestCount) / 4)
            let courtIdsToAssign = []
            if (nextStatus === 'ACCEPTED') {
                courtIdsToAssign = selectAvailableCourts({
                    count: requestedCourtCount,
                    startTime: form.values.startTime,
                    endTime: form.values.endTime,
                    eventId: event.id,
                    courts,
                    bookings,
                    reservations: allReservations,
                    events: otherEvents,
                })
            }
            const [date, startTime] = form.values.startTime.split('T')
            const [endDate, endTime] = form.values.endTime.split('T')
            const updated = await updateEventRequest(event.id, {
                id: event.id,
                name: form.values.name,
                startTime: `${date}T${startTime}:00`,
                endTime: `${endDate}T${endTime}:00`,
                guestCount: Number(form.values.guestCount),
                requestedCourtCount,
                equipmentRequired: Boolean(form.values.equipmentRequired),
                eventCode: form.values.eventCode.trim() || null,
                organizerEmail: form.values.organizerEmail.trim(),
                status: nextStatus,
                requester: {id: event.requester_id},
            })
            const selectedRecordIds = selectedOrganizers.map((item) => item.id).filter(Boolean)
            const removedOrganizers = organizerRecords.filter((item) => !selectedRecordIds.includes(item.id))
            const addedOrganizers = selectedOrganizers.filter((item) => !item.id)
            await Promise.all(removedOrganizers.map((item) => deleteEventOrganizer(item.id)))
            await Promise.all(addedOrganizers.map((item) => createEventOrganizer({
                event_request_id: event.id,
                member_id: item.memberId,
            })))
            for (const reservation of reservations) {
                await deleteEventCourtReservation(reservation.id)
            }
            for (const courtId of courtIdsToAssign) {
                await createEventCourtReservation({event_request_id: event.id, court_id: courtId})
            }
            const reservationResponse = await getEventCourtReservations()
            const updatedReservations = unwrap(reservationResponse)
            setAllReservations(updatedReservations)
            setReservations(updatedReservations.filter((item) => item.event_request_id === event.id))
            const organizerResponse = await getEventOrganizers()
            const memberResponse = await getMembers()
            const membersById = new Map(unwrap(memberResponse).map((member) => [member.id, member]))
            const updatedOrganizers = unwrap(organizerResponse)
                .filter((item) => item.event_request_id === event.id)
                .map((item) => ({id: item.id, memberId: item.member_id, email: membersById.get(item.member_id)?.email}))
                .filter((item) => item.email)
            setOrganizerRecords(updatedOrganizers)
            setSelectedOrganizers(updatedOrganizers)
            notify('Eventet er opdateret.', 'success')
            return updated
        } catch (error) {
            notify(error.message || 'Eventet kunne ikke opdateres.', 'error')
            return null
        } finally {
            setSaving(false)
        }
    }

    const updateEvent = async (submitEvent) => {
        submitEvent.preventDefault()
        const result = await persist('ACCEPTED')
        if (result) onEventUpdated({...event, ...toEventSummary(form.values, 'ACCEPTED')})
    }

    const closeEvent = async () => {
        const result = await persist('DENIED')
        if (result) onEventUpdated({...event, ...toEventSummary(form.values, 'DENIED')})
    }

    return {
        form,
        organizerInput,
        setOrganizerInput,
        selectedOrganizers,
        setSelectedOrganizers,
        handleAddOrganizer,
        isDirty,
        reservations,
        courts,
        detailsLoading,
        saving,
        updateEvent,
        closeEvent,
    }

}