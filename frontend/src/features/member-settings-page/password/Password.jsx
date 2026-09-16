// Pathing
// _______
// src/features/member-settings-page/password/Password.jsx

import InputPassword from '@/shared/components/input-password/InputPassword'
import Submit from '@/shared/components/submit/Submit'
import styles from './Password.module.css'
import { usePassword } from './Password.hooks'

const Password = ({ targetId }) => {

    // usePassword setup
    const {
        registerField,
        handlePassword
    } = usePassword()

    return (
        <section id={targetId} className={`settingsRow ${styles.passwordRow}`}>

            <form onSubmit={handlePassword} className="settingsForm">

                {/* TEXT INPUT */}
                <InputPassword
                    {...registerField('currentPassword')}
                    label="Nuværende password"
                    placeholder="Nuværende password"
                    size="l"
                    className={styles.passwordInput}
                    required
                />

                {/* TEXT INPUT */}
                <InputPassword
                    {...registerField('newPassword')}
                    label="Nyt password"
                    placeholder="Nye password"
                    size="l"
                    className={styles.passwordInput}
                    required
                />

                {/* TEXT INPUT */}
                <InputPassword
                    {...registerField('confirmPassword')}
                    label="Bekræftelse"
                    placeholder="Nye password igen"
                    size="l"
                    className={styles.passwordInput}
                    required
                />

                {/* SUBMIT */}
                <Submit
                    label="Skift password"
                    size="l"
                    className={`settingsSubmit ${styles.passwordSubmit}`}
                />

            </form>

        </section>
    )
}

export default Password