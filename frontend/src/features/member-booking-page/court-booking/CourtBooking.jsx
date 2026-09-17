// Pathing
// _______
// src/features/member-booking-page/court-booking/CourtBooking.jsx

import InputText from '@/shared/components/input-text/InputText'
import Select from '@/shared/components/select/Select'
import Submit from '@/shared/components/submit/Submit'
import styles from './CourtBooking.module.css'
import { useCourtBooking } from './CourtBooking.hooks'

const CourtBooking = ({ targetId }) => {

    // useCourtBooking setup
    const {
        values,
        registerField,
        availableTimes,
        availableCourts,
        isClosed,
        minDate,
        handleDateChange,
        handleTimeChange,
        handleBooking,
    } = useCourtBooking()

    return (
        <section id={targetId} className={`settingsRow ${styles.courtBookingRow}`}>

            <form onSubmit={(e) => { handleBooking(e) }}>

                {/* DATE */}
                <InputText
                    {...registerField('date')}
                    label="Vælg dato"
                    type="date"
                    min={minDate}
                    onChange={handleDateChange}
                    size="l"
                    className={styles.input}
                    required
                />

                {/* CLOSED */}
                {values?.date && isClosed && (
                    <p className={styles.closedMessage}>
                        Vi holder lukket søndag - desværre
                    </p>
                )}

                {/* TIME */}
                {values?.date && !isClosed && (
                    <Select
                        {...registerField('time')}
                        label="Vælg tidspunkt"
                        onChange={handleTimeChange}
                        size="l"
                        className={styles.input}
                        options={[
                            {
                                value: '',
                                label: 'Vælg tidspunkt',
                            },
                            ...availableTimes,
                        ]}
                        required
                    />
                )}

                {/* COURT */}
                {values?.date && values?.time && (
                    <Select
                        {...registerField('court')}
                        label="Vælg bane"
                        size="l"
                        className={styles.input}
                        options={[
                            {
                                value: '',
                                label: 'Vælg bane',
                            },
                            ...availableCourts.map((court) => ({
                                value: court.id,
                                label: court.name,
                            })),
                        ]}
                        required
                    />
                )}

                {/* SUBMIT */}
                {values?.date && values?.time && values?.court && (
                    <Submit
                        label="Book bane"
                        size="l"
                        className={`settingsSubmit ${styles.submit}`}
                    />
                )}

            </form>

        </section>
    )
}

export default CourtBooking