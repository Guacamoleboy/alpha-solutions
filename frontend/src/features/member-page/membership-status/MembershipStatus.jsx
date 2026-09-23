// Pathing
// _______
// src/features/member-page/membership-status/MembershipStatus.jsx

import styles from '../MemberImageCard.module.css'

const MembershipStatus = ({ className = '' }) => (
    <article className={`${styles.card} ${styles.membershipStatus} ${className}`}>
        <span className={styles.content}>
            <span className={styles.title}>Din medlemsstatus</span>
            <span className={styles.value}>Premium medlem</span>
            <span className={styles.subtitle}>Aktivt medlemskab</span>
        </span>
    </article>
)

export default MembershipStatus