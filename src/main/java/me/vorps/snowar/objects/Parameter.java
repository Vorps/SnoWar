package me.vorps.snowar.objects;

import lombok.Getter;
import lombok.Setter;
import me.vorps.snowar.Exceptions.SqlException;
import me.vorps.snowar.databases.Database;

import java.sql.ResultSet;

/**
 * Project RushVolcano Created by Vorps on 21/04/2016 at 23:09.
 */
public class Parameter {
    private static @Getter String map;
    private static @Getter String label;
    private static @Getter @Setter int timeGame;

    public static void init(ResultSet result) throws SqlException{
        map = Database.SNOWAR.getDatabase().getString(result, 2);
        label = Database.SNOWAR.getDatabase().getString(result, 3);
        timeGame = Database.SNOWAR.getDatabase().getInt(result, 4);
    }
}
