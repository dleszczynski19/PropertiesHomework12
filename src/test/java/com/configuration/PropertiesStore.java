package com.configuration;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public enum PropertiesStore {
    BROWSER("browser"),
    BROWSER_WEBELEMENT_TIMEOUT("browser.webelement.timeout"),
    BROWSER_HEADLESS("browser.headless"),
    ENVIRONMENT("environment");

    private String value;
    private String propertyKey;
    private static Properties properties = null;
    public static final String CONFIG_PROPERTIES = "config.properties";

    PropertiesStore(String propertyKey) {
        this.propertyKey = propertyKey;
        this.value = this.retrieveValue(propertyKey);
    }

    private String retrieveValue(String key) {
        return System.getProperty(key) != null ? System.getProperty(key) : getValueFromConfigFile(key);
    }

    private String getValueFromConfigFile(String key) {
        if (properties == null) {
            properties = loadConfigFile();
        }
        Object objFromFile = properties.get(key);
        return objFromFile != null ? Objects.toString(objFromFile) : null;
    }

    private static Properties loadConfigFile() {
        Properties copy = null;
        try {
            InputStream configFileStream = ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties");
            try {
                Properties properties = new Properties();
                properties.load(configFileStream);
                copy = properties;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (configFileStream != null) {
                    try {
                        configFileStream.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return copy;
    }

    public boolean isSpecified() {
        return StringUtils.isNotEmpty(this.value);
    }

    public String getValue() {
        return this.retrieveValue(this.propertyKey);
    }

    public int getIntValue() {
        return Integer.parseInt(this.retrieveValue(this.propertyKey));
    }

    public boolean getBooleanValue() {
        return Boolean.parseBoolean(this.retrieveValue(this.propertyKey));
    }
}