package domain;

public class Proba extends Entity<Long>{
    /***
     * tipProba - inot/ciclism/alergare
     * distanta - distanta pentru o proba
     */
    private String tipProba;
    private Integer distanta;

    public Proba(Long idProba,String tipProba,Integer distanta){
        setId(idProba);
        this.tipProba = tipProba;
        this.distanta = distanta;
    }

    public String getTipProba() {
        return tipProba;
    }

    public void setTipProba(String tipProba) {
        this.tipProba = tipProba;
    }

    public Integer getDistanta() {
        return distanta;
    }

    public void setDistanta(Integer distanta) {
        this.distanta = distanta;
    }

    @Override
    public String toString() {
        return "Proba{" +
                "tipProba='" + tipProba + '\'' +
                ", distanta='" + distanta + '\'' +
                '}';
    }
}
