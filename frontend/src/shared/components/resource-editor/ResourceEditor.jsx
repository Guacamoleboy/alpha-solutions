import ResourceEditorItem from './ResourceEditorItem'
import {useResourceEditor} from './ResourceEditor.hooks'
import styles from './ResourceEditor.module.css'

const ResourceEditor = ({
    title,
    items = [],
    fields,
    loading = false,
    error = null,
    onUpdate,
    onDelete,
    toPayload = (_, values) => values,
    showTitle = true,
}) => {
    const {busyId, handleDelete, handleUpdate} = useResourceEditor({
        title,
        onUpdate,
        onDelete,
        toPayload,
    })

    return (
        <section className={styles.resourceEditor}>
            {showTitle && <h1>{title}</h1>}

            {loading && <p>Indlæser...</p>}
            {error && <p>Kunne ikke hente {title.toLowerCase()}.</p>}
            {!loading && !error && items.length === 0 && <p>Ingen {title.toLowerCase()} fundet.</p>}

            {items.map((item) => (
                <ResourceEditorItem
                    key={item.id}
                    item={item}
                    fields={fields}
                    busy={busyId === item.id}
                    onDelete={handleDelete}
                    onSubmit={handleUpdate}
                />
            ))}
        </section>
    )
}

export default ResourceEditor
