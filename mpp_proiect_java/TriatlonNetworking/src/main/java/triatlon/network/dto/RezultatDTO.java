package triatlon.network.dto;

import java.io.Serializable;


public class RezultatDTO implements Serializable {
    private long idrezultat;
    private long idproba;
    private long idparticipant;
    private Integer numarPuncte;

    public RezultatDTO(long idrezultat,long idproba, long idparticipant, Integer numarPuncte) {
        this.idrezultat = idrezultat;
        this.idproba = idproba;
        this.idparticipant = idparticipant;
        this.numarPuncte = numarPuncte;
    }

    public long getIdrezultat(){return idrezultat;}

    public long getidproba() {
        return idproba;
    }

    public long getidparticipant() {
        return idparticipant;
    }

    public Integer getnumarPuncte() {
        return numarPuncte;
    }
    @Override
    public String toString(){
        return "RezultatDTO["+idproba+" <--> "+idparticipant+" : "+numarPuncte+"]";
    }
}
