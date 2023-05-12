package com.example.springbootapirestbacked.music;
//import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class SetupLog4j {

    static final Logger logger = Logger.getLogger(String.valueOf(SetupLog4j.class));
    //static final String log4jPropertyFile = "C:/config/logback.xml";

  public static void main(String[] args) {
       /* String message = "This is a String";
        Integer zero = 0;
        try {
            logger.debug("Logging message: {}", message);
            logger.debug("Going to divide {} by {}", 42, zero);
            int result = 42 / zero;
        } catch (Exception e) {
            logger.error("Error dividing {} by {} ", 42, zero, e);

    }
   // }
}

        */
logger.info("funciona");
  }}
