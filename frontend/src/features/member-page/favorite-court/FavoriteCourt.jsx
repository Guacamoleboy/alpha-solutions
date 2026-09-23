// Pathing
// _______
// src/features/member-page/favorite-court/FavoriteCourt.jsx

import styles from '../MemberImageCard.module.css'

const FavoriteCourt = ({ className = '', name }) => (
    <article className={`${styles.card} ${className}`}>
        <img
            className={styles.image}
            src="/images/member-page/2.jpg"
            alt="Pickleballbane set ovenfra"
        />
        <span className={styles.overlay} />
        <span className={styles.content}>
            <span className={styles.title}>Din favorit bane</span>
            <span className={styles.subtitle}>{name}</span>
        </span>
    </article>
)

export default FavoriteCourt