// Pathing
// _______
// src/app/routes/AppRoutes.tsx

import { Routes, Route } from 'react-router-dom'

import MemberPage from '@/app/pages/MemberPage'
import LoginPage from '@/app/pages/LoginPage'
import MembershipPage from '@/app/pages/MembershipPage'
import RegisterPage from '@/app/pages/LoginPage'
import MemberSettingsPage from '@/app/pages/MemberSettingsPage'

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
            <Route path="/membership" element={<MembershipPage />} />
            <Route path="/member/settings" element={<MemberSettingsPage />} />
        </Route>

    </Routes>
)

export default AppRoutes