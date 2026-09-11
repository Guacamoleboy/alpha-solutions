// Pathing
// _______
// src/features/member-settings-page/password/Password.jsx

import InputPassword from '@/shared/components/input-password/InputPassword'
import Submit from '@/shared/components/submit/Submit'
import styles from './Password.module.css'
import { updateMemberPassword } from '@/api/endpoints/member'

const Password = ({ targetId }) => {

    const handlePassword = async (e) => {
        e.preventDefault()
        await updateMemberPassword();
    }

    return (
        <section id={targetId} className={`settingsRow ${styles.passwordRow}`}>

            <form onSubmit={handlePassword} className="settingsForm">

                {/* TEXT INPUT */}
                <InputPassword
                    label="Nuværende password"
                    placeholder="Nuværende password"
                    size="l"
                    className={styles.passwordInput}
                />

                {/* TEXT INPUT */}
                <InputPassword
                    label="Nyt password"
                    placeholder="Nye password"
                    size="l"
                    className={styles.passwordInput}
                />

                {/* TEXT INPUT */}
                <InputPassword
                    label="Bekræftelse"
                    placeholder="Nye password igen"
                    size="l"
                    className={styles.passwordInput}
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