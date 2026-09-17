// Pathing
// _______
// src/features/member-booking-page/previous-bookings/PreviousBookings.jsx

import { formatDate, formatTime } from '@/shared/utils/dateTime'
import styles from './PreviousBookings.module.css'
import { usePreviousBookings } from './PreviousBookings.hooks'

const PreviousBookings = ({ targetId, bookings }) => {

    // usePreviousBookings setup
    const { previousBookings } = usePreviousBookings(bookings)

    return (
        <section
            id={targetId}
            className={`settingsRow ${styles.previousBookings}`}
        >

            {/* META */}
            <h2>Historik</h2>

            {/* VALIDATION ON DATA */}
            {previousBookings.length === 0 ? ( <p>Ingen tidligere bookings</p> ) : (

                // IF DATA AVAILABLE
                <div className={styles.bookingList}>

                    {previousBookings.map((booking) => (
                        <div
                            key={booking.id}
                            className={styles.booking}
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

                        </div>
                    ))}

                </div>
            )}

        </section>
    )
}

export default PreviousBookings