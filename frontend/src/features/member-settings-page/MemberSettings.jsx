// Pathing
// _______
// src/features/member-settings-page/MemberSettings.jsx

import QuickNavigation from '@/shared/components/quick-navigation/QuickNavigation'

import Update from './update/Update'
import Delete from './delete/Delete'
import Password from './password/Password'

const navigationItems = [
    {
        label: 'Din oplysninger',
        targetId: 'update-account',
    },
    {
        label: 'Skift password',
        targetId: 'change-password',
    },
    {
        label: 'Slet bruger',
        targetId: 'delete-account',
    },
]

const MemberSettings = () => (
    <>
        <QuickNavigation items={navigationItems} />

        <Update targetId="update-account" />
        <Password targetId="change-password" />
        <Delete targetId="delete-account" />
    </>
)

export default MemberSettings