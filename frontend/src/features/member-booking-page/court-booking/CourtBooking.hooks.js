// Pathing
// _______
// src/features/member-booking-page/court-booking/CourtBooking.hooks.js

import { useState } from 'react'
import { createBooking } from '@/api/endpoints/booking'
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

    // Operating hours setup
    const { operatingHours } = useOperatingHours()

    // Court setup
    const { courts } = useCourt()
    const { member } = useMember()

    // Notification setup
    const { notify } = useNotification()

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
        setAvailableCourts(
            courts.filter(
                (court) => court.active && (
                    (court.required_membership_id === null
                    || court.required_membership_id === undefined)
                    || (member?.membership_id !== null
                    && member?.membership_id !== undefined
                    && member.membership_id >= court.required_membership_id)
                )
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
