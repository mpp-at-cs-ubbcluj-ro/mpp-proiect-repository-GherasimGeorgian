package triatlon.domain;

import java.time.LocalDateTime;

/***
 *
 */
public class Rezultat extends Entity<Long>{
    private Participant participant;
    private Integer tipProba;
    private Integer numarPuncte;
    private Arbitru arbitru;
    private Competitie competitie;
    private LocalDateTime timp_pornire;
    private LocalDateTime timp_finalizare;

    public Rezultat(){

    }

    public Participant getParticipant() {
        return participant;
    }

    public Integer getTipProba() {return tipProba;}

    public Integer getNumarPuncte() {return numarPuncte;}

    public Arbitru getArbitru() {return arbitru;}
}
