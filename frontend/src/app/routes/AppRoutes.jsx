// Pathing
// _______
// src/app/routes/AppRoutes.tsx

import {Routes, Route} from 'react-router-dom'

import MemberPage from '@/app/pages/MemberPage'
import LoginPage from '@/app/pages/LoginPage'
import MembershipPage from '@/app/pages/MembershipPage'
import RegisterPage from '@/app/pages/RegisterPage'
import MemberSettingsPage from '@/app/pages/MemberSettingsPage'
import MemberBookingPage from '@/app/pages/MemberBookingPage'

import AppLayout from '@/app/layouts/AppLayout'
import AuthLayout from '@/app/layouts/AuthLayout'
import ProtectedRoutes from '@/app/routes/ProtectedRoutes'

const AppRoutes = () => (
    <Routes>

        {/* Login */}
        <Route element={<AuthLayout />}>
            <Route path="/" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
        </Route>

        {/* Member Portal */}
        <Route element={<ProtectedRoutes allowedRoles={["MEMBER"]} />}>
            <Route element={<AppLayout />}>
                <Route path="/member" element={<MemberPage />} />
                <Route path="/membership" element={<MembershipPage />} />
                <Route path="/member/settings" element={<MemberSettingsPage />} />
                <Route path="/member/booking" element={<MemberBookingPage />} />
            </Route>
        </Route>

        {/* Owner Portal */}
        {/*
        <Route element={<ProtectedRoutes allowedRoles={["OWNER"]} />}>
            <Route element={<AppLayout />}>
                <Route path="/dashboard" element={<OwnerDashboardPage />} />
            </Route>
        </Route>
        */}
        
    </Routes>
)

export default AppRoutes