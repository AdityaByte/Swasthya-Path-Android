package in.ayush.swasthyapath.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {

    public static String getBackendUrl(Context context) {
        Properties properties = new Properties();
        try (InputStream inputStream = context.getAssets().open("config.properties")) {
            properties.load(inputStream);
            return properties.getProperty("BACKEND_URL");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
