// Pathing
// _______
// src/shared/components/select/Select.jsx

export default function Select({ label, size = 'm', className = '', options = [], ...props }) {
    return (
        <div className="sharedInputWrapper">
            {label && <label className="sharedInputLabel">{label}</label>}
            <div className="sharedSelectWrapper">
                <select 
                    data-size={size} 
                    className={`sharedInput sharedSelect ${className}`}
                    {...props}
                >
                    {options.map(({ value, label }) => (
                        <option 
                            key={value} 
                            value={value}>
                            {label}
                        </option>
                    ))}
                </select>
                {/* CUSTOM ICON INSTEAD OF DEFAULT */}
                <i className="fa fa-chevron-down sharedSelectIcon" />
            </div>
        </div>
    )
}