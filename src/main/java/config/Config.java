package config;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
            }
            // load a properties file from class path, inside static method
            properties.load(input);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getDatabaseUser() {
        return properties.getProperty("DATABASE_USER");
    }

    public static String getDatabasePassword() {
        return properties.getProperty("DATABASE_PASSWORD");
    }
}

