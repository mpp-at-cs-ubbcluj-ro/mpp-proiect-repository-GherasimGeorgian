package triatlon.domain;

import java.util.List;

public class ParticipantDTO {
    private String firstName;
    private String lastName;
    private Integer punctaj;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(Integer punctaj) {
        this.punctaj = punctaj;
    }

    public ParticipantDTO(String firstName, String lastName, Integer punctaj){
        this.firstName = firstName;
        this.lastName= lastName;
        this.punctaj = punctaj;
    }
}
