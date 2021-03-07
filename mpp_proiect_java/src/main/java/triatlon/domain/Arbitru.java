package triatlon.domain;

import java.time.LocalDateTime;

public class Arbitru extends Entity<Long>{

    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;


    public Arbitru(Long idArbitru,String firstName, String lastName,String email,String password,String userName){
        setId(idArbitru);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;

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
