package service;


import domain.*;
import repository.IArbitruRepository;
import repository.IParticipantRepository;
import repository.IProbaRepository;
import repository.IRezultatRepository;
import utils.events.ChangeEvent;
import utils.events.ChangeEventType;
import utils.observer.Observable;
import utils.observer.Observer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ServiceTriatlon implements Observable<ChangeEvent> {
    private IArbitruRepository arbitruDataBase;
    private IParticipantRepository participantDataBase;
    private IProbaRepository probaDataBase;
    private IRezultatRepository rezultatDataBase;


    public ServiceTriatlon(IArbitruRepository arbitruDataBase,
                           IParticipantRepository participantDataBase,
                           IProbaRepository probaDataBase,
                           IRezultatRepository rezultatDataBase) {
        this.arbitruDataBase = arbitruDataBase;
        this.participantDataBase = participantDataBase;
        this.probaDataBase = probaDataBase;
        this.rezultatDataBase = rezultatDataBase;

    }

    private List<Observer<ChangeEvent>> observers=new ArrayList<>();

    @Override
    public void addObserver(Observer<ChangeEvent> e) {
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<ChangeEvent> e) {
        observers.remove(e);
    }
    @Override
    public void notifyObservers(ChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }

    public Participant getParticipantbyId(long idParticipant){
        return participantDataBase.findOne(idParticipant);
    }

    public Proba getProbabyId(long idProba){
        return probaDataBase.findOne(idProba);
    }


    public Iterable<Arbitru> getAllArbitrii(){
        return arbitruDataBase.findAll();
    }

    public boolean loginArbitru(String username,String password){
        return arbitruDataBase.loginArbitru(username,password);
    }
    public Arbitru getArbitruByName(String username){
       for(Arbitru arbitru : arbitruDataBase.findAll()){
            if(arbitru.getUserName().equals(username)){
                return arbitru;
            }
        }
       return null;
    }
    public Iterable<ParticipantDTO> getParticipantiDTO(){
        List<ParticipantDTO> participantiDTO = StreamSupport.stream(participantDataBase.findAll().spliterator(), false)
                .map(x->new ParticipantDTO(x.getFirstName(),x.getLastName(),
                        StreamSupport.stream(rezultatDataBase.findAll().spliterator(), false)
                                .filter(a->a.getParticipant().getId().toString().equals(x.getId().toString()))
                                .collect(Collectors.toList()).stream().map(c->c.getNumarPuncte()).reduce(0, (a, b) -> a + b)
                ))
                .sorted(Comparator.comparing(ParticipantDTO::getLastName))
                .collect(Collectors.toList());

        return participantiDTO;
    }
    public Participant findParticipantByNumePrenume(String firstName, String lastName){
        for(Participant participant : participantDataBase.findAll()){
            if(participant.getFirstName().equals(firstName) && participant.getLastName().equals(lastName) ){
                return participant;
            }
        }
        return null;
    }
    public Long createIdRandom(){
        do{
            boolean ok = true;
            Long id = new Random().nextLong();
            if(id< 0){
                id *= -1;
            }

            return id;
        }while(true);
    }
    public Rezultat adaugaRezultat(Proba proba, Participant participant, Integer numar_puncte){
        Rezultat rez = new Rezultat(createIdRandom(),proba,participant,numar_puncte);
        rezultatDataBase.save(rez);
        notifyObservers(new ChangeEvent(ChangeEventType.REZULTAT_ADD, null));
        return  rez;
    }
    public Iterable<Proba> getProbe(){
        return probaDataBase.findAll();
    }

    public Iterable<Rezultat> filterbyProba(Proba proba){
        return rezultatDataBase.filterByProba(proba);
    }
}
