// Pathing
// _______
// src/features/login-page/login/Login.jsx

import { useNavigate } from 'react-router-dom'
import InputText from '@/shared/components/input-text/InputText'
import InputPassword from '@/shared/components/input-password/InputPassword'
import Submit from '@/shared/components/submit/Submit'
import styles from './Login.module.css'

const Login = () => {

    const navigate = useNavigate()

    // Empty for now. No logic just yet.
    const handleSubmit = (e) => {
        e.preventDefault()
        navigate('/member')
    }

    return (
        <>
            <div className={styles.loginWrapper}>

                {/* META INFORMATION */}
                <h1 className={styles.loginTitle}>Pickelball Adgangsportal</h1>
                <p className={styles.loginText}>Indtast venligst dine informationer for at få adgang til portalen.</p>

                {/* LINE FOR SECTION */}
                <hr />

                <form onSubmit={handleSubmit} className={styles.loginForm}>

                    {/* TEXT INPUT */}
                    <InputText
                        label="Brugernavn / E-mail"
                        placeholder="Indtast brugernavn eller e-mail..." 
                        size="l"
                    />
                
                    {/* PASSWORD INPUT */}
                    <InputPassword
                        label="Adgangskode"
                        placeholder="Indtast adgangskode..." 
                        size="l"
                    />

                    {/* SUBMIT */}
                    <Submit
                        label="Log ind på portalen" 
                        size="l"
                        className={styles.loginSubmitBtn}
                    />

                </form>

            </div>
        </>
    )
}

export default Login