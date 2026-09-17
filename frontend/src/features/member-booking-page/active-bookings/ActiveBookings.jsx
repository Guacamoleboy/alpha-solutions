// Pathing
// _______
// src/features/member-booking-page/active-bookings/ActiveBookings.jsx

import Submit from '@/shared/components/submit/Submit'
import {formatDate, formatTime} from '@/shared/utils/dateTime'
import styles from './ActiveBookings.module.css'
import { useActiveBookings } from './ActiveBookings.hooks'

const ActiveBookings = ({ targetId, bookings }) => {

    // useActiveBookings setup
    const {
        activeBookings,
        loading,
        handleCancel,
    } = useActiveBookings(bookings)

    return (
        <section id={targetId} className={`settingsRow ${styles.activeBookings}`}>
            
            {/* META */}
            <h2>Aktive bookings</h2>

            {/* DATA VALIDATION */}
            {activeBookings.length === 0 ? ( <p>Ingen aktive bookings</p> ) : (

                // If data is available it uses this setup instead
                <div className={styles.bookingList}>

                    {activeBookings.map((booking) => (

                        <form
                            key={booking.id}
                            className={styles.booking}
                            onSubmit={(e) => {
                                e.preventDefault()
                                handleCancel(booking.id)
                            }}
                        >

                            {/* COURT */}
                            <div className={styles.column}>
                                <span className={styles.label}>
                                    Bane
                                </span>
                                <span>
                                    {booking.court_name}
                                </span>
                            </div>

                            {/* DATE */}
                            <div className={styles.column}>
                                <span className={styles.label}>
                                    Dato
                                </span>
                                <span>
                                    {formatDate(booking.start_time)}
                                </span>
                            </div>

                            {/* TIME */}
                            <div className={styles.column}>
                                <span className={styles.label}>
                                    Tid
                                </span>
                                <span>
                                    {formatTime(booking.start_time)}
                                    {' - '}
                                    {formatTime(booking.end_time)}
                                </span>
                            </div>

                            {/* CANCEL */}
                            <Submit
                                label="Aflys"
                                size="m"
                                className={styles.cancel}
                                disabled={loading}
                            />

                        </form>
                    ))}

                </div>
            )}

        </section>
    )
}

export default ActiveBookings