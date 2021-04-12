package triatlon.services;

import domain.*;

import java.util.List;

public interface ITriatlonServices {
    void login(Arbitru arbitru, ITriatlonObserver client) throws TriatlonException;
    void sendRezultat(Rezultat rezultat) throws TriatlonException;
    void logout(Arbitru arbitru, ITriatlonObserver client) throws TriatlonException;
    Arbitru getArbitrubyName(String name) throws TriatlonException;
    List<ParticipantDTO> getParticipantiDTO(Arbitru arbitru) throws TriatlonException;
    Proba getProbaArbitrubyId(long id) throws TriatlonException;
    List<Rezultat> filterbyProba(Proba proba) throws  TriatlonException;
    Participant findParticipantByNumePrenume(String firstName,String lastName) throws TriatlonException;

}