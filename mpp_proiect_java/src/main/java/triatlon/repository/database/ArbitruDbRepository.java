package triatlon.repository.database;

import triatlon.domain.Arbitru;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import triatlon.repository.IArbitruRepository;
import triatlon.repository.IRepository;

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
    public ArbitruDbRepository(Properties props){
        logger.info("Initializing ArbitruDbRepository with properties: {} ",props);
        dbUtils=new DbUtils(props);
    }


    @Override
    public void save(Arbitru entity) {
//       // logger.traceEntry("saving task {} ",entity);
//        Connection con=dbUtils.getConnection();
//        try(PreparedStatement preStmt=con.prepareStatement("insert into SortingTasks values (?,?,?,?,?)")){
//            preStmt.setInt(1,entity.getId());
//            preStmt.setString(2,entity.getDesc());
//            preStmt.setInt(3,entity.getNrElem());
//            preStmt.setString(5,entity.getAlg().name());
//            preStmt.setString(4,entity.getOrder().name());
//            int result=preStmt.executeUpdate();
//        }catch (SQLException ex){
//           // logger.error(ex);
//            System.out.println("Error DB "+ex);
//        }
//       // logger.traceExit();

    }




    @Override
    public Arbitru findOne(Long id_param) {
        logger.traceEntry("finding task with id {} ",id_param);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from arbitru where idArbitru=?")){
            preStmt.setLong(1,id_param);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    long id = result.getLong("idArbitru");
                    String firstName = result.getString("firstname");
                    String lastName = result.getString("lastname");
                    String email = result.getString("email");
                    String pass = result.getString("password");
                    String username = result.getString("username");

                    Arbitru ab = new Arbitru(id, firstName, lastName, email, pass,username);
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
                    long id = result.getLong("idArbitru");
                    String firstName = result.getString("firstname");
                    String lastName = result.getString("lastname");
                    String email = result.getString("email");
                    String pass = result.getString("password");
                    String username = result.getString("username");

                    Arbitru ab = new Arbitru(id, firstName, lastName, email, pass,username);
                    tasks.add(ab);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            dbUtils.CloseConnection(con);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(tasks);
        dbUtils.CloseConnection(con);
        return tasks;
    }



}
