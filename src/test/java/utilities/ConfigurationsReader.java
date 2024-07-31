package utilities;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Utility class to read configuration properties from a file.
 */
public class ConfigurationsReader {
    private static Properties configFile;

    static {
        try {
            String path = "config.properties";
            FileInputStream input = new FileInputStream(path);
            configFile = new Properties();
            configFile.load(input);
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the property value for the given key.
     *
     * @param key the property key
     * @return the property value
     */
    public static String getProperty(String key) {
        return configFile.getProperty(key);
    }
}