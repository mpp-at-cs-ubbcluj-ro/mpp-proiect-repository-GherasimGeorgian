package domain;

public class Arbitru extends Entity<Long>{

    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private long responsabil_proba;

    public Arbitru(Long idArbitru,String firstName, String lastName,String email,String password,String userName,long responsabil_proba){
        setId(idArbitru);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.responsabil_proba = responsabil_proba;
    }

    @Override
    public String toString() {
        return "Arbitru{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
    public long getIdProbaArbitru(){
        return responsabil_proba;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
