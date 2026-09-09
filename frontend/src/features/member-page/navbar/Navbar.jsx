// Pathing
// _______
// src/features/member-page/navbar/Navbar.jsx

import { Link } from 'react-router-dom'
import styles from './Navbar.module.css'

const Navbar = () => {
    
    // Placeholders for now. Changes as we go.
    const menuItems = [
        { id: 1, label: 'Dashboard', icon: 'fa-briefcase', path: '/member' },
        { id: 2, label: 'Banereservation', icon: 'fa-star', path: '/member/booking' },
        { id: 3, label: 'Mit Medlemskab', icon: 'fa-plus', path: '/member/membership' },
        { id: 4, label: 'Indstillinger', icon: 'fa-minus', path: '/member/settings' }
    ]

    return (
        <aside className={styles.navbarWrapper}>

            {/* META */}
            <div className={styles.logoSection}>
                <h1 className={styles.logoTitle}>Alpha Pickleball</h1>
            </div>

            {/* NAVIGATION */}
            <nav className={styles.navMenu}>
                {menuItems.map((item) => (
                    <Link key={item.id} to={item.path} className={styles.navLink}>
                        <i className={`fa ${item.icon} ${styles.navIcon}`} aria-hidden="true"></i>
                        {item.label}
                    </Link>
                ))}
            </nav>

        </aside>
    )
}

export default Navbar
