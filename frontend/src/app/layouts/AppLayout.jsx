// Pathing
// _______
// src/app/layouts/AppLayout.jsx

import { Outlet } from 'react-router-dom'
import Navbar from '@/shared/components/navbar/Navbar'

function AppLayout() {
    return (
        <div className="appLayout">

            {/* Navbar*/}
            <Navbar />

            {/* Page Specific */}
            <main>
                <Outlet />
            </main>

        </div>
    )
}

export default AppLayout