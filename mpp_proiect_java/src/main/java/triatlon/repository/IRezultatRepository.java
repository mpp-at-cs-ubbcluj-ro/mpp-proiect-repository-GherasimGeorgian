package triatlon.repository;

import triatlon.domain.Proba;
import triatlon.domain.Rezultat;

import java.util.List;


public interface IRezultatRepository extends IRepository<Long, Rezultat>{
     List<Rezultat> filterByProba(Proba proba);
}
