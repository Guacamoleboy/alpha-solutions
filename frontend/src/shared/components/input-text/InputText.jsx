// Pathing
// _______
// src/shared/components/input-text/InputText.jsx

export default function InputText({label, size = 'm', className = '', ...props }) {
  return (
    <div className="sharedInputWrapper">
      {label && <label className="sharedInputLabel">{label}</label>}
      <input
        type="text"
        data-size={size}
        className={`sharedInput ${className}`}
        {...props}
      />
    </div>
  )
}