// Pathing
// _______
// src/shared/components/quick-navigation/QuickNavigation.jsx

const QuickNavigation = ({ items = [] }) => {

    // Click Handle
    const handleNavigation = (targetId) => {
        document.getElementById(targetId)?.scrollIntoView({
            behavior: 'smooth',
            block: 'start',
        })
    }

    return (
        <div className="quickNavigationWrapper">
            {items.map((item) => (
                <button
                    key={item.targetId}
                    type="button"
                    onClick={() => handleNavigation(item.targetId)}
                >
                    {item.label}
                </button>
            ))}
        </div>
    )
    
}

export default QuickNavigation