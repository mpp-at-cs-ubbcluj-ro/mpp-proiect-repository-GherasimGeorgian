package triatlon.services;

import domain.Rezultat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITriatlonObserver  extends Remote {
    void rezultatReceived(Rezultat rezultat) throws TriatlonException, RemoteException;

}
