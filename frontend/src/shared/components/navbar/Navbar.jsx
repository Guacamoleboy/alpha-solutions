// Pathing
// _______
// src/shared/components/navbar/Navbar.jsx

import {NavLink} from 'react-router-dom'
import {useAuth} from '@/shared/hooks/useAuth'
import styles from './Navbar.module.css'

const Navbar = () => {

    const {role, logout} = useAuth()

    const menuItems = role === 'OWNER'
        ? [
            {id: 1, label: 'Dashboard', icon: 'fa-dashboard', path: '/dashboard'},
            {id: 2, label: 'Ressourcer', icon: 'fa-line-chart', path: '/dashboard/resources'},
            {id: 3, label: 'Ansatte', icon: 'fa-users', path: '/dashboard/staff'},
            {id: 4, label: 'Åbningstider', icon: 'fa-clock-o', path: '/dashboard/operating-hours'},
            {id: 5, label: 'Baner', icon: 'fa-th-large', path: '/dashboard/courts'},
        ]
        : [
            {id: 1, label: 'Forside', icon: 'fa-home', path: '/member'},
            {id: 2, label: 'Banereservation', icon: 'fa-bookmark', path: '/member/booking'},
            {id: 3, label: 'Mit Medlemskab', icon: 'fa-user', path: '/membership'},
            {id: 4, label: 'Indstillinger', icon: 'fa-cog', path: '/member/settings'},
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
                        className={({isActive}) =>
                            `${styles.navLink} ${isActive ? styles.active : ''}`
                        }
                        end={item.path === '/member' || item.path === '/dashboard'}
                    >
                        <i
                            className={`fa ${item.icon} ${styles.navIcon}`}
                            aria-hidden="true"
                        />
                        {item.label}
                    </NavLink>
                ))}
            </nav>

            <button
                type="button"
                className={`${styles.navLink} ${styles.logoutButton}`}
                onClick={logout}
            >
                <i
                    className={`fa fa-sign-out ${styles.navIcon}`}
                    aria-hidden="true"
                />
                Log ud
            </button>

        </aside>
    )
}

export default Navbar
