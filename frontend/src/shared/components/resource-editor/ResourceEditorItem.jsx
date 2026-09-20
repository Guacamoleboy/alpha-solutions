import InputText from '@/shared/components/input-text/InputText'
import ResourceActions from '@/shared/components/resource-actions/ResourceActions'
import Select from '@/shared/components/select/Select'
import {useResourceEditorItem} from './ResourceEditorItem.hooks'
import styles from './ResourceEditor.module.css'

const ResourceEditorItem = ({item, fields, busy, onDelete, onSubmit}) => {
    const {getFieldValue, registerField, values} = useResourceEditorItem({item, fields})
    const singleRow = fields.length <= 4
    const fieldCount = Math.min(fields.length, 4)

    return (
        <form
            className={`${styles.resourceRow} ${singleRow ? styles.singleRow : styles.multiRow}`}
            onSubmit={(event) => onSubmit(event, item, values)}
        >
            <div className={styles.inputRow}>
                <div className={styles.fields} style={{'--field-count': fieldCount}}>
                    {fields.map((field) => {
                        const fieldProps = {
                            ...registerField(field.key),
                            placeholder: String(getFieldValue(field)),
                            required: field.required,
                            size: 'l',
                        }

                        if (field.options) {
                            return (
                                <Select
                                    key={field.key}
                                    {...fieldProps}
                                    label={field.label}
                                    options={field.options}
                                />
                            )
                        }

                        return (
                            <InputText
                                key={field.key}
                                {...fieldProps}
                                label={field.label}
                                type={field.type || 'text'}
                            />
                        )
                    })}
                </div>
            </div>

            <ResourceActions
                inline={singleRow}
                actions={[
                    {label: 'Opdatér', type: 'submit', disabled: busy},
                    {label: 'Slet', danger: true, disabled: busy, onClick: () => onDelete(item)},
                ]}
            />
        </form>
    )
}

export default ResourceEditorItem
