// Pathing
// _______
// src/features/member-page/upcoming-bookings/UpcomingBookings.jsx

import styles from '../MemberImageCard.module.css'

const UpcomingBookings = ({ className = '', count }) => (
    <article className={`${styles.card} ${className}`}>
        <img
            className={styles.image}
            src="/images/member-page/7.jpg"
            alt="Pickleballbane set ovenfra"
        />
        <span className={styles.overlay} />
        <span className={styles.content}>
            <span className={styles.title}>Antal kommende bookings</span>
            <span className={styles.value}>{count}</span>
        </span>
    </article>
)

export default UpcomingBookings