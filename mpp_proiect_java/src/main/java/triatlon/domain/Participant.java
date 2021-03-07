package triatlon.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Participant extends Entity<Long> {

    private String firstName;
    private String lastName;
    private List<Rezultat> rezultate;
    private Integer varsta;
    private Integer totalPuncte;

    public Participant(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.rezultate = new ArrayList<Rezultat>();
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
