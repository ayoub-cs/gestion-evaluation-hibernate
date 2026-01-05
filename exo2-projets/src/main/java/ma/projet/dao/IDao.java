package ma.projet.dao;

import java.util.List;

/**
 * Interface générique définissant les opérations CRUD de base
 */
public interface IDao<T> {

    boolean save(T entity);

    boolean remove(T entity);

    boolean modify(T entity);

    T findById(int id);

    List<T> findAll();
}
