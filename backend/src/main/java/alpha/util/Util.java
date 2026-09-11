package alpha.util;

import alpha.exception.ApiException;
import alpha.exception.ResourceNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

// ################################### //
//      .properties file handler       //
//          - Dev Environment          //
// ################################### //

// Created by: Guacamoleboy
// ________________________
// Last updated: 28/02-2026
// By: Guacamoleboy

public class Util {

    // Attributes
    private static String root_path = "src/main/resources";
    private static boolean isTest = false;

    // _________________________________________________________________________________________________________________

    public static void setTestMode(boolean testMode) {
        isTest = testMode;
        root_path = isTest ? "src/test/resources" : "src/main/resources";
    }

    // _________________________________________________________________________________________________________________

    public static String getPropertyValue(String propertyName, String resourceName) {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(resourceName)) {
            if (inputStream == null) {
                throw new ResourceNotFoundException("Property file", resourceName);
            }
            Properties properties = new Properties();
            properties.load(inputStream);
            String value = properties.getProperty(propertyName);
            // Java 17 doesn't support null in switch-case.
            if (value == null) {
                throw new ResourceNotFoundException("Property: " + propertyName, resourceName);
            }
            switch (value) {
                case "\n":
                case "\r\n":
                    return "\\n";
                default:
                    return value.trim();
            }
        } catch (IOException ex) {
            throw new ApiException(500, "Could not read property", ex);
        }
    }

    // _________________________________________________________________________________________________________________

    public static List<String> fileMissingSearcher(String resourceName) {
        File folder = new File(root_path);
        if (!folder.exists() || !folder.isDirectory()) {
            return new ArrayList<>();
        }
        List<String> fileSuggestions = new ArrayList<>();
        Arrays.stream(folder.listFiles())
                .filter(File::isFile)
                .map(File::getName)
                .filter(name -> name.toLowerCase().contains(".properties"))
                .forEach(fileSuggestions::add);
        return fileSuggestions;
    }

}