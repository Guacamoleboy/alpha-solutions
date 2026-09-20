import {useEffect} from 'react'
import useForm from '@/shared/hooks/useForm'

const getFieldValue = (item, field) => {
    const value = item[field.itemKey || field.key] ?? ''
    return field.type === 'time' && typeof value === 'string'
        ? value.slice(0, 5)
        : value
}

const getInitialValues = (item, fields) => fields.reduce((values, field) => {
    if (field.options || field.showValue) {
        values[field.key] = String(getFieldValue(item, field))
    }
    return values
}, {})

export const useResourceEditorItem = ({item, fields}) => {
    const {registerField, setValues, values} = useForm(getInitialValues(item, fields))

    useEffect(() => {
        setValues(getInitialValues(item, fields))
    }, [item, fields, setValues])

    return {
        getFieldValue: (field) => getFieldValue(item, field),
        registerField,
        values,
    }
}
