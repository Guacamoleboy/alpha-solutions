// Pathing
// _______
// src/shared/components/submit/Submit.jsx

export default function Submit({label = 'Fortsæt', size = 'm', className = '', ...props }) {
  return (
    <button 
      type="submit" 
      data-size={size} 
      className={`sharedSubmitButton ${className}`}
      {...props}
    >
      {label}
    </button>
  )
}