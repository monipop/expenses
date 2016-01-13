package util;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 7/16/14
 * Time: 8:55 PM
 */
public class Files {

    public static Map<String, String> readProperties(String fileName) {
        Properties prop = new Properties();
        Map<String, String> propSet = new HashMap<>();
        InputStream input = null;
        String key = null;
        String value = null;

        try {
            input = new FileInputStream(fileName);

            //load the properties file
            prop.load(input);
            Enumeration<?> propNames = prop.propertyNames();

            while (propNames.hasMoreElements()) {
                key = propNames.nextElement().toString();
                value = prop.getProperty(key);
                propSet.put(key, value);
            }
        } catch (IOException e) {
            String message = String.format("File %s was not found.", fileName);
            throw new IllegalArgumentException(message, e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return propSet;
    }

    public static void writeFile(String path, String name, String text) {
        String fileName = path + name;

        try {
            File file = new File(fileName);
            FileOutputStream is = new FileOutputStream(file);
            OutputStreamWriter output = new OutputStreamWriter(is);
            //BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(text);
            output.close();

        } catch (IOException e) {
            throw new IllegalArgumentException("Problem writing the file" + fileName, e);
        }
    }
}
