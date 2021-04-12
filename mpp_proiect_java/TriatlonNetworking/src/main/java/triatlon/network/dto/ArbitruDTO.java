package triatlon.network.dto;

import java.io.Serializable;

public class ArbitruDTO implements Serializable {
    private long id;
    private String username;

    public ArbitruDTO(long id) {
        this(id,"");
    }

    public ArbitruDTO(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    @Override
    public String toString(){
        return "ArbitruDTO["+id+' '+username+"]";
    }
}
