// Pathing
// _______
// src/features/login-page/login/Login.jsx

import { useLogin } from './Login.hooks'
import InputText from '@/shared/components/input-text/InputText'
import InputPassword from '@/shared/components/input-password/InputPassword'
import Submit from '@/shared/components/submit/Submit'
import styles from './Login.module.css'

const Login = () => {

    const {
        navigate,
        registerField,
        handleSubmit,
    } = useLogin()

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
                        {...registerField('email')}
                        label="E-mail"
                        placeholder="Indtast e-mail..."
                        type="email"
                        required
                        size="l"
                    />
                
                    {/* PASSWORD INPUT */}
                    <InputPassword
                        {...registerField('password')}
                        label="Adgangskode"
                        placeholder="Indtast adgangskode..."
                        required
                        size="l"
                    />

                    <div className={styles.loginActions}>

                        {/* REGISTER */}
                        <Submit
                            type="button"
                            label="Opret bruger"
                            size="l"
                            className={styles.loginActionBtnRegister}
                            onClick={() => navigate('/register')}
                        />

                        {/* LOGIN */}
                        <Submit
                            type="submit"
                            label="Gå til portal"
                            size="l"
                            className={styles.loginActionBtn}
                        />

                    </div>

                </form>

            </div>
        </>
    )
}

export default Login