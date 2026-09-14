// Pathing
// _______
// src/shared/components/select/Select.jsx

export default function Select({ label, size = 'm', className = '', options = [], ...props }) {
    return (
        <div className="sharedInputWrapper">
            {label && <label className="sharedInputLabel">{label}</label>}
            <select
                data-size={size}
                className={`sharedInput sharedSelect ${className}`}
                {...props}
            >
                {options.map(({ value, label }) => (
                    <option key={value} value={value}>
                        {label}
                    </option>
                ))}
            </select>
        </div>
    )
}