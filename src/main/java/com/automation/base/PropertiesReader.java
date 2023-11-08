package com.automation.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

	 public static Properties config = new Properties();

	 

     public static Properties readProp() {

                     FileInputStream fis = null;

                     try {

                                     fis = new FileInputStream(System.getProperty("user.dir")

                                                                     + "/src/test/resources/config/Config.properties");

                                     config.load(fis);

                     } catch (IOException e) {

                                     e.printStackTrace();

                     }

                     return config;

     }



     public static String getPropertyValue(String prop) {

                     String propertyValue = null;

                     try {

                                     propertyValue = readProp().getProperty(prop);

                                     if (propertyValue.isEmpty()) {

                                                     throw new NullPointerException();

                                     }

                     } catch (Exception e) {

                     e.printStackTrace();

                     }

                     return propertyValue;

     }

    

}

