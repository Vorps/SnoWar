package me.vorps.snowar.databases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Project SnoWar Created by Vorps on 26/07/2016 at 23:30.
 */
public class File {

    /**
     * Read File
     * @param path String
     * @return ArrayList<String>
     */
    public static ArrayList<String> systemRead(String path){
        ArrayList<String> data = new ArrayList<>();
        String line;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
            reader.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
