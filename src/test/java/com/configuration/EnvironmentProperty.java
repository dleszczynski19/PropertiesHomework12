package com.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class EnvironmentProperty {
    private final String APP_ENVIRONMENT;
    private static Logger log = LoggerFactory.getLogger(EnvironmentProperty.class);
    private final BrowserEnvironment browserEnvironment;

    private EnvironmentProperty() {
        this.APP_ENVIRONMENT = initAppEnvironment();
        this.initEnv();
        this.browserEnvironment = new BrowserEnvironment();
    }

    private static String initAppEnvironment() {
        return PropertiesStore.ENVIRONMENT.isSpecified() ? PropertiesStore.ENVIRONMENT.getValue() : "";
    }

    public static EnvironmentProperty getInstance() {
        return EnvironmentProperty.EnvironmentPropertySingleton.INSTANCE;
    }

    public BrowserEnvironment getBrowserEnvironment() {
        return browserEnvironment;
    }

    private void initEnv() {
        if (!this.APP_ENVIRONMENT.isEmpty()) {
            log.info("--------------CONFIG--------------");
            log.info("Environment name : " + this.APP_ENVIRONMENT);
            loadAllEnvPropertiesToSystem(this.APP_ENVIRONMENT);
        } else {
            log.error("Please provide \"environment\" property");
            assertThat(true, equalTo(false));
        }
    }

    private void loadAllEnvPropertiesToSystem(String appEnvironment) {
        setSystemPropertiesFromPathUrl(appEnvironment);
    }

    private static void setSystemPropertiesFromPathUrl(String directoryName) {
        URL url = EnvironmentProperty.class.getClassLoader().getResource(directoryName);
        if (url != null) {
            Properties environmentProperties = new Properties();
            try {
                Stream<Path> files = Files.walk(Paths.get(url.toURI()));
                try {
                    ((List)files.filter((x$0) -> {
                        return Files.isRegularFile(x$0, new LinkOption[0]);
                    }).collect(Collectors.toList())).forEach((path) -> {
                        try {
                            environmentProperties.load(new FileInputStream(path.toString()));
                        } catch (IOException var3) {
                            log.error("error 1");

                        }

                    });
                } catch (Exception e) {
                    log.error("error 2");
                } finally {
                    if (files != null) {
                        try {
                            files.close();
                        } catch (Throwable var13) {
                            log.error("error 3");
                        }
                    } else {
                        files.close();
                    }
                }

            } catch (Exception r) {
                log.error("error 4");

            }

            log.info("Loading property from uri {}", url);
            environmentProperties.forEach((propertyName, propertyValue) -> {
                if (System.getProperty(propertyName.toString()) == null) {
                    System.setProperty(propertyName.toString(), propertyValue.toString());
                    log.debug("****Loading environment property {} = {} ", propertyName, propertyValue);
                }
            });
            log.info("Properties loaded from {} : {} ", directoryName, environmentProperties.size());
        } else {
            log.warn("No such property directory '{}' present in the resources ,make sure you are providing correct directory name.", directoryName);
        }
    }

    private static class EnvironmentPropertySingleton {
        private static final EnvironmentProperty INSTANCE = new EnvironmentProperty();
    }
}