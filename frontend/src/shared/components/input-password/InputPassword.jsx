// Pathing
// _______
// src/shared/components/input-password/InputPassword.jsx

export default function InputPassword({label, size = 'm', className = '', ...props }) {
  return (
    <div className="sharedInputWrapper">
      {label && <label className="sharedInputLabel">{label}</label>}
      <input 
        type="password" 
        data-size={size} 
        className={`sharedInput ${className}`}
        {...props} 
      />
    </div>
  )
}