// Pathing
// _______
// src/shared/components/resource-actions/ResourceActions.jsx

import Submit from '@/shared/components/submit/Submit'
import styles from './ResourceActions.module.css'

const ResourceActions = ({actions = [], inline = false}) => (
    <div className={`${styles.actions} ${inline ? styles.inline : ''} ${actions.length === 1 ? styles.single : ''}`}>
        {actions.map((action) => (
            action.type === 'submit'
                ? <Submit key={action.label} label={action.label} size={inline ? 'm' : 'l'} disabled={action.disabled} />
                : (
                    <button
                        key={action.label}
                        type="button"
                        className={`${styles.actionButton} ${action.danger ? styles.danger : ''}`}
                        disabled={action.disabled}
                        onClick={action.onClick}
                    >
                        {action.label}
                    </button>
                )
        ))}
    </div>
)

export default ResourceActions