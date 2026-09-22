// Pathing
// _______
// src/features/forgot-password-page/forgot-password/ForgotPassword.jsx

import InputText from '@/shared/components/input-text/InputText'
import InputPassword from '@/shared/components/input-password/InputPassword'
import Submit from '@/shared/components/submit/Submit'
import styles from './ForgotPassword.module.css'
import { useForgotPassword } from './ForgotPassword.hooks'

const ForgotPassword = () => {

    const {
        isVerified,
        navigate,
        registerField,
        handleVerify,
        handleReset,
    } = useForgotPassword()

    return (
        <div className={styles.forgotPasswordWrapper}>
            <h1 className={styles.forgotPasswordTitle}>
                {isVerified ? 'Nulstil password' : 'Glemt password'}
            </h1>
            <p className={styles.forgotPasswordText}>
                {isVerified
                    ? 'Indtast og bekræft dit nye password.'
                    : 'Bekræft din bruger med e-mail og fødselsdato.'}
            </p>
            <hr />

            {!isVerified ? (
                <form onSubmit={handleVerify} className={styles.forgotPasswordForm}>
                    <InputText
                        {...registerField('email')}
                        label="E-mail"
                        placeholder="Indtast e-mail..."
                        type="email"
                        required
                        size="l"
                    />
                    <InputText
                        {...registerField('date_of_birth')}
                        label="Fødselsdato"
                        type="date"
                        required
                        size="l"
                    />
                    <div className={styles.forgotPasswordActions}>
                        <Submit
                            type="button"
                            label="Gå tilbage"
                            size="l"
                            onClick={() => navigate('/')}
                        />
                        <Submit label="Fortsæt" size="l" />
                    </div>
                </form>
            ) : (
                <form onSubmit={handleReset} className={styles.forgotPasswordForm}>
                    <InputPassword
                        {...registerField('new_password')}
                        label="Nyt password"
                        placeholder="Indtast nyt password..."
                        required
                        size="l"
                    />
                    <InputPassword
                        {...registerField('confirm_password')}
                        label="Gentag nyt password"
                        placeholder="Gentag nyt password..."
                        required
                        size="l"
                    />
                    <div className={styles.forgotPasswordActions}>
                        <Submit
                            type="button"
                            label="Gå tilbage"
                            size="l"
                            onClick={() => navigate('/')}
                        />
                        <Submit label="Nulstil password" size="l" />
                    </div>
                </form>
            )}
        </div>
    )
}

export default ForgotPassword