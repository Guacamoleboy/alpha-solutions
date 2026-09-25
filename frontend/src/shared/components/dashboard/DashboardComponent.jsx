// Pathing
// _______
// src/shared/components/dashboard/DashboardComponent.jsx

import {Link} from 'react-router-dom'
import styles from './DashboardComponent.module.css'

const DashboardComponent = ({
    backgroundImage = '',
    children,
    columns = 4,
    rows = 3,
    className = '',
    as = 'section',
    to = '',
    ...restProps
}) => {
    const Component = to ? Link : as

    return (
        <Component
            className={`${styles.dashboard} ${className}`}
            style={{
                '--dashboard-columns': columns,
                '--dashboard-rows': rows,
                ...(backgroundImage ? {backgroundImage: `url(${backgroundImage})`} : {}),
            }}
            {...restProps}
            {...(to ? {to} : {})}
        >
            {children}
        </Component>
    )
}

export default DashboardComponent