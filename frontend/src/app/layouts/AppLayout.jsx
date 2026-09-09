// Pathing
// _______
// src/app/layouts/AppLayout.jsx

import { Outlet } from 'react-router-dom'
import { MemberNavbar } from '@/features/member-page'

function AppLayout() {
    return (
        <div className="appLayout">

            {/* Navbar*/}
            <MemberNavbar />

            {/* Page Specific */}
            <main>
                <Outlet />
            </main>

        </div>
    )
}

export default AppLayout