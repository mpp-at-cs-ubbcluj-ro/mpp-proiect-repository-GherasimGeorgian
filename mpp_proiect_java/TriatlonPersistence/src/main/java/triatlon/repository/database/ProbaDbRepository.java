package triatlon.repository.database;

import triatlon.repository.config.ApplicationContext;
import domain.Proba;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import triatlon.repository.IProbaRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProbaDbRepository implements IProbaRepository {
    private DbUtils dbUtils;
    private final static Logger logger = LogManager.getLogger(ProbaDbRepository.class);

    public ProbaDbRepository(ApplicationContext context){
        //logger.info("Initializing ProbaDbRepository with properties: {} ",props);
        Properties props = context.getPROPERTIES();
        dbUtils=new DbUtils(props);

    }


    public void save(Proba entity){
        //logger.traceEntry("saving task {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into proba values (?,?,?)")){
            preStmt.setLong(1,entity.getId());
            preStmt.setString(2,entity.getTipProba());
            preStmt.setInt(3,entity.getDistanta());



            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        //logger.traceExit();
    }

    public Proba findOne(Long id){
        //logger.traceEntry("finding task with id {} ",id);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from proba where idproba=?")){
            preStmt.setLong(1,id);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    Long idproba = result.getLong("idproba");
                    String tipProba = result.getString("tipProba");
                    Integer distanta = result.getInt("distanta");

                    Proba ab = new Proba(idproba, tipProba, distanta);
                    logger.traceExit(ab);
                    dbUtils.CloseConnection(con);
                    return ab;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            dbUtils.CloseConnection(con);
            System.out.println("Error DB "+ex);
        }
        //logger.traceExit("No task found with id {}", id);

        return null;
    }
    public Iterable<Proba> findAll(){
        Connection con=dbUtils.getConnection();
        List<Proba> tasks=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from proba")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    Long idproba = result.getLong("idproba");
                    String tipProba = result.getString("tipProba");
                    Integer distanta = result.getInt("distanta");

                    Proba ab = new Proba(idproba, tipProba, distanta);
                    tasks.add(ab);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            dbUtils.CloseConnection(con);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(tasks);
        //dbUtils.CloseConnection(con);
        return tasks;
    }

}
