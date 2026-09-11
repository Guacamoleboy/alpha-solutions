package alpha.config;

import alpha.util.Util;
import java.util.Properties;

public class HibernateProperties {

    // Attributes

    // _________________________________________________________________________________________________________________

    public static Properties setBaseProperties() {
        Properties props = new Properties();
        props.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.current_session_context_class", "thread");
        props.put("hibernate.show_sql", "false");
        props.put("hibernate.format_sql", "false");
        props.put("hibernate.use_sql_comments", "false");
        return props;
    }

    // _________________________________________________________________________________________________________________

    public static Properties setDeployedProperties(Properties props) {
        String DBName = System.getenv("DB_NAME");
        String DBHost = System.getenv("DB_HOST");
        props.setProperty("hibernate.connection.url", "jdbc:postgresql://" + DBHost + ":5432/" + DBName);
        props.setProperty("hibernate.connection.username", System.getenv("DB_USERNAME"));
        props.setProperty("hibernate.connection.password", System.getenv("DB_PASSWORD"));
        return props;
    }

    // _________________________________________________________________________________________________________________

    public static Properties setDevProperties(Properties props, String resourceName) {
        String DBName = Util.getPropertyValue("DB_NAME", resourceName);
        String DB_USERNAME = Util.getPropertyValue("DB_USERNAME", resourceName);
        String DB_PASSWORD = Util.getPropertyValue("DB_PASSWORD", resourceName);
        props.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/" + DBName);
        props.put("hibernate.connection.username", DB_USERNAME);
        props.put("hibernate.connection.password", DB_PASSWORD);
        return props;
    }

    // _________________________________________________________________________________________________________________

    public static Properties setTestProperties(Properties props) {
        props.put("hibernate.connection.driver_class", "org.testcontainers.jdbc.ContainerDatabaseDriver");
        props.put("hibernate.connection.url", "jdbc:tc:postgresql:16.2:///test_db");
        props.put("hibernate.archive.autodetection", "hbm,class");
        props.put("hibernate.show_sql", "false");
        props.put("hibernate.hbm2ddl.auto", "create-drop");
        return props;
    }

}