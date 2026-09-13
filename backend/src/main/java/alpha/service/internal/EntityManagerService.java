package alpha.service.internal;

import alpha.dao.impl.EntityManagerDAO;
import java.util.List;

public class EntityManagerService <T> {

    // Attributes
    protected final EntityManagerDAO<T> entityManagerDAO;
    protected final Class<T> classSpecific;

    // _________________________________________________________________________________________________________________

    public EntityManagerService(EntityManagerDAO<T> entityManagerDAO, Class<T> entityClass) {
        this.entityManagerDAO = entityManagerDAO;
        this.classSpecific = entityClass;
    }

    // _________________________________________________________________________________________________________________

    public T create(T entity) {
        validateNotEmpty(entity, classSpecific.getSimpleName() + ".entity");
        return entityManagerDAO.create(entity);
    }

    // _________________________________________________________________________________________________________________

    public T update(T entity) {
        validateNotEmpty(entity, classSpecific.getSimpleName() + ".entity");
        return entityManagerDAO.update(entity);
    }

    // _________________________________________________________________________________________________________________

    public T delete(T entity) {
        validateNotEmpty(entity, classSpecific.getSimpleName() + ".entity");
        return entityManagerDAO.delete(entity);
    }

    // _________________________________________________________________________________________________________________

    public T deleteById(Object id) {
        validateNotEmpty(id, classSpecific.getSimpleName() + ".id");
        return entityManagerDAO.deleteById(id);
    }

    // _________________________________________________________________________________________________________________

    public int deleteAll() {
        return entityManagerDAO.deleteAll();
    }

    // _________________________________________________________________________________________________________________

    public int deleteAllSafe() {
        return entityManagerDAO.deleteAllSafe();
    }

    // _________________________________________________________________________________________________________________

    public T getById(Object id) {
        validateNotEmpty(id, classSpecific.getSimpleName() + ".id");
        return entityManagerDAO.getById(id);
    }

    // _________________________________________________________________________________________________________________

    public List<T> getAll() {
        return entityManagerDAO.getAll();
    }

    // _________________________________________________________________________________________________________________

    public boolean existByColumn(Object value, String column) {
        validateNotEmpty(value, classSpecific.getSimpleName() + ".value");
        validateNotEmpty(column, classSpecific.getSimpleName() + "." + column);
        return entityManagerDAO.existByColumn(value, column);
    }

    // _________________________________________________________________________________________________________________

    public <R> R getColumnById(Object id, String column) {
        validateNotEmpty(id, classSpecific.getSimpleName() + ".id");
        validateNotEmpty(column, classSpecific.getSimpleName() + "." + column);
        return entityManagerDAO.getColumnById(id, column);
    }

    // _________________________________________________________________________________________________________________

    public int updateColumnById(Object id, String column, Object value) {
        validateNotEmpty(id, classSpecific.getSimpleName() + ".id");
        validateNotEmpty(column, classSpecific.getSimpleName() + "." + column);
        return entityManagerDAO.updateColumnById(id, column, value);
    }

    // _________________________________________________________________________________________________________________

    public T findEntityByColumn(Object value, String column) {
        validateNotEmpty(value, "value");
        validateNotEmpty(column, classSpecific.getSimpleName() + "." + column);
        return entityManagerDAO.findEntityByColumn(value, column);
    }

    // _________________________________________________________________________________________________________________

    protected void validateNotEmpty(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " can't be null");
        }
        if (value instanceof String text && text.isBlank()) {
            throw new IllegalArgumentException(fieldName + " can't be empty");
        }
    }

}