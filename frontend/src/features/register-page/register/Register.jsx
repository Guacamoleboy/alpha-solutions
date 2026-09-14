// Pathing
// _______
// src/features/register-page/register/Register.jsx

import { useNavigate } from 'react-router-dom'
import InputText from '@/shared/components/input-text/InputText'
import InputPassword from '@/shared/components/input-password/InputPassword'
import Submit from '@/shared/components/submit/Submit'
import styles from './Register.module.css'

const Register = () => {

    const navigate = useNavigate()

    // No logic just yet.
    const handleSubmit = (e) => {
        e.preventDefault()
    }

    // To limit redundant code just now.
    const inputRows = [
        [
            ['text', 'Fornavn', 'Indtast fornavn...'],
            ['text', 'Efternavn', 'Indtast efternavn...'],
        ],
        [
            ['password', 'Adgangskode', 'Indtast adgangskode...'],
            ['password', 'Adgangskode igen', 'Indtast adgangskode igen...'],
        ],
        [
            ['text', 'Telefon', 'Indtast telefonnummer...'],
            ['text', 'Fødselsdato', 'MM-DD-YYYY'],
        ],
        [
            ['text', 'E-mail', 'Indtast e-mail...'],
            ['text', 'Køn', 'Indtast køn...'],
        ],
    ]

    return (
        <>
            <div className={styles.registerWrapper}>

                {/* META */}
                <h1 className={styles.registerTitle}>Opret bruger</h1>
                <p className={styles.registerText}>Indtast venligst dine informationer for at oprette en bruger.</p>

                {/* LINE */}
                <hr />

                <form onSubmit={handleSubmit} className={styles.registerForm}>

                    {inputRows.map((row, rowIndex) => (
                        <div className={styles.registerRow} key={rowIndex}>

                            {row.map(([type, label, placeholder], inputIndex) => {
                                const Input = type === 'password'
                                    ? InputPassword
                                    : InputText

                                return (
                                    <Input
                                        key={inputIndex}
                                        label={label}
                                        placeholder={placeholder}
                                        size="l"
                                    />
                                )
                            })}

                        </div>
                    ))}

                    {/* ACTIONS */}
                    <div className={styles.registerActions}>

                        {/* LOGIN */}
                        <Submit
                            type="button"
                            label="Gå tilbage"
                            size="l"
                            className={styles.registerActionBtn}
                            onClick={() => navigate('/')}
                        />

                        {/* REGISTER */}
                        <Submit
                            label="Opret bruger"
                            size="l"
                            className={styles.registerActionBtnFinal}
                        />

                    </div>

                </form>

            </div>
        </>
    )
}

export default Register