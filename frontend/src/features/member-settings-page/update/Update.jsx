// Pathing
// _______
// src/features/member-settings-page/update/Update.jsx

import InputText from '@/shared/components/input-text/InputText'
import Submit from '@/shared/components/submit/Submit'
import styles from './Update.module.css'
import { updateMember } from '@/api/endpoints/member'

// Change with API data later
const inputFields = [
    {
        id: 'name',
        label: 'Navn',
        placeholder: 'Klaus',
    },
    {
        id: 'lastname',
        label: 'Efternavn',
        placeholder: 'Klausen',
    },
    {
        id: 'birthdate',
        label: 'Fødselsdato',
        placeholder: '15 Juni 1998',
    },
    {
        id: 'phone',
        label: 'Telefon',
        placeholder: '+45 60 60 60 60',
    },
    {
        id: 'email',
        label: 'Email',
        placeholder: 'klaus@klaus.dk',
    }
]

const Update = ({ targetId }) => {

    const handleUpdate = async (e) => {
        e.preventDefault()
        await updateMember();
    }

    return (
        <section id={targetId} className={`settingsRow ${styles.updateRow}`} >

            <form onSubmit={handleUpdate} className="settingsForm">

                {/* INPUT MAP */}
                {inputFields.map((field) => (
                    <InputText
                        key={field.id}
                        label={field.label}
                        placeholder={field.placeholder}
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