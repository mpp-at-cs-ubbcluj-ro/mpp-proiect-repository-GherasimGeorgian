package triatlon.repository.database;

import domain.Proba;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import triatlon.repository.IProbaRepositoryREST;
import triatlon.repository.config.ApplicationContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class ProbaRepositoryMock implements IProbaRepositoryREST {
    private Map<Long, Proba> allProbe;
    private DbUtils dbUtils;


    public ProbaRepositoryMock(ApplicationContext context) {
        Properties props = context.getPROPERTIES();
        dbUtils=new DbUtils(props);
        allProbe=new TreeMap<Long, Proba>();
        getAll();
    }
    @Override
    public Proba findOne(Long id){
        Proba probaa=allProbe.get(id);
        if (probaa==null)
            return null;

        return probaa;
    }

    @Override
    public Proba findById(Long idproba) {
        return findOne(idproba);

    }


    @Override
    public Proba[] getProbe() {
        return allProbe.values().toArray(new Proba[allProbe.size()]);
    }

    @Override
    public void save(Proba proba) {

        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into proba values (?,?,?)")){
            preStmt.setLong(1,proba.getId());
            preStmt.setString(2,proba.getTipProba());
            preStmt.setInt(3,proba.getDistanta());



            int result=preStmt.executeUpdate();
        }catch (SQLException ex){

            System.out.println("Error DB "+ex);
        }
        //logger.traceExit();
    }

    @Override
    public void delete(Long s) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from proba where idproba=?")){
            preStmt.setLong(1,s);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){

            System.out.println("Error DB "+ex);
        }

    }


    @Override
    public void update(Long s, Proba user) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update Proba set distanta=? where idproba=?")){

            preStmt.setInt(1, user.getDistanta());
            preStmt.setLong(2, s);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){

            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public Iterable<Proba> getAll() {

        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from proba")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    Long idproba = result.getLong("idproba");
                    String tipProba = result.getString("tipProba");
                    Integer distanta = result.getInt("distanta");

                    Proba ab = new Proba(idproba, tipProba, distanta);
                    allProbe.put(ab.getId(),ab);
                }
            }
        } catch (SQLException e) {

            dbUtils.CloseConnection(con);
            System.out.println("Error DB "+e);
        }

        return allProbe.values();
    }
}
