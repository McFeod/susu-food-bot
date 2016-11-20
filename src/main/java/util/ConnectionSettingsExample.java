package util;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    static Properties herokuSettings(){
        String url = System.getenv("DATABASE_URL");
        Pattern pattern = Pattern.compile(
                "\\w+://(?<user>\\w+):(?<password>\\w+)@(?<host>[-A-Za-z0-9.]+):(?<port>\\d+)/(?<dbname>\\w+)/?");
        Matcher matcher = pattern.matcher(url);
        Properties properties = new Properties();
        if (matcher.matches()) {
            properties.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            properties.setProperty("hibernate.connection.url", String.format(
                    "jdbc:postgresql://%s:%s/%s", matcher.group("host"), matcher.group("port"), matcher.group("dbname")));
            properties.setProperty("hibernate.connection.username", matcher.group("user"));
            properties.setProperty("hibernate.connection.password", matcher.group("password"));
        }
        return properties;
    }
}
