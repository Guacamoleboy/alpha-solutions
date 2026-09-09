// Pathing
// _______
// src/features/member-page/hero/Hero.jsx

import { useNavigate } from 'react-router-dom'
import Submit from '@/shared/components/submit/Submit'
import styles from './Hero.module.css'

const Hero = () => {

    const navigate = useNavigate()

    const handleBookingClick = () => {
        navigate('/member/booking')
    }

    return (
        <div className={styles.heroWrapper}>
            <div className={styles.heroContent}>

                {/* MEMBER META */}
                <h1 className={styles.heroTitle}>
                    Velkommen tilbage, navn.
                    <span className={styles.premiumBadge}>Premium medlem</span>
                </h1>
                
                {/* MEMBER DATA */}
                <div className={styles.heroMeta}>
                    <p className={styles.metaText}>
                        Sidst spillet Pickleball: <span className={styles.metaHighlight}>09-09/2026</span>
                    </p>
                </div>

            </div>

            {/* SALES */}
            <div className={styles.heroAction}>
                <Submit 
                    label="Skal vi booke en ny tid?" 
                    size="l" 
                    className={styles.heroSubmitBtn}
                    onClick={handleBookingClick}
                />
            </div>

        </div>
    )
}

export default Hero
