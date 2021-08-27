package triatlon.repository;

import domain.Proba;



public interface IProbaRepositoryREST extends ICrudRepositoryREST<Long, Proba>{
    Proba[] getProbe();
    Proba findById(Long idproba);
}