// Pathing
// _______
// src/features/owner-dashboard/operating-hours/OperatingHours.jsx

import QuickNavigation from '@/shared/components/quick-navigation/QuickNavigation'
import ResourceCreate from '@/shared/components/resource-create/ResourceCreate'
import ResourceEditor from '@/shared/components/resource-editor/ResourceEditor'
import styles from '@/features/owner-dashboard/OwnerDashboard.module.css'
import {useOperatingHours} from './OperatingHours.hooks'

const OperatingHours = () => {
    const {
        error,
        fields,
        handleCreate,
        handleDelete,
        handleUpdate,
        loading,
        operatingHours,
        setView,
        toPayload,
        view,
    } = useOperatingHours()

    const title = view === 'create' ? 'Opret ny åbningstid' : 'Åbningstider'

    return (
        <>
            <h1 className={styles.title}>{title}</h1>
            <QuickNavigation
                items={[
                    {label: 'Forside', targetId: 'operating-hours-home'},
                    {label: 'Opret ny', targetId: 'operating-hours-create'},
                ]}
                scrollToTop
                onNavigate={(targetId) => setView(targetId === 'operating-hours-create' ? 'create' : 'home')}
            />

            {view === 'home' && (
                <div id="operating-hours-home">
                    <ResourceEditor
                        title="Åbningstider"
                        showTitle={false}
                        items={operatingHours}
                        loading={loading}
                        error={error}
                        fields={fields}
                        toPayload={toPayload}
                        onUpdate={handleUpdate}
                        onDelete={handleDelete}
                    />
                </div>
            )}

            {view === 'create' && (
                <ResourceCreate
                    title="Opret ny åbningstid"
                    showTitle={false}
                    targetId="operating-hours-create"
                    fields={fields}
                    toPayload={(values) => toPayload(null, values)}
                    onCreate={handleCreate}
                    onCreated={() => setView('home')}
                />
            )}
        </>
    )
}

export default OperatingHours
