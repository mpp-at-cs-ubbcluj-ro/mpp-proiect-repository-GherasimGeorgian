package triatlon.repository;

import domain.Entity;

public interface ICrudRepositoryREST <ID, E extends Entity<ID>> {
    void save(E e);
    void delete(ID id);
    E findOne(ID id);
    void update(ID id, E e);
    Iterable<E> getAll();
}
