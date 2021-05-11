package triatlon.server;

import domain.*;
import service.ServiceTriatlon;
import triatlon.services.ITriatlonObserver;
import triatlon.services.ITriatlonServices;
import triatlon.services.TriatlonException;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TriatlonServicesImpl implements ITriatlonServices {

    private ServiceTriatlon serviceTriatlon;
    private Map<String, ITriatlonObserver> loggedClients;
    public TriatlonServicesImpl(ServiceTriatlon serviceTriatlon){
        loggedClients=new ConcurrentHashMap<>();
        this.serviceTriatlon = serviceTriatlon;
    }

    public synchronized void login(Arbitru ab_user, ITriatlonObserver client) throws TriatlonException {
        //TODO validari
        if (ab_user!=null){
            System.out.println("User cu id:" + ab_user.getId().toString());
            loggedClients.put(ab_user.getId().toString(), client);
            System.out.println("Numar utilizatori conectati: "+ loggedClients.size());

        }else
            throw new TriatlonException("Authentication failed.");
    }



    public synchronized Arbitru getArbitrubyName(String  nume){
        //TODO validari
        System.out.println("Nume arbitru: "+nume);
        return serviceTriatlon.getArbitruByName(nume);
    }
    public synchronized List<ParticipantDTO> getParticipantiDTO(Arbitru ab){
        //TODO validari
        List<ParticipantDTO> lista = StreamSupport.stream(serviceTriatlon.getParticipantiDTO().spliterator(), false)
                .collect(Collectors.toList());

        return lista;

    }
    public synchronized  List<Rezultat> filterbyProba(Proba proba){
        //TODO validari
        List<Rezultat> lista = StreamSupport.stream(serviceTriatlon.filterbyProba(proba).spliterator(), false)
                .collect(Collectors.toList());

        return lista;

    }
    public synchronized Participant findParticipantByNumePrenume(String firstName, String lastName){
        //TODO validari
        Participant participant = serviceTriatlon.findParticipantByNumePrenume(firstName,lastName);
        return participant;
    }


    private final int defaultThreadsNo=5;

    public synchronized void sendRezultat(Rezultat rezultat) throws TriatlonException {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);

        serviceTriatlon.adaugaRezultat(rezultat.getProba(),rezultat.getParticipant(), rezultat.getNumarPuncte());
        for (Map.Entry<String,ITriatlonObserver> entry : loggedClients.entrySet()) {
            executor.execute(() -> {
                try {
                    entry.getValue().rezultatReceived(rezultat);
                } catch (TriatlonException|RemoteException e) {
                    System.err.println("Error notifying " + e);
                }
            });


        }
        executor.shutdown();
    }

    public synchronized void logout(Arbitru arbitru, ITriatlonObserver client) throws TriatlonException {
        //TODO validari
        ITriatlonObserver localClient=loggedClients.remove(arbitru.getId().toString());
        if (localClient==null)
            throw new TriatlonException("Arbitru "+ arbitru.getId().toString()+" is not logged in.");

    }

    public synchronized Proba getProbaArbitrubyId(long id) throws TriatlonException{
        //TODO validari
        Proba proba = serviceTriatlon.getProbabyId(id);
        return proba;
    }

}
