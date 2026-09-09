// Pathing
// _______
// src/shared/components/input-password/InputPassword.jsx

export default function InputPassword({label, size = 'm', ...props }) {
  return (
    <div className="sharedInputWrapper">
      {label && <label className="sharedInputLabel">{label}</label>}
      <input 
        type="password" 
        data-size={size} 
        className="sharedInput" 
        {...props} 
      />
    </div>
  )
}