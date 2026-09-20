// Pathing
// _______
// src/features/owner-dashboard/courts/Courts.jsx

import QuickNavigation from '@/shared/components/quick-navigation/QuickNavigation'
import ResourceCreate from '@/shared/components/resource-create/ResourceCreate'
import ResourceEditor from '@/shared/components/resource-editor/ResourceEditor'
import styles from '@/features/owner-dashboard/OwnerDashboard.module.css'
import {useCourts} from './Courts.hooks'

export const Court = () => {
    const {
        courts,
        error,
        fields,
        handleCreate,
        handleDelete,
        handleUpdate,
        loading,
        setView,
        toPayload,
        view,
    } = useCourts()

    const title = view === 'create' ? 'Opret ny bane' : 'Baner'

    return (
        <>
            <h1 className={styles.title}>{title}</h1>
            <QuickNavigation
                items={[
                    {label: 'Forside', targetId: 'court-home'},
                    {label: 'Opret ny', targetId: 'court-create'},
                ]}
                scrollToTop
                onNavigate={(targetId) => setView(targetId === 'court-create' ? 'create' : 'home')}
            />

            {view === 'home' && (
                <div id="court-home">
                    <ResourceEditor
                        title="Baner"
                        showTitle={false}
                        items={courts}
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
                    title="Opret ny bane"
                    showTitle={false}
                    targetId="court-create"
                    fields={fields}
                    toPayload={(values) => toPayload(null, values)}
                    onCreate={handleCreate}
                    onCreated={() => setView('home')}
                />
            )}
        </>
    )
}

export default Court
