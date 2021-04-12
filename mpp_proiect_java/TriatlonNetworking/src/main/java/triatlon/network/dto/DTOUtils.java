package triatlon.network.dto;

import domain.Arbitru;
import domain.Participant;
import domain.Proba;
import domain.Rezultat;
import service.ServiceTriatlon;

public class DTOUtils {
    private ServiceTriatlon service;
    public DTOUtils(ServiceTriatlon service){
        this.service = service;
    }

    public RezultatDTO getDTO(Rezultat rezultat){
        long idrezultat = rezultat.getId();
        long idproba = rezultat.getProba().getId();
        long idparticipant = rezultat.getParticipant().getId();
        Integer numarPuncte = rezultat.getNumarPuncte();

        return new RezultatDTO(idrezultat, idproba, idparticipant,numarPuncte);
    }

    public Rezultat getFromDTO(RezultatDTO rezdto){
       Participant participant = service.getParticipantbyId(rezdto.getidparticipant());


        Proba proba = service.getProbabyId(rezdto.getidproba());
        return new Rezultat(rezdto.getIdrezultat(), proba, participant, rezdto.getnumarPuncte());

    }

    public Arbitru getFromDTO(ArbitruDTO arbdto){
        long id= arbdto.getId();
        String username= arbdto.getUserName();
        Arbitru arbitru = service.getArbitruByName(username);
        System.out.println("Acest arb:" + arbitru.toString());
        return arbitru;


    }
    public ArbitruDTO getDTO(Arbitru user){
        long id= (long)user.getId();
        String username=user.getUserName();
        return new ArbitruDTO(id, username);
    }
}
