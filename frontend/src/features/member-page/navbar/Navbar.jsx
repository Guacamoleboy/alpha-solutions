// Pathing
// _______
// src/features/member-page/navbar/Navbar.jsx

import { NavLink } from 'react-router-dom'
import styles from './Navbar.module.css'

const Navbar = () => {
    
    // Placeholders for now. Changes as we go.
    const menuItems = [
        { id: 1, label: 'Forside', icon: 'fa-home', path: '/member' },
        { id: 2, label: 'Banereservation', icon: 'fa-bookmark', path: '/member/booking' },
        { id: 3, label: 'Mit Medlemskab', icon: 'fa-user', path: '/membership' },
        { id: 4, label: 'Indstillinger', icon: 'fa-cog', path: '/member/settings' }
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
                    <NavLink
                        key={item.id}
                        to={item.path}
                        className={({ isActive }) =>
                            `${styles.navLink} ${isActive ? styles.active : ''}`
                        }
                        end={item.path === '/member'}
                    >
                        <i
                            className={`fa ${item.icon} ${styles.navIcon}`}
                            aria-hidden="true"
                        />
                        {item.label}
                    </NavLink>
                ))}
            </nav>

        </aside>
    )
}

export default Navbar
