package repository;



import domain.Proba;
import domain.Rezultat;

import java.util.List;


public interface IRezultatRepository extends IRepository<Long, Rezultat>{
     List<Rezultat> filterByProba(Proba proba);
}
