// Pathing
// _______
// src/features/event-page/event-create/EventCreate.hooks.js

import { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import useForm from '@/shared/hooks/useForm'
import { createEventRequest, getEventRequests } from '@/api/endpoints/eventRequest'
import { getCourts } from '@/api/endpoints/court'
import { getBookings } from '@/api/endpoints/booking'
import { createEventCourtReservation, getEventCourtReservations } from '@/api/endpoints/eventCourtReservation'
import useNotification from '@/shared/hooks/useNotification'
import { getAvailableTimes, getDayOfWeek } from '@/features/member-booking-page/court-booking/bookingTime'
import { useOperatingHours } from '@/shared/hooks/useOperatingHours'
import {useAuth} from '@/shared/hooks/useAuth'

// ------------------------------------------------------------------------------------------------------

const initialValues = {
    name: '',
    date: '',
    startTime: '',
    endTime: '',
    guests: 1,
    courts: 1,
    equipmentRequired: false,
    organizerOne: '',
    organizerTwo: '',
    organizerThree: '',
    eventCode: '',
}

// ------------------------------------------------------------------------------------------------------

const dayNames = {
    SUNDAY: 'søndag',
    MONDAY: 'mandag',
    TUESDAY: 'tirsdag',
    WEDNESDAY: 'onsdag',
    THURSDAY: 'torsdag',
    FRIDAY: 'fredag',
    SATURDAY: 'lørdag',
}

// ------------------------------------------------------------------------------------------------------

const toMinutes = (time) => {
    const [hours, minutes] = time.split(':').map(Number)
    return hours * 60 + minutes
}

// ------------------------------------------------------------------------------------------------------

export const useEventCreate = () => {

    // Setup
    const { registerField, values, setValues } = useForm(initialValues)
    const { notify } = useNotification()
    const { operatingHours, error: operatingHoursError } = useOperatingHours()
    const navigate = useNavigate()
    const {role} = useAuth()
    const isOwner = role === 'OWNER'
    const eventsPath = isOwner ? '/dashboard/events' : '/member/events'

    // Date
    const today = new Date()
    const minDate = [
        today.getFullYear(),
        String(today.getMonth() + 1).padStart(2, '0'),
        String(today.getDate()).padStart(2, '0'),
    ].join('-')

    // Fail
    useEffect(() => {
        if (operatingHoursError) {
            notify('Åbningstider kunne ikke hentes.', 'error')
        }
    }, [notify, operatingHoursError])
    
    const handleEquipmentChange = (event) => {
        setValues((current) => ({
            ...current,
            equipmentRequired: event.target.checked,
        }))
    }

    const handleDateChange = (event) => {
        const date = event.target.value
        setValues((current) => ({
            ...current,
            date,
            startTime: '',
            endTime: '',
        }))
    }

    const handleStartTimeChange = (event) => {
        setValues((current) => ({
            ...current,
            startTime: event.target.value,
            endTime: '',
        }))
    }

    const handleIntegerChange = (event) => {
        const { name, value } = event.target
        const integerValue = value.replace(',', '.').split('.')[0].replace(/\D/g, '')
        setValues((current) => ({
            ...current,
            [name]: integerValue,
            ...(name === 'guests' ? {courts: String(Math.ceil(Number(integerValue || 0) / 4))} : {}),
        }))
    }

    // TODO: FIX THIS CLUTTER AT SOME POINT
    const operatingHour = values.date
        ? operatingHours.find((hour) => hour.day_of_week === getDayOfWeek(values.date))
        : null
    const isClosed = Boolean(values.date && operatingHour?.closed)
    const closedLabel = isClosed ? `Lukket ${dayNames[getDayOfWeek(values.date)]}` : null
    const availableTimes = operatingHour && !operatingHour.closed
        ? getAvailableTimes(values.date, operatingHour)
        : []
    const startMinutes = values.startTime ? toMinutes(values.startTime) : null
    const closingTime = operatingHour?.close_time?.slice(0, 5)
    const closingOption = closingTime ? [{ value: closingTime, label: closingTime }] : []
    const endTimes = startMinutes === null
        ? []
        : availableTimes
            .filter((time) => toMinutes(time.value) > startMinutes)
            .concat(closingOption)
            .filter((time, index, times) => times.findIndex((item) => item.value === time.value) === index)

    const handleSubmit = async (event) => {
        event.preventDefault()

        try {
            if (values.name.trim().length > 100) {
                throw new Error('Eventnavn må højst være 100 tegn.')
            }

            const organizerEmails = [values.organizerOne, values.organizerTwo, values.organizerThree]
                .map((email) => email.trim())
                .filter(Boolean)

            let courtIds = []
            if (isOwner) {
                const [courtResponse, bookingResponse, reservationResponse, eventResponse] = await Promise.all([
                    getCourts(), getBookings(), getEventCourtReservations(), getEventRequests(),
                ])
                const start = new Date(`${values.date}T${values.startTime}:00`)
                const end = new Date(`${values.date}T${values.endTime}:00`)
                const unavailable = new Set()
                ;(bookingResponse?.data || []).filter((booking) => booking.status !== 'CANCELLED'
                    && new Date(booking.start_time) < end && new Date(booking.end_time) > start)
                    .forEach((booking) => unavailable.add(booking.court_id))
                const eventById = new Map((eventResponse?.data || []).filter((item) => item.status === 'ACCEPTED').map((item) => [item.id, item]))
                ;(reservationResponse?.data || []).forEach((reservation) => {
                    const reservedEvent = eventById.get(reservation.event_request_id)
                    if (reservedEvent && new Date(reservedEvent.start_time) < end && new Date(reservedEvent.end_time) > start) {
                        unavailable.add(reservation.court_id)
                    }
                })
                const available = (courtResponse?.data || []).filter((court) => court.active && !unavailable.has(court.id))
                for (let index = available.length - 1; index > 0; index -= 1) {
                    const swapIndex = Math.floor(Math.random() * (index + 1))
                    ;[available[index], available[swapIndex]] = [available[swapIndex], available[index]]
                }
                const requiredCount = Math.ceil(Number(values.guests) / 4)
                if (requiredCount < 1 || available.length < requiredCount) {
                    throw new Error('Eventet kan ikke afholdes - tryk venligst annuller event')
                }
                courtIds = available.slice(0, requiredCount).map((court) => court.id)
            }

            const response = await createEventRequest({
                name: values.name,
                start_time: `${values.date}T${values.startTime}:00`,
                end_time: `${values.date}T${values.endTime}:00`,
                guest_count: Number(values.guests),
                requested_court_count: Math.ceil(Number(values.guests) / 4),
                equipment_required: values.equipmentRequired,
                event_code: values.eventCode || null,
                organizer_emails: organizerEmails,
            })

            if (isOwner) {
                for (const courtId of courtIds) {
                    await createEventCourtReservation({
                        event_request_id: response?.data?.id,
                        court_id: courtId,
                    })
                }
            }

            navigate(isOwner ? `${eventsPath}?event=${response?.data?.id}` : eventsPath)
            notify(isOwner ? 'Eventet er oprettet og bekræftet.' : 'Eventforespørgsel sendt.', 'success')
        } catch (error) {
            notify(error.message || 'Eventet kunne ikke oprettes.', 'error')
        }
    }

    return { 
        availableTimes, 
        closedLabel, 
        endTimes, 
        handleDateChange, 
        handleEquipmentChange, 
        handleIntegerChange, 
        handleStartTimeChange, 
        handleSubmit,
        isOwner,
        minDate, 
        registerField, 
        values 
    }
    
}