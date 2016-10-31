package util;

import java.util.Properties;

class ConnectionSettingsExample {
    static Properties getSettings(){
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost/dbname");
        properties.setProperty("hibernate.connection.username", "user");
        properties.setProperty("hibernate.connection.password", "qwerty");
        return properties;
    }
}
