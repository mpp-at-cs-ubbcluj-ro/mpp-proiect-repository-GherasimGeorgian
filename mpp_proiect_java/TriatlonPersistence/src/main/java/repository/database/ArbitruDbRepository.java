package repository.database;
//import config.ApplicationContext;
import domain.Arbitru;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.IArbitruRepository;
import repository.config.ApplicationContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ArbitruDbRepository implements IArbitruRepository {

    private DbUtils dbUtils;
    private final static Logger logger = LogManager.getLogger(ArbitruDbRepository.class);

    public ArbitruDbRepository(ApplicationContext context){
        //logger.info("Initializing ArbitruDbRepository with properties: {} ",props);
        Properties props = context.getPROPERTIES();
        dbUtils=new DbUtils(props);
    }


    @Override
    public void save(Arbitru entity) {

    }

    @Override
    public Arbitru findOne(Long id_param) {
        //logger.traceEntry("finding task with id {} ",id_param);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from arbitru where idarbitru=?")){
            preStmt.setLong(1,id_param);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    long id = result.getLong("idarbitru");
                    String firstName = result.getString("firstname");
                    String lastName = result.getString("lastname");
                    String email = result.getString("email");
                    String pass = result.getString("password");
                    String username = result.getString("username");
                    long responsabilproba = result.getLong("responsabil_proba");
                    Arbitru ab = new Arbitru(id, firstName, lastName, email, pass,username,responsabilproba);
                    //logger.traceExit(task);
                    dbUtils.CloseConnection(con);
                    return ab;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            dbUtils.CloseConnection(con);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No task found with id {}", id_param);

        return null;
    }

    @Override
    public Iterable<Arbitru> findAll() {
        //logger.();
        Connection con=dbUtils.getConnection();
        List<Arbitru> tasks=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from arbitru")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    long id = result.getLong("idarbitru");
                    String firstName = result.getString("firstname");
                    String lastName = result.getString("lastname");
                    String email = result.getString("email");
                    String pass = result.getString("password");
                    String username = result.getString("username");
                    long responsabilproba = result.getLong("responsabil_proba");
                    Arbitru ab = new Arbitru(id, firstName, lastName, email, pass,username,responsabilproba);
                    tasks.add(ab);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            dbUtils.CloseConnection(con);
            System.out.println("Error DB "+e);
        }
        //logger.traceExit(tasks);
        dbUtils.CloseConnection(con);
        return tasks;
    }

    public boolean loginArbitru(String username, String password){
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as SIZE from arbitru where username=? and password=?")){
            preStmt.setString(1,username);
            preStmt.setString(2,password);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    dbUtils.CloseConnection(con);
                    if(result.getInt("SIZE")>0)
                        return true;
                    else
                        return false;
                }
            }
        }catch (SQLException ex){
            //logger.error(ex);
            dbUtils.CloseConnection(con);
            System.out.println("Error DB "+ex);
            return false;
        }
        return false;
    }

}
