package org.lwjglb.engine;

import java.io.InputStream;
import java.util.Scanner;

public class Utils {

    public static String Load(String location) throws Exception{
        // Create the string
        String contents;
        // Load contents
        try (InputStream in = Class.forName(Utils.class.getName()).getResourceAsStream(location);
             Scanner scanner = new Scanner(in, "UTF-8")) {
            contents = scanner.useDelimiter("\\A").next();
        }
        return contents;
    }
}
