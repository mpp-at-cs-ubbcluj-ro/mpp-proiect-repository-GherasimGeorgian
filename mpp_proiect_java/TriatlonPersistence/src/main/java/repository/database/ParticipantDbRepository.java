package repository.database;

import repository.config.ApplicationContext;
import domain.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.IParticipantRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantDbRepository implements IParticipantRepository {
    private DbUtils dbUtils;
    private final static Logger logger = LogManager.getLogger(ParticipantDbRepository.class);
    public ParticipantDbRepository(ApplicationContext context){
        //logger.info("ParticipantDbRepository with properties: {} ",props);
        Properties props = context.getPROPERTIES();
        dbUtils=new DbUtils(props);
    }

    @Override
    public void save(Participant entity) {
        //logger.traceEntry("saving task {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into participant values (?,?,?,?)")){
            preStmt.setLong(1,entity.getId());
            preStmt.setString(2,entity.getFirstName());
            preStmt.setString(3,entity.getLastName());
            preStmt.setInt(4,entity.getVarsta());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        //logger.traceExit();

    }


    @Override
    public Participant findOne(Long id_param) {
        //logger.traceEntry("finding task with id {} ",id_param);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from participant where idparticipant=?")){
            preStmt.setLong(1,id_param);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    long id = result.getLong("idparticipant");
                    String firstName = result.getString("firstname");
                    String lastName = result.getString("lastname");
                    Integer varsta = result.getInt("varsta");

                    Participant part = new Participant(id, firstName, lastName, varsta);
                    logger.traceExit(part);
                    dbUtils.CloseConnection(con);
                    return part;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            dbUtils.CloseConnection(con);
            System.out.println("Error DB "+ex);
        }
        //logger.traceExit("No task found with id {}", id_param);

        return null;
    }

    @Override
    public Iterable<Participant> findAll() {
        Connection con=dbUtils.getConnection();
        List<Participant> tasks=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from participant")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    long id = result.getLong("idparticipant");
                    String firstName = result.getString("firstname");
                    String lastName = result.getString("lastname");
                    Integer varsta = result.getInt("varsta");

                    Participant ab = new Participant(id, firstName, lastName, varsta);
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
}
