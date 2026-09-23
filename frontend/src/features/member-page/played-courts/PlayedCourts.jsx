// Pathing
// _______
// src/features/member-page/played-courts/PlayedCourts.jsx

import { useNavigate } from 'react-router-dom'
import styles from './PlayedCourts.module.css'

const PlayedCourts = ({ className = '', count }) => {

    const navigate = useNavigate()

    const handleClick = () => {
        navigate('/member/booking?section=history')
    }

    return (
        <button
            type="button"
            className={`${styles.playedCourts} ${className}`}
            onClick={handleClick}
            aria-label="Se historik over spillede kampe"
        >
            <img
                className={styles.image}
                src="/images/member-page/3.jpg"
                alt="Pickleballbane set ovenfra"
            />
            <span className={styles.overlay} />
            <span className={styles.content}>
                <span className={styles.label}>Kampe spillet</span>
                <span className={styles.count}>{count}</span>
            </span>
            <span className={styles.actionIcon} aria-hidden="true">
                <i className="fa fa-arrow-up" />
            </span>
        </button>
    )
}

export default PlayedCourts