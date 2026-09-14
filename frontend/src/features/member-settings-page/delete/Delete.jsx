// Pathing
// _______
// src/features/member-settings-page/delete/Delete.jsx

import InputText from '@/shared/components/input-text/InputText'
import Submit from '@/shared/components/submit/Submit'
import styles from './Delete.module.css'
import { useDelete } from './Delete.hooks'

const Delete = ({ targetId }) => {

    const {
        registerField,
        handleDelete,
    } = useDelete()

    return (
        <section id={targetId} className={`settingsRow ${styles.deleteRow}`}>

            <form onSubmit={handleDelete} className="settingsForm">

                {/* TEXT INPUT */}
                <InputText
                    {...registerField('confirmation')}
                    label="Slet din bruger"
                    placeholder='Skriv "JEG ER SIKKER" for at slette din bruger'
                    size="l"
                    className={styles.deleteInput}
                    required
                />

                {/* SUBMIT */}
                <Submit
                    label="Slet"
                    size="l"
                    className={`settingsSubmit ${styles.deleteSubmit}`}
                />

            </form>

        </section>
    )
}

export default Delete