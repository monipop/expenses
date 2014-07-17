import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
}
