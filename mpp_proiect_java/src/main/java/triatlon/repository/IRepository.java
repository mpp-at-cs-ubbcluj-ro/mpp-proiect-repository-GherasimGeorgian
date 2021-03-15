package triatlon.repository;

public interface IRepository<ID, T> {

    void save(T entity);
    T findOne(ID id);
    Iterable<T> findAll();
}
