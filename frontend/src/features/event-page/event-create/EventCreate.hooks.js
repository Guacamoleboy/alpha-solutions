// Pathing
// _______
// src/features/event-page/event-create/EventCreate.hooks.js

import { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import useForm from '@/shared/hooks/useForm'
import { createEventRequest } from '@/api/endpoints/eventRequest'
import useNotification from '@/shared/hooks/useNotification'
import { getAvailableTimes, getDayOfWeek } from '@/features/member-booking-page/court-booking/bookingTime'
import { useOperatingHours } from '@/shared/hooks/useOperatingHours'

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

            await createEventRequest({
                name: values.name,
                start_time: `${values.date}T${values.startTime}:00`,
                end_time: `${values.date}T${values.endTime}:00`,
                guest_count: Number(values.guests),
                requested_court_count: Number(values.courts),
                equipment_required: values.equipmentRequired,
                event_code: values.eventCode || null,
                organizer_emails: organizerEmails,
            })

            navigate('/member/events')
            notify('Eventforespørgsel sendt.', 'success')
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
        minDate, 
        registerField, 
        values 
    }
    
}