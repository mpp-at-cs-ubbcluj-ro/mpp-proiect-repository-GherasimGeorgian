package repository;


import domain.Arbitru;

public interface IArbitruRepository extends IRepository<Long, Arbitru>{
    boolean loginArbitru(String username, String password);
}