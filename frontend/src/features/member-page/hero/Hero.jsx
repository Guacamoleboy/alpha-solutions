// Pathing
// _______
// src/features/member-page/hero/Hero.jsx

import { useNavigate } from 'react-router-dom'
import Submit from '@/shared/components/submit/Submit'
import styles from './Hero.module.css'
import useMember from '@/shared/hooks/useMember'

const Hero = () => {

    const navigate = useNavigate()
    const { member } = useMember()

    // Hardcoded for now.
    const handleBookingClick = () => {
        navigate('/member/booking')
    }

    return (
        <div className={styles.heroWrapper}>
            <div className={styles.heroContent}>

                {/* MEMBER META */}
                <h1 className={styles.heroTitle}>
                    Velkommen tilbage, {member?.first_name}.
                    <span className={styles.premiumBadge}>{member?.membership_name} medlem</span>
                </h1>
                
                {/* MEMBER DATA */}
                <div className={styles.heroMeta}>
                    <p className={styles.metaText}>
                        Sidst spillet Pickleball:{' '}
                        <span className={styles.metaHighlight}>
                            {member?.last_played || 'Aldrig'}
                        </span>
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
