// Pathing
// _______
// src/features/register-page/register/Register.jsx

import { useRegister } from './Register.hooks'
import InputText from '@/shared/components/input-text/InputText'
import InputPassword from '@/shared/components/input-password/InputPassword'
import Submit from '@/shared/components/submit/Submit'
import styles from './Register.module.css'
import Select from '@/shared/components/select/Select'

const Register = () => {

    {/* REGISTER SETUP */}
    const {
        navigate,
        registerField,
        handleSubmit,
        inputRows,
    } = useRegister()

    return (
        <>
            <div className={styles.registerWrapper}>

                {/* META */}
                <h1 className={styles.registerTitle}>Opret bruger</h1>
                <p className={styles.registerText}>
                    Indtast venligst dine informationer for at oprette en bruger.
                </p>

                {/* LINE */}
                <hr />

                {/* FORM */}
                <form onSubmit={handleSubmit} className={styles.registerForm}>

                    {/* MAP OF INPUTS */}
                    {inputRows.map((row, rowIndex) => (
                        <div className={styles.registerRow} key={rowIndex}>
                            
                            {/* SPECIFICATIONS OF EACH INPUT */}
                            {row.map(([type, name, label, placeholder]) => {
                                if (type === 'select') {
                                    return (
                                        <Select
                                            key={name}
                                            {...registerField(name)}
                                            label={label}
                                            options={[
                                                { value: 'male', label: 'Mand' },
                                                { value: 'female', label: 'Kvinde' },
                                                { value: 'none', label: 'Ønsker ikke at oplyse' },
                                            ]}
                                            required
                                            size="l"
                                        />
                                    )
                                }
                                
                                {/* ARTINARY CHECK */}
                                const Input = type === 'password'
                                    ? InputPassword
                                    : InputText
                                
                                {/* FINAL */}
                                return (
                                    <Input
                                        key={name}
                                        type={type}
                                        {...registerField(name)}
                                        label={label}
                                        placeholder={placeholder}
                                        max={name === 'date_of_birth' ? new Date().toISOString().split('T')[0] : undefined}
                                        required
                                        size="l"
                                    />
                                )
                            })}
                        </div>
                    ))}

                    {/* SUBMIT ROW */}
                    <div className={styles.registerActions}>

                        {/* TO LOGIN */}
                        <Submit
                            type="button"
                            label="Gå tilbage"
                            size="l"
                            className={styles.registerActionBtn}
                            onClick={() => navigate('/')}
                        />

                        {/* CREATE ACCOUNT */}
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