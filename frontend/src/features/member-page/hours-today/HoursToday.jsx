// Pathing
// _______
// src/features/member-page/hours-today/HoursToday.jsx

import { Link } from 'react-router-dom'
import styles from './HoursToday.module.css'
import { useHoursToday } from './HoursToday.hooks'

const HoursToday = ({ className = '' }) => {

    const { loading, timeRemaining, statusLabel, statusTime, todayLabel } = useHoursToday()

    return (
        <Link className={`${styles.hoursToday} ${className}`} to="/operating-hours">
            <span className={styles.title}>{loading ? 'Vi lukker om' : statusLabel}</span>
            <span className={styles.time}>
                {loading ? '--:--' : statusTime || timeRemaining || 'Lukket'}
            </span>
            <span className={styles.footer}>
                <span className={styles.date}>{todayLabel}</span>
                <span className={styles.linkText}>Se åbningstider for i morgen</span>
            </span>
            <span className={styles.actionIcon} aria-hidden="true">
                <i className="fa fa-arrow-up" />
            </span>
        </Link>
    )
}

export default HoursToday