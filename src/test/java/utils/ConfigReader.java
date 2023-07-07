package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    static Properties properties;

    public static Properties readProperties(String filePath){
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e){
            e.printStackTrace();
        }
        return properties;
    }

    public static String getPropertyValue (String key){
        return properties.getProperty(key);
    }
}
