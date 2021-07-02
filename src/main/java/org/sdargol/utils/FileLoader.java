package org.sdargol.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class FileLoader {
    private static final Logger LOGGER = Log.getLogger(FileLoader.class.getName());

    private static final String MAIN_DIR = System.getProperty("user.dir");

    public static String getMainDir(){
        return MAIN_DIR;
    }

    public static String listToString(List<String> data){
        StringBuilder builder = new StringBuilder();

        data.forEach(builder::append);

        String str = builder.toString();

        LOGGER.info("Success load: " + str);

        return str;
    }

    public static List<String> load(String path){
        String filePath = getMainDir() + path;
        File file = new File(filePath);
        List<String> data = new ArrayList<>();

        try(Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                data.add(reader.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return data;
    }
}
