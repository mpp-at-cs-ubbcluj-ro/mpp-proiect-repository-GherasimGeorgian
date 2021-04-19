package repository.config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ApplicationContext {
    public String CONFIG_LOCATION;
    public ApplicationContext(){
        CONFIG_LOCATION="C://Users//Ghera//IdeaProjects//mpp-proiect-repository-GherasimGeorgian//mpp_proiect_java//TriatlonPersistence//src//main//resources//config.properties";
    }

    public Properties getPROPERTIES() {
        return getProperties();
    }


    public Properties getProperties() {
        Properties properties=new Properties();
        try {
            properties.load(new FileReader(CONFIG_LOCATION));
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Cannot load config properties");
        }
    }
}
