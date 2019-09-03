package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    public static Properties properties = null;

    static {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getSystemResourceAsStream("Utils/utils.properties");
        try {
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    //test
    public static void main(String[] args){
        PropertiesUtil.getProperty("solrUrl");
    }
}
