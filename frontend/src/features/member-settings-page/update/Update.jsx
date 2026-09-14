// Pathing
// _______
// src/features/member-settings-page/update/Update.jsx

import InputText from '@/shared/components/input-text/InputText'
import Submit from '@/shared/components/submit/Submit'
import styles from './Update.module.css'
import { useUpdate } from './Update.hooks'

// Change with API data later
const inputFields = [
    {
        id: 'first_name',
        label: 'Navn',
        type: 'text'
    },
    {
        id: 'last_name',
        label: 'Efternavn',
        type: 'text'
    },
    {
        id: 'date_of_birth',
        label: 'Fødselsdato',
        type: 'date'
    },
    {
        id: 'phone',
        label: 'Telefon',
        type: 'text'
    },
    {
        id: 'email',
        label: 'Email',
        type: 'email'
    }
]

const Update = ({ targetId }) => {

    const {
        member,
        registerField,
        handleUpdate,
    } = useUpdate()

    return (
        <section id={targetId} className={`settingsRow ${styles.updateRow}`} >

            <form onSubmit={handleUpdate} className="settingsForm">

                {/* INPUT MAP */}
                {inputFields.map((field) => (
                    <InputText
                        key={field.id}
                        {...registerField(field.id)}
                        label={field.label}
                        placeholder={member?.[field.id] || ''}
                        type={field.type}
                        size="l"
                    />
                ))}

                {/* SUBMIT */}
                <Submit
                    label="Opdatér oplysninger"
                    size="l"
                    className={`settingsSubmit ${styles.updateSubmit}`}
                />

            </form>

        </section>
    )
}

export default Update