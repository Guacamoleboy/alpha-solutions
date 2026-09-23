// Pathing
// _______
// src/features/member-page/membership-additions/MembershipAdditions.jsx

import { Link } from 'react-router-dom'
import styles from '../MemberImageCard.module.css'

const MembershipAdditions = ({ className = '' }) => (
    <Link
        className={`${styles.card} ${className}`}
        to="/membership"
    >
        <img
            className={styles.image}
            src="/images/member-page/4.jpg"
            alt="Pickleballbane set ovenfra"
        />
        <span className={styles.overlay} />
        <span className={styles.content}>
            <span className={styles.title}>Se dine tilføjelser</span>
        </span>
        <span className={styles.actionIcon} aria-hidden="true">
            <i className="fa fa-arrow-up" />
        </span>
    </Link>
)

export default MembershipAdditions