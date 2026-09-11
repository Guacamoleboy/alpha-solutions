// Pathing
// _______
// src/features/member-settings-page/delete/Delete.jsx

import InputText from '@/shared/components/input-text/InputText'
import Submit from '@/shared/components/submit/Submit'
import styles from './Delete.module.css'
import { deleteMember } from '@/api/endpoints/member'

const Delete = ({ targetId }) => {

    const handleDelete = async (e) => {
        e.preventDefault()
        await deleteMember();
    }

    return (
        <section id={targetId} className={`settingsRow ${styles.deleteRow}`}>

            <form onSubmit={handleDelete} className="settingsForm">

                {/* TEXT INPUT */}
                <InputText
                    label="Slet din bruger"
                    placeholder="Skriv &quot;JEG ER SIKKER&quot; for at slette din bruger"
                    size="l"
                    className={styles.deleteInput}
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