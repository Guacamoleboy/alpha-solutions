// Pathing
// _______
// src/app/pages/MemberPage.jsx

import {
    FavoriteCourt,
    HoursToday,
    MemberHero,
    MembershipAdditions,
    MembershipStatus,
    PlayedCourts,
    UpcomingBookings,
} from '@/features/member-page'
import { useMemberBookingStats } from './MemberPage.hooks'
import styles from './MemberPage.module.css'

const MemberPage = () => {

    const { upcomingBookings, favoriteCourt, playedCourts } = useMemberBookingStats()

    return (
        <div className="memberPage">
        <MemberHero className={styles.hero} />
        <HoursToday className={styles.hoursToday} />
        <PlayedCourts className={styles.playedCourts} count={playedCourts} />
        <MembershipStatus className={styles.membershipStatus} />
        <UpcomingBookings className={styles.upcomingBookings} count={upcomingBookings} />
        <FavoriteCourt className={styles.favoriteCourt} name={favoriteCourt} />
        <MembershipAdditions className={styles.membershipAdditions} />
        </div>
    )
}

export default MemberPage