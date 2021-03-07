package triatlon.repository.database;

import triatlon.domain.Arbitru;
import triatlon.domain.validators.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ArbitruDB extends AbstractDbRepository<Long, Arbitru>{
    public ArbitruDB(String url, String username, String password, Validator<Arbitru> validator) {
        super(url, username,password,validator);
    }
    @Override
    public Arbitru extractEntity(ResultSet rs){
        Arbitru arbitru = null;
        try{
            Long idArbitru = rs.getLong("idArbitru");

            String firstName = rs.getString("firstname");
            String lastName = rs.getString("lastname");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String username = rs.getString("username");



            arbitru = new Arbitru(idArbitru,firstName,lastName,email,password,username);


        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return arbitru;
    }
    @Override
    public String abstractSelect(){
        return new String("select * from arbitru");
    }
    @Override
    public String abstractInsert(){
        return new String("INSERT INTO arbitru(id_utilizator,date_create_account,email,parola,type_account,photo_url) values(?,?,?,?,?,?)");
    }
    @Override
    public void abstractInsertParameters(PreparedStatement stmt, Arbitru arbitru){
//        try{
//            stmt.setLong(1, account.getId());
//            stmt.setTimestamp(2,Timestamp.valueOf(account.getData_creeare_cont()));
//            stmt.setString(3,account.getEmail());
//            stmt.setString(4,account.getParola());
//            stmt.setString(5,account.getTipCont());
//            stmt.setString(6,account.getUrl_photo());
//        } catch (
//                SQLException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public String abstractDelete(Long id){
        return new String("DELETE FROM account where id_utilizator='" + id.toString() + "'");
    }

    @Override
    public String abstractUpdate(){
        return new String("UPDATE account SET id_utilizator = ?," +
                "date_create_account = ?," +
                "email = ?," +
                "parola = ?," +
                "type_account= ?," +
                "photo_url= ?" +
                " WHERE id_utilizator = ?");
    }
    public void abstractUpdateParameters(PreparedStatement stmt,Arbitru entity){
//        try{
//            stmt.setLong(1, entity.getId());
//            stmt.setTimestamp(2,Timestamp.valueOf(entity.getData_creeare_cont()));
//            stmt.setString(3, entity.getEmail());
//            stmt.setString(4, entity.getParola());
//            stmt.setString(5, entity.getTipCont());
//            stmt.setString(6, entity.getUrl_photo());
//            stmt.setLong(7,entity.getId());
//        } catch (
//                SQLException e) {
//            e.printStackTrace();
//        }
    }
}
