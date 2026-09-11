package alpha.config;

import alpha.exception.ResourceNotFoundException;
import alpha.util.Util;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.util.List;
import java.util.Properties;

public class HibernateConfig {

    // Attributes
    private static EntityManagerFactory emf;
    private static EntityManagerFactory emfTest;
    private static final String RESOURCE_NAME = "config.properties";

    // _________________________________________________________________________________________________________________

    public static void setTest(Boolean test) {
        HibernateEnvironment.setTest(test);
    }

    // _________________________________________________________________________________________________________________

    public static Boolean getTest() {
        return HibernateEnvironment.getTest();
    }

    // _________________________________________________________________________________________________________________

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null)
            emf = createEMF(getTest());
        return emf;
    }

    // _________________________________________________________________________________________________________________

    public static EntityManagerFactory getEntityManagerFactoryForTest() {
        if (emfTest == null){
            setTest(true);
            emfTest = createEMF(getTest());
        }
        return emfTest;
    }

    // _________________________________________________________________________________________________________________

    private static EntityManagerFactory createEMF(boolean forTest) {

        try {
            Configuration configuration = new Configuration();
            Properties props = HibernateProperties.setBaseProperties();

            if (forTest) {
                props = HibernateProperties.setTestProperties(props);
            } else if (System.getenv("DB_HOST") != null) {
                props = HibernateProperties.setDeployedProperties(props);
            } else {

                // Set, Suggest or Exception Handle.
                try {
                    props = HibernateProperties.setDevProperties(props, RESOURCE_NAME);
                } catch (ResourceNotFoundException e) {
                    List<String> suggestions = Util.fileMissingSearcher(RESOURCE_NAME);
                    if (!suggestions.isEmpty()) {
                        String introMessage = String.format("""
                                \n---------------------------
                                HibernateConfig ERROR
                                    - createEMF(boolean forTest)
                                    - .properties file not found
                                
                                Your search param: 
                                - %s
                                
                                Searching for other .properties files incase of spelling mistake..
                                _______
                                
                                File(s) found:
                                """, RESOURCE_NAME);
                        System.out.println(introMessage);
                        for (String s : suggestions) {
                            System.out.println("- " +  s);
                        }
                        System.out.println("---------------------------\n");
                        System.exit(0);
                    } else {
                        String nothingFound = String.format("""
                                No file containing ".properties" found.. 
                                Check your resources/ folder.
                                """);
                        System.out.println(nothingFound);
                        throw e;
                    }
                }
            }

            configuration.setProperties(props);
            HibernateAnnotation.registerEntities(configuration);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);
            return sf.unwrap(EntityManagerFactory.class);

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }

    }

}