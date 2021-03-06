package triatlon.repository.database;

import triatlon.repository.config.ApplicationContext;
import domain.Proba;
import domain.Rezultat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import triatlon.repository.IProbaRepository;
import triatlon.repository.IRezultatRepository;
import triatlon.repository.IParticipantRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RezultatDbRepository implements IRezultatRepository {
    private DbUtils dbUtils;
    private final static Logger logger = LogManager.getLogger(RezultatDbRepository.class);
    private IParticipantRepository participantDbRepository;
    private IProbaRepository probaDbRepository;
    public RezultatDbRepository(ApplicationContext context, IParticipantRepository participantDbRepository, IProbaRepository probaDbRepository){
        //logger.info("Initializing RezultatDbRepository with properties: {} ",props);
        Properties props = context.getPROPERTIES();
        dbUtils=new DbUtils(props);
        this.participantDbRepository = participantDbRepository;
        this.probaDbRepository = probaDbRepository;
    }

    @Override
    public void save(Rezultat entity) {
        //logger.traceEntry("saving task {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into rezultat values (?,?,?,?)")){
            preStmt.setLong(1,entity.getId());
            preStmt.setLong(2,entity.getProba().getId());
            preStmt.setLong(3,entity.getParticipant().getId());
            preStmt.setInt(4,entity.getNumarPuncte());


            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        //logger.traceExit();
    }



    @Override
    public Rezultat findOne(Long id_param) {
        //logger.traceEntry("finding task with id {} ",id_param);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from rezultat where idrezultat=?")){
            preStmt.setLong(1,id_param);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    Long idrezultat = result.getLong("idrezultat");
                    Long idproba = result.getLong("idproba");
                    Long idparticipant = result.getLong("idParticipant");
                    Integer numarpuncte = result.getInt("numarpuncte");

                    Rezultat ab = new Rezultat(idrezultat, probaDbRepository.findOne(idproba), participantDbRepository.findOne(idparticipant), numarpuncte);
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
        //logger.traceExit("No task found with id {}", id_param);

        return null;
    }

    @Override
    public Iterable<Rezultat> findAll() {
        Connection con=dbUtils.getConnection();
        List<Rezultat> tasks=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from rezultat")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    Long idrezultat = result.getLong("idrezultat");
                    Long idproba = result.getLong("idproba");
                    Long idparticipant = result.getLong("idParticipant");
                    Integer numarpuncte = result.getInt("numarpuncte");
                    //System.out.println(idproba);
                    //System.out.println(idparticipant);
                    Rezultat ab = new Rezultat(idrezultat, probaDbRepository.findOne(idproba), participantDbRepository.findOne(idparticipant), numarpuncte);
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


    public List<Rezultat> filterByProba(Proba proba){
        Connection con=dbUtils.getConnection();
        List<Rezultat> tasks=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from rezultat where idproba=?")) {
            preStmt.setLong(1,proba.getId());
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    Long idrezultat = result.getLong("idRezultat");
                    Long idproba = result.getLong("idproba");
                    Long idparticipant = result.getLong("idParticipant");
                    Integer numarpuncte = result.getInt("numarpuncte");
                    //System.out.println(idproba);
                    //System.out.println(idparticipant);
                    Rezultat ab = new Rezultat(idrezultat, probaDbRepository.findOne(idproba), participantDbRepository.findOne(idparticipant), numarpuncte);
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
