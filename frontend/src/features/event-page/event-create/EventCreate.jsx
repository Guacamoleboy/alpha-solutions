// Pathing
// _______
// src/features/event-page/event-create/EventCreate.jsx

import { Link } from 'react-router-dom'
import DashboardComponent from '@/shared/components/dashboard/DashboardComponent'
import InputPassword from '@/shared/components/input-password/InputPassword'
import InputText from '@/shared/components/input-text/InputText'
import Select from '@/shared/components/select/Select'
import Submit from '@/shared/components/submit/Submit'
import { useEventCreate } from './EventCreate.hooks'
import EventCreatePreview from './EventCreatePreview'
import EventBookingVisuals from './EventBookingVisuals'
import styles from './EventCreate.module.css'

const EventCreate = () => {

    const {
        availableTimes,
        closedLabel,
        endTimes,
        handleDateChange,
        handleEquipmentChange,
        handleIntegerChange,
        handleStartTimeChange,
        handleSubmit,
        minDate,
        registerField,
        values,
    } = useEventCreate()

    return (
        <div className={styles.layout}>

            {/* COMPONENT 1 TODO: Move out into its own component? */}
            <DashboardComponent className={styles.formBox} columns={3} rows={2}>
                <form className={styles.formContent} onSubmit={handleSubmit}>
                    <h1 className="heroTitle">Opret dit eget event</h1>

                    <div className={styles.name}>
                        <InputText
                            {...registerField('name')}
                            label="Eventnavn"
                            placeholder="Skriv eventets navn"
                            maxLength={100}
                            required
                        />
                    </div>

                    <div className={styles.guests}>
                        <InputText
                            {...registerField('guests')}
                            onChange={handleIntegerChange}
                            label="Antal gæster"
                            type="number"
                            min="1"
                            max="40"
                            step="1"
                            required
                        />
                    </div>

                    <div className={styles.courts}>
                        <InputText
                            {...registerField('courts')}
                            onChange={handleIntegerChange}
                            label="Antal baner (4 pr bane)"
                            type="number"
                            min="1"
                            step="1"
                            required
                        />
                    </div>

                    <div className={styles.date}>
                        <InputText
                            {...registerField('date')}
                            onChange={handleDateChange}
                            label="Dato"
                            type="date"
                            min={minDate}
                            required
                        />
                    </div>

                    <div className={styles.startTime}>
                        <Select
                            {...registerField('startTime')}
                            onChange={handleStartTimeChange}
                            label="Fra"
                            options={[{ value: '', label: closedLabel || 'Vælg starttid' }, ...availableTimes]}
                            required
                        />
                    </div>

                    <div className={styles.endTime}>
                        <Select
                            {...registerField('endTime')}
                            label="Til"
                            options={[{ value: '', label: closedLabel || 'Vælg sluttid' }, ...endTimes]}
                            required
                        />
                    </div>

                    <div className={styles.organizerOne}>
                        <InputText
                            {...registerField('organizerOne')}
                            label="Co-arrangør 1"
                            placeholder="mail@example.com"
                        />
                    </div>

                    <div className={styles.organizerTwo}>
                        <InputText
                            {...registerField('organizerTwo')}
                            label="Co-arrangør 2"
                            placeholder="mail@example.com"
                        />
                    </div>

                    <div className={styles.organizerThree}>
                        <InputText
                            {...registerField('organizerThree')}
                            label="Co-arrangør 3"
                            placeholder="mail@example.com"
                        />
                    </div>

                    <div className={styles.eventCode}>
                        <InputPassword
                            {...registerField('eventCode')}
                            label="Eventkode (valgfri)"
                            placeholder="Privat kode"
                            maxLength={100}
                        />
                    </div>

                    <label className={styles.equipment}>
                        <span>Udstyr nødvendigt</span>
                        <span className={`${styles.equipmentControl} ${values.equipmentRequired ? styles.selected : ''}`}>
                            <input
                                type="checkbox"
                                checked={values.equipmentRequired}
                                onChange={handleEquipmentChange}
                            />
                            {values.equipmentRequired && <i className="fa fa-check-square-o" aria-hidden="true" />}
                        </span>
                    </label>

                    <div className={styles.formActions}>
                        <Link className={styles.cancel} to="/member/events">Annuller</Link>
                        <Submit label="Anmod om event" size="m" />
                    </div>
                </form>
            </DashboardComponent>

            <EventBookingVisuals />
            <EventCreatePreview values={values} />
        </div>
    )
}

export default EventCreate
