package repository.database;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtils {
    private Properties jdbcProps;
    private static final Logger logger= LogManager.getLogger();
    public DbUtils(Properties props){
        jdbcProps=props;
    }
    private Connection instance=null;
    private Connection getNewConnection(){
        //logger.traceEntry();

        String url=jdbcProps.getProperty("database.triatlon.url");
        String user=jdbcProps.getProperty("database.triatlon.username");
        String pass=jdbcProps.getProperty("database.triatlon.pasword");
        //logger.info("trying to connect to database ... {}",url);
        //logger.info("user: {}",user);
        //logger.info("pass: {}", pass);
        Connection con=null;
        try {
            if (user!=null && pass!=null)
                con= DriverManager.getConnection(url,user,pass);
            else
                con=DriverManager.getConnection(url);
        } /*catch (ClassNotFoundException e) {
            logger.error(e);
            System.out.println("Error loading driver "+e);
        } */catch (SQLException e) {
            //logger.error(e);
            System.out.println("Error getting connection "+e);
        }
        return con;
    }

    public Connection getConnection(){
       // logger.traceEntry();
        try {
            if (instance==null || instance.isClosed())
                instance=getNewConnection();

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        //logger.traceExit(instance);
        return instance;
    }

    public void CloseConnection(Connection con){
         //logger.traceEntry();
        try {
            if (con!=null || !con.isClosed())
                con.close();

        } catch (SQLException e) {
              logger.error(e);
            System.out.println("Error DB "+e);
        }
    }
}
