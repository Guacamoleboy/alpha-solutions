// Pathing
// _______
// src/shared/hooks/useForm.js

import { useState } from 'react'

export default function useForm(initialValues = {}) {
    const [values, setValues] = useState(initialValues)

    const handleChange = (e) => {
        const { name, value } = e.target
        setValues(prev => ({
            ...prev,
            [name]: value,
        }))
    }

    const reset = () => setValues(initialValues)

    const registerField = (name) => {
        return {
            name,
            value: values[name] !== undefined ? values[name] : '', 
            onChange: handleChange
        }
    }

    return {
        values,
        setValues,
        reset,
        registerField,
    }

}