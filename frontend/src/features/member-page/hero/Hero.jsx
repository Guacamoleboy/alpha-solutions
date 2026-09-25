// Pathing
// _______
// src/features/member-page/hero/Hero.jsx

import { useNavigate } from 'react-router-dom'
import Submit from '@/shared/components/submit/Submit'
import styles from './Hero.module.css'
import useMember from '@/shared/hooks/useMember'

const Hero = ({ className = '' }) => {

    const navigate = useNavigate()
    const { member } = useMember()

    const handleBookingClick = () => {
        navigate('/member/booking')
    }

    return (
        <div className={`${styles.heroWrapper} ${className}`}>
            <div className={styles.heroContent}>

                {/* MEMBER META */}
                <h1 className="heroTitle">
                    Velkommen tilbage, {member?.first_name} {member?.last_name}.
                </h1>

                {/* MEMBER QUOTE */}
                <div className={styles.heroMeta}>
                    <p className={styles.metaText}>
                        Pickleball er ikke bare et spil – det er dagens bedste pause.
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