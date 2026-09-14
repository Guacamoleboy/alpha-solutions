package alpha.dao;

import java.util.List;

public interface IDAO <T> {

    T create(T entity);
    T update(T entity);
    T getById(Object id);
    <R> R getColumnById(Object id, String column);
    int updateColumnById(Object id, String column, Object value);
    boolean existByColumn(Object o, String column);
    T findEntityByColumn(Object value, String column);
    List<T> getAll();
    T delete(T entity);
    T deleteById(Object id);
    int deleteAll();
    int deleteAllSafe();

}