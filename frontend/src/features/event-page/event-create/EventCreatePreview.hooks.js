// Pathing
// _______
// src/features/event-page/event-create/EventCreatePreview.hooks.js

export const useEventPreview = (values) => {

    const organizers = [values.organizerOne, values.organizerTwo, values.organizerThree]
        .map((organizer) => organizer.trim())
        .filter(Boolean)
        
    const date = values.date || 'Vælg dato'
    const time = values.startTime && values.endTime ? `${values.startTime}–${values.endTime}` : 'Vælg tidsrum'

    return {
        date,
        details: [
            values.equipmentRequired ? 'Udstyr ønskes' : 'Uden ekstra udstyr',
            time,
            `${values.guests || 0} gæster · ${values.courts || 0} baner`,
        ],
        organizers,
        title: values.name || 'Dit event',
    }

}