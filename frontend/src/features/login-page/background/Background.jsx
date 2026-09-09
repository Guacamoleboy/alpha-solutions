// Pathing
// _______
// src/features/login-page/background/Background.jsx

import styles from './Background.module.css'

const Background = () => {
    return (
        <>
            <div className={styles.backgroundWrapper}>
                <img src="/images/login-page/background.png" alt="pickelball login background" />
            </div>
        </>
    )
}

export default Background