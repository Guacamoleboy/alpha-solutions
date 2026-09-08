// Pathing
// _______
// src/app/routes/AppRoutes.tsx

import { Routes, Route } from 'react-router-dom'

import HomePage from '@/app/pages/HomePage'
import AppLayout from '@/app/layout/AppLayout'

const AppRoutes = () => (
    <Routes>

        {/* APP */}
        <Route element={<AppLayout />}>
            <Route path="/" element={<HomePage />} />
        </Route>

    </Routes>
)

export default AppRoutes