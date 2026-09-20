// Pathing
// _______
// src/features/owner-dashboard/employees/Employees.jsx

import QuickNavigation from '@/shared/components/quick-navigation/QuickNavigation'
import ResourceCreate from '@/shared/components/resource-create/ResourceCreate'
import ResourceEditor from '@/shared/components/resource-editor/ResourceEditor'
import styles from '@/features/owner-dashboard/OwnerDashboard.module.css'
import {useEmployees} from './Employees.hooks'

export const Employee = () => {
    const {
        error,
        fields,
        handleCreate,
        handleDelete,
        handleUpdate,
        loading,
        setView,
        staff,
        view,
    } = useEmployees()

    const title = view === 'create' ? 'Opret ny ansat' : 'Ansatte'

    return (
        <>
            <h1 className={styles.title}>{title}</h1>
            <QuickNavigation
                items={[
                    {label: 'Forside', targetId: 'staff-home'},
                    {label: 'Opret ny', targetId: 'staff-create'},
                ]}
                scrollToTop
                onNavigate={(targetId) => setView(targetId === 'staff-create' ? 'create' : 'home')}
            />

            {view === 'home' && (
                <div id="staff-home">
                    <ResourceEditor
                        title="Ansatte"
                        showTitle={false}
                        items={staff}
                        loading={loading}
                        error={error}
                        fields={fields}
                        onUpdate={handleUpdate}
                        onDelete={handleDelete}
                    />
                </div>
            )}

            {view === 'create' && (
                <ResourceCreate
                    title="Opret ny ansat"
                    showTitle={false}
                    targetId="staff-create"
                    fields={fields}
                    onCreate={handleCreate}
                    onCreated={() => setView('home')}
                />
            )}
        </>
    )
}

export default Employee
