// Pathing
// _______
// src/app/layouts/AuthLayout.jsx

import { Outlet } from 'react-router-dom'

function AuthLayout() {
    return (
        <div className="authLayout">

            {/* Page Specific */}
            <main>
                <Outlet />
            </main>

        </div>
    )
}

export default AuthLayout