package domain;

import java.util.Objects;

public class Participant extends Entity<Long> {

    private String firstName;
    private String lastName;
    private Integer varsta;

    public Participant(Long idParticipant,String firstName, String lastName,Integer varsta) {
        setId(idParticipant);
        this.firstName = firstName;
        this.lastName = lastName;
        this.varsta = varsta;
    }

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

    public Integer getVarsta(){
        return varsta;
    }

  @Override
    public String toString() {
        return "Participant{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participant)) return false;
        Participant that = (Participant) o;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),getFirstName(), getLastName());
    }
}
