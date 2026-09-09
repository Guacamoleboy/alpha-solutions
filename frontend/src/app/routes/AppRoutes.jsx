// Pathing
// _______
// src/app/routes/AppRoutes.tsx

import { Routes, Route } from 'react-router-dom'

import MemberPage from '@/app/pages/MemberPage'
import LoginPage from '@/app/pages/LoginPage'
import RegisterPage from '@/app/pages/LoginPage'

import AppLayout from '@/app/layouts/AppLayout'
import AuthLayout from '@/app/layouts/AuthLayout'

const AppRoutes = () => (
    <Routes>

        {/* Login */}
        <Route element={<AuthLayout />}>
            <Route path="/" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
        </Route>

        {/* Member Portal */}
        <Route element={<AppLayout />}>
            <Route path="/member" element={<MemberPage />} />
        </Route>

    </Routes>
)

export default AppRoutes