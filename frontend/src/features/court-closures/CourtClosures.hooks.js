// Pathing
// _______
// src/features/court-closures/CourtClosures.hooks.js

import {useEffect, useMemo, useState} from 'react'
import {getCourts} from '@/api/endpoints/court'
import {getOperatingHours} from '@/api/endpoints/operating-hour'
import {createCourtClosure, deleteCourtClosure, getCourtClosures} from '@/api/endpoints/court-closure'
import useNotification from '@/shared/hooks/useNotification'
import {getAvailableTimes, getDayOfWeek} from '@/features/member-booking-page/court-booking/bookingTime'

// ------------------------------------------------------------------------------------------------------

const localDateString = (date) => {
    const offset = date.getTimezoneOffset() * 60000
    return new Date(date.getTime() - offset).toISOString().slice(0, 10)
}

// ------------------------------------------------------------------------------------------------------

export const useCourtClosures = () => {

    const [courts, setCourts] = useState([])
    const [operatingHours, setOperatingHours] = useState([])
    const [closures, setClosures] = useState([])
    const [courtId, setCourtId] = useState('')
    const [date, setDate] = useState('')
    const [startTime, setStartTime] = useState('')
    const [endTime, setEndTime] = useState('')
    const [reason, setReason] = useState('')
    const [loading, setLoading] = useState(true)
    const [busy, setBusy] = useState(false)
    const [error, setError] = useState(null)
    const {notify} = useNotification()

    useEffect(() => {
        Promise.all([getCourts(), getOperatingHours(), getCourtClosures()])
            .then(([courtResponse, hoursResponse, closureResponse]) => {
                setCourts((courtResponse.data || []).filter((court) => court.active))
                setOperatingHours(hoursResponse.data || [])
                setClosures(closureResponse.data || [])
            })
            .catch((loadError) => {
                setError(loadError)
                notify(loadError.message || 'Banernes lukninger kunne ikke hentes', 'error')
            })
            .finally(() => setLoading(false))
    }, [notify])

    const operatingHour = useMemo(() => {
        if (!date) return null
        const dayOfWeek = getDayOfWeek(date)
        return operatingHours.find((hours) => hours.day_of_week === dayOfWeek) || null
    }, [date, operatingHours])

    const startTimes = useMemo(() => (
        date && operatingHour && !operatingHour.closed
            ? getAvailableTimes(date, operatingHour)
            : []
    ), [date, operatingHour])

    const endTimes = useMemo(() => {
        if (!startTime || !operatingHour?.close_time) return []
        const closingTime = operatingHour.close_time.slice(0, 5)
        return startTimes
            .filter((time) => time.value > startTime)
            .concat({value: closingTime, label: closingTime})
            .filter((time, index, times) => times.findIndex((item) => item.value === time.value) === index)
    }, [operatingHour, startTime, startTimes])

    const sortedClosures = useMemo(() => [...closures].sort((left, right) => (
        left.start_time.localeCompare(right.start_time)
    )), [closures])

    const handleCreate = async (event) => {
        event.preventDefault()
        setBusy(true)
        try {
            const response = await createCourtClosure({
                court_id: Number(courtId),
                start_time: `${date}T${startTime}:00`,
                end_time: `${date}T${endTime}:00`,
                reason,
            })
            setClosures((current) => [...current, response.data])
            setStartTime('')
            setEndTime('')
            setReason('')
            notify('Banen er lukket i det valgte tidsrum', 'success')
        } catch (createError) {
            notify(createError.message || 'Lukningen kunne ikke gemmes', 'error')
        } finally {
            setBusy(false)
        }
    }

    const handleStartTimeChange = (event) => {
        setStartTime(event.target.value)
        setEndTime('')
    }

    const handleDelete = async (closure) => {
        try {
            await deleteCourtClosure(closure.id)
            setClosures((current) => current.filter((item) => item.id !== closure.id))
            notify('Lukningen er fjernet', 'success')
        } catch (deleteError) {
            notify(deleteError.message || 'Lukningen kunne ikke fjernes', 'error')
        }
    }

    return {
        busy,
        courtId,
        courts,
        closures: sortedClosures,
        date,
        endTime,
        error,
        handleCreate,
        handleDelete,
        handleStartTimeChange,
        loading,
        operatingHour,
        reason,
        setReason,
        startTimes,
        endTimes,
        setCourtId,
        setDate,
        setEndTime,
        setStartTime,
        startTime,
        today: localDateString(new Date()),
    }
    
}