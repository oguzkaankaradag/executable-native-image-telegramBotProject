package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class ConfigPropertiesReader {

    private static final  String defaultFilePath = "~/Documents/ExecutableNativeImageTelegramBot/src/main/resources/config.properties";
    //check where config properties file comes from


    public static void checkCommandLineArgs(String[] args) {

        String filePath = "";

        if (args.length < 1) {
            filePath = defaultFilePath;
        }
        else
            filePath = args[0];
    }

    //Get desired value from key:value pair config properties file
    public String getValueFromFile(String key) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(defaultFilePath);
        Properties properties = new Properties();
        properties.load(fileInputStream);

        //Convert Enumeration type allKeys to List type
        Enumeration<?> allKeys = properties.propertyNames();
        List<String> keyList = new ArrayList<>();
        while (allKeys.hasMoreElements()) {
            String k = (String) allKeys.nextElement();
            keyList.add(k);
        }

        //Check  all keys for existence of key in properties file
        String value = "";
        if(keyList.contains(key)) {
            value = properties.getProperty(key);
        }
        else
            System.out.println("invalid method argument. "  + key + "does not exist in properties file !!");

        return value;
    }

}
