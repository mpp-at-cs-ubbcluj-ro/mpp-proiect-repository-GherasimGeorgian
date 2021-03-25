package triatlon.service;

import triatlon.domain.*;
import triatlon.repository.*;
import triatlon.utils.events.ChangeEvent;
import triatlon.utils.events.ChangeEventType;
import triatlon.utils.observer.Observable;
import triatlon.utils.observer.Observer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
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
    public Participant findParticipantByNumePrenume(String firstName,String lastName){
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
    public void adaugaRezultat(Proba proba,Participant participant,Integer numar_puncte){
        rezultatDataBase.save(new Rezultat(createIdRandom(),proba,participant,numar_puncte));
        notifyObservers(new ChangeEvent(ChangeEventType.REZULTAT_ADD, null));
    }
    public Iterable<Proba> getProbe(){
        return probaDataBase.findAll();
    }

    public Iterable<Rezultat> filterbyProba(Proba proba){
        return rezultatDataBase.filterByProba(proba);
    }
}
