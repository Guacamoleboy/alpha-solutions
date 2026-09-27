// Pathing
// _______
// src/features/member-booking-page/court-booking/CourtBooking.hooks.js

import {useEffect, useState} from 'react'
import { createBooking } from '@/api/endpoints/booking'
import {getEventRequests} from '@/api/endpoints/eventRequest'
import {getEventCourtReservations} from '@/api/endpoints/eventCourtReservation'
import useForm from '@/shared/hooks/useForm'
import useNotification from '@/shared/hooks/useNotification'
import { useOperatingHours } from '@/shared/hooks/useOperatingHours'
import { useCourt } from '@/shared/hooks/useCourt'
import useMember from '@/shared/hooks/useMember'
import { getDayOfWeek, getAvailableTimes,getBookingEndTime } from './bookingTime'

export const useCourtBooking = () => {

    // Form hook setup
    const {
        values,
        setValues,
        registerField,
        reset,
    } = useForm()

    // State
    const [availableTimes, setAvailableTimes] = useState([])
    const [availableCourts, setAvailableCourts] = useState([])
    const [isClosed, setIsClosed] = useState(false)
    const [eventReservations, setEventReservations] = useState([])

    // Operating hours setup
    const { operatingHours } = useOperatingHours()

    // Court setup
    const { courts } = useCourt()
    const { member } = useMember()

    // Notification setup
    const { notify } = useNotification()

    useEffect(() => {
        let active = true
        Promise.all([getEventRequests(), getEventCourtReservations()])
            .then(([eventResponse, reservationResponse]) => {
                if (active) {
                    setEventReservations((reservationResponse?.data || []).map((reservation) => ({
                        ...reservation,
                        event: (eventResponse?.data || []).find((event) => event.id === reservation.event_request_id),
                    })).filter((reservation) => reservation.event?.status === 'ACCEPTED'))
                }
            })
            .catch((error) => notify(error.message || 'Eventreservationer kunne ikke hentes.', 'error'))
        return () => { active = false }
    }, [notify])

    // TODO: Move this to dateTime to prevent redundant code (same in bookingTime.js)
    // Get todays date
    const today = new Date()
    const minDate = [
        today.getFullYear(),
        String(today.getMonth() + 1).padStart(2, '0'),
        String(today.getDate()).padStart(2, '0'),
    ].join('-')

    // Handle date
    const handleDateChange = (e) => {
        const date = e.target.value

        setValues({
            date,
            time: '',
            court: '',
        })

        setAvailableTimes([])
        setAvailableCourts([])
        setIsClosed(false)

        if (!date) {
            return
        }

        const dayOfWeek = getDayOfWeek(date)

        // Find operating hours for the selected day
        const operatingHour = (operatingHours ?? [])
            .find((hour) => hour.day_of_week === dayOfWeek)

        if (!operatingHour || operatingHour.closed) {
            setIsClosed(true)
            return
        }

        setAvailableTimes(getAvailableTimes(date, operatingHour))

    }

    // Handle time
    const handleTimeChange = (e) => {
        const time = e.target.value
        setValues(prev => ({
            ...prev,
            time,
            court: '',
        }))
        const startTime = `${values.date}T${time}:00`
        const endTime = getBookingEndTime(values.date, time)
        const reservedCourtIds = new Set(eventReservations
            .filter(({event}) => event.start_time < endTime && event.end_time > startTime)
            .map(({court_id}) => court_id))
        setAvailableCourts(
            courts.filter(
                (court) => court.active && (
                    (court.required_membership_id === null
                    || court.required_membership_id === undefined)
                    || (member?.membership_id !== null
                    && member?.membership_id !== undefined
                    && member.membership_id >= court.required_membership_id)
                ) && !reservedCourtIds.has(court.id)
            )
        )
    }

    // Handle booking
    const handleBooking = async (e) => {
        e.preventDefault()
        try {
            const startTime = `${values.date}T${values.time}:00`
            const endTime = getBookingEndTime(
                values.date,
                values.time
            )
            await createBooking({
                court_id: Number(values.court),
                start_time: startTime,
                end_time: endTime,
            })
            notify('Bane blev booket', 'success')
            // Reset form
            reset()
            setAvailableTimes([])
            setAvailableCourts([])
            setIsClosed(false)
        } catch (error) {
            console.error('Failed to create booking:', error)
            notify(error.message || 'Bane kunne ikke bookes', 'error')
        }
    }

    return {
        values,
        registerField,
        availableTimes,
        availableCourts,
        isClosed,
        minDate,
        handleDateChange,
        handleTimeChange,
        handleBooking,
    }

}