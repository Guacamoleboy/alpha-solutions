// Pathing
// _______
// src/shared/components/resource-create/ResourceCreate.jsx

import InputText from '@/shared/components/input-text/InputText'
import ResourceActions from '@/shared/components/resource-actions/ResourceActions'
import Select from '@/shared/components/select/Select'
import {useResourceCreate} from './ResourceCreate.hooks'
import styles from '@/shared/components/resource-editor/ResourceEditor.module.css'

const ResourceCreate = ({title, fields, onCreate, onCreated, toPayload = (values) => values, targetId, showTitle = true}) => {
    const {
        busy,
        handleCreate,
        registerField,
    } = useResourceCreate({title, onCreate, onCreated, toPayload})

    return (
        <section id={targetId} className={styles.resourceEditor}>
            {showTitle && <h1>{title}</h1>}

            <form className={`${styles.resourceRow} ${fields.length <= 4 ? styles.singleRow : styles.multiRow}`} onSubmit={handleCreate}>
                <div className={styles.inputRow}>
                    <div className={styles.fields} style={{'--field-count': Math.min(fields.length, 4)}}>
                        {fields.map((field) => {
                            const commonProps = {
                                placeholder: field.placeholder || field.label,
                                ...registerField(field.key),
                                required: field.required,
                                size: 'l',
                            }

                            if (field.options) {
                                return (
                                    <Select
                                        key={field.key}
                                        {...commonProps}
                                        label={field.label}
                                        options={field.options}
                                    />
                                )
                            }

                            return (
                                <InputText
                                    key={field.key}
                                    {...commonProps}
                                    label={field.label}
                                    type={field.type || 'text'}
                                />
                            )
                        })}
                    </div>
                </div>
                <ResourceActions inline={fields.length <= 4} actions={[{label: 'Opret', type: 'submit', disabled: busy}]} />
            </form>
        </section>
    )
}

export default ResourceCreate
