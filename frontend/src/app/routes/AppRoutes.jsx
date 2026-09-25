// Pathing
// _______
// src/app/routes/AppRoutes.tsx

import {Routes, Route} from 'react-router-dom'

import MemberPage from '@/app/pages/MemberPage'
import LoginPage from '@/app/pages/LoginPage'
import MembershipPage from '@/app/pages/MembershipPage'
import RegisterPage from '@/app/pages/RegisterPage'
import ForgotPasswordPage from '@/app/pages/ForgotPasswordPage'
import MemberSettingsPage from '@/app/pages/MemberSettingsPage'
import MemberBookingPage from '@/app/pages/MemberBookingPage'
import EventPage from '@/app/pages/EventPage'
import EventCreatePage from '@/app/pages/EventCreatePage'
import {OwnerDashboard} from '@/features/owner-dashboard'
import OwnerResourcesPage from '@/app/pages/OwnerResourcesPage'
import OwnerEmployeesPage from '@/app/pages/OwnerEmployeesPage'
import OwnerCourtsPage from '@/app/pages/OwnerCourtsPage'
import OwnerOperatingHoursPage from '@/app/pages/OwnerOperatingHoursPage'

import AppLayout from '@/app/layouts/AppLayout'
import AuthLayout from '@/app/layouts/AuthLayout'
import ProtectedRoutes from '@/app/routes/ProtectedRoutes'
import PremiumMemberRoute from '@/app/routes/PremiumMemberRoute'

const AppRoutes = () => (
    <Routes>

        {/* Login */}
        <Route element={<AuthLayout />}>
            <Route path="/" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/forgot-password" element={<ForgotPasswordPage />} />
        </Route>

        {/* Member Portal */}
        <Route element={<ProtectedRoutes allowedRoles={["MEMBER"]} />}>
            <Route element={<AppLayout />}>
                <Route path="/member" element={<MemberPage />} />
                <Route path="/membership" element={<MembershipPage />} />
                <Route path="/member/settings" element={<MemberSettingsPage />} />
                <Route path="/member/booking" element={<MemberBookingPage />} />
                <Route element={<PremiumMemberRoute />}>
                    <Route path="/member/events" element={<EventPage />} />
                    <Route path="/member/events/opret" element={<EventCreatePage />} />
                </Route>
            </Route>
        </Route>

        {/* Owner Portal */}
        <Route element={<ProtectedRoutes allowedRoles={["OWNER"]} />}>
            <Route element={<AppLayout />}>
                <Route path="/dashboard" element={<OwnerDashboard />} />
                <Route path="/dashboard/resources" element={<OwnerResourcesPage />} />
                <Route path="/dashboard/staff" element={<OwnerEmployeesPage />} />
                <Route path="/dashboard/operating-hours" element={<OwnerOperatingHoursPage />} />
                <Route path="/dashboard/courts" element={<OwnerCourtsPage />} />
            </Route>
        </Route>
        
    </Routes>
)

export default AppRoutes