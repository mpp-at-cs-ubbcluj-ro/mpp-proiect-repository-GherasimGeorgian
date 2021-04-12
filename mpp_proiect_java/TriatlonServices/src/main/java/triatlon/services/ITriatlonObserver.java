package triatlon.services;

import domain.Rezultat;

public interface ITriatlonObserver {
    void rezultatReceived(Rezultat rezultat) throws TriatlonException;

}
