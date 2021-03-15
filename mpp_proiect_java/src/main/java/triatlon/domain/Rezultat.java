package triatlon.domain;

import java.time.LocalDateTime;

/***
 *
 */
public class Rezultat extends Entity<Long>{

    private Proba proba;
    private Participant participant;
    private Integer numarPuncte;


    public Rezultat(Long idRezultat,Proba proba,Participant participant, Integer numarPuncte){
        setId(idRezultat);
        this.proba = proba;
        this.participant = participant;
        this.numarPuncte = numarPuncte;

    }

    public Proba getProba() {
        return proba;
    }

    public void setProba(Proba proba) {
        this.proba = proba;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Integer getNumarPuncte() {
        return numarPuncte;
    }

    public void setNumarPuncte(Integer numarPuncte) {
        this.numarPuncte = numarPuncte;
    }

    @Override
    public String toString() {
        return "Rezultat " + proba.getTipProba() + " " + participant.getFirstName() + " " + numarPuncte.toString();
    }
}
