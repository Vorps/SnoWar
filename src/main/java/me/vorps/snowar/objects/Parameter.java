package me.vorps.snowar.objects;

import lombok.Getter;
import lombok.Setter;
import me.vorps.snowar.Exceptions.SqlException;
import me.vorps.snowar.databases.Database;

import java.sql.ResultSet;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class Parameter {
    private static @Getter String map;
    private static @Getter String label;
    private static @Getter @Setter int timeGame;
    private static @Getter @Setter int cooldownBall;
    private static @Getter @Setter int nbrBall;
    private static @Getter @Setter int timeBall;
    private static @Getter @Setter int ball;
    private static @Getter @Setter double damage;
    private static @Getter int timeBonus;

    public static void init(ResultSet result) throws SqlException{
        map = Database.SNOWAR.getDatabase().getString(result, 2);
        label = Database.SNOWAR.getDatabase().getString(result, 3);
        timeGame = Database.SNOWAR.getDatabase().getInt(result, 4);
        cooldownBall = Database.SNOWAR.getDatabase().getInt(result, 6);
        nbrBall = Database.SNOWAR.getDatabase().getInt(result, 7);
        timeBall = Database.SNOWAR.getDatabase().getInt(result, 8);
        ball = Database.SNOWAR.getDatabase().getInt(result, 9);
        damage = Database.SNOWAR.getDatabase().getDouble(result, 10);
        timeBonus = Database.SNOWAR.getDatabase().getInt(result, 11);
    }
}
