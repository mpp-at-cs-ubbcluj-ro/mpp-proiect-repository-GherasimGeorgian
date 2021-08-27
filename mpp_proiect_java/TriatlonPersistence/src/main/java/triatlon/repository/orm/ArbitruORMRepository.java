package triatlon.repository.orm;

import java.util.ArrayList;
import java.util.List;

import domain.Arbitru;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import triatlon.repository.IArbitruRepository;


public class ArbitruORMRepository implements IArbitruRepository {
    private static SessionFactory sessionFactory;


    public ArbitruORMRepository(){

    }
    public boolean loginArbitru(String username, String password) {
        initialize();
        Arbitru arbitru =null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;

            try {
                tx = session.beginTransaction();

                try {
                    arbitru =
                            (Arbitru)session.createQuery("from Arbitru where username=:fn and password=:ln")
                                    .setParameter("fn", username)
                                    .setParameter("ln", password)
                                    .uniqueResult();
                }catch(Exception ex){
                    ex.printStackTrace();
                }


                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }catch (Exception ex){

            ex.printStackTrace();
        }finally
        {
            close();
        }
        int gasit =0;
        if(arbitru != null){
            gasit =1;
        }
        if(gasit == 1){
            return true;
        }
        return false;

    }
    @Override
    public void save(Arbitru entity) {

    }
    @Override
    public Arbitru findOne(Long id_param) {
        return null;
    }
    @Override
    public Iterable<Arbitru> findAll() {
        initialize();

        List<Arbitru> arbitrii = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;

            try {
                tx = session.beginTransaction();

                try {
                    arbitrii =
                            session.createQuery("from Arbitru").list();
                }catch(Exception ex){
                    ex.printStackTrace();
                }


                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }catch (Exception ex){

            ex.printStackTrace();
        } finally
        {
            close();
        }
        return arbitrii;

    }


    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exceptie "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }



}
