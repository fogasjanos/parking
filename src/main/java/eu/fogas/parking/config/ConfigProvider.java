package eu.fogas.parking.config;

import eu.fogas.parking.exception.ConfigurationRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class ConfigProvider {
    private static final String DEFAULT_CONFIG_NAME = "config.properties";

    private final Properties properties = new Properties();

    public ConfigProvider() {
        this(DEFAULT_CONFIG_NAME);
    }

    public ConfigProvider(String configName) {
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream(configName)) {
            if (input == null) {
                log.error("Unable to find {}", configName);
                return;
            }
            properties.load(input);
        } catch (IOException ioe) {
            log.error("Cannot read config file: {}", configName, ioe);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getIntProperty(String key) {
        try {
            return Integer.parseInt(getProperty(key));
        } catch (NumberFormatException nfe) {
            log.error("Invalid property: {}", key, nfe);
            throw new ConfigurationRuntimeException("Invalid property: " + key, nfe);
        }
    }

    public int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(getProperty(key));
        } catch (NumberFormatException nfe) {
            log.debug("Returning with default value {} for property: {}", defaultValue, key);
            return defaultValue;
        }
    }
}
