package triatlon.repository;

import triatlon.domain.Arbitru;


public interface IArbitruRepository extends IRepository<Long, Arbitru>{
    boolean loginArbitru(String username, String password);
}