package com.ms.usermanagement.util;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.util.Properties;

public class Logger4j {
    private static Logger logger = null;
    static final String LOG_PROPERTIES_FILE = "log4j.properties";
    public static Logger getLogger() {
        if (logger != null)
            return logger;
        else {
            logger = Logger.getLogger(Logger4j.class);
            initializeLogger();
        }
        return logger;
    }
    private static void initializeLogger() {
        BasicConfigurator.configure();
        Properties logProperties = new Properties();
        try {
            // load our log4j properties / configuration file
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            logProperties.load(loader.getResourceAsStream(LOG_PROPERTIES_FILE));
            PropertyConfigurator.configure(logProperties);
            logger.info("Log4j has been initialized.");
        } catch (IOException e) {
            e.printStackTrace();
            Logger4j.getLogger().error("Exception : ", e);
            throw new RuntimeException("Unable to load logging property " + LOG_PROPERTIES_FILE);
        }
    }
}
