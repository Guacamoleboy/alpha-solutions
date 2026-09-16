// Pathing
// _______
// src/shared/components/notification/Notification.jsx

import useNotification from '@/shared/hooks/useNotification'
import styles from './Notification.module.css'

const Notification = () => {

    // useNotification setup
    const {
        notification,
        dismiss,
    } = useNotification()

    // No notification
    if (!notification) {
        return null
    }

    return (
        <div className={`${styles.notification} ${styles[notification.type]}`}
            role="alert"
        >
            {/* MESSAGE */}
            <span className={styles.message}>
                {notification.message}
            </span>

            {/* CLOSE BUTTON */}
            <button
                type="button"
                className={styles.dismiss}
                onClick={dismiss}
            >
                <i className="fa fa-times" />
            </button>
        </div>
    )
}

export default Notification