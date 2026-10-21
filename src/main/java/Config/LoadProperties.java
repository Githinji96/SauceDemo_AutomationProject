package Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProperties {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = LoadProperties.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found on classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }
        return value;
    }

    public static String getUsername() {
        return getProperty("login.username");
    }

    public static String getPassword() {
        return getProperty("login.password");
    }

    public static String getQaUrl() {
        return getProperty("qa.url");
    }

    public static String getStagingUrl() {
        return getProperty("staging.url");
    }

    // Resolves URL based on env name: "qa" or "staging"
    public static String getUrlForEnv(String env) {
        switch (env.toLowerCase().trim()) {
            case "staging": return getStagingUrl();
            case "qa":
            default:        return getQaUrl();
        }
    }

    // Returns list of usernames for data provider tests
    public static String[] getUsers() {
        return getProperty("login.users").split(",");
    }
}
