package me.vorps.snowar.objects;

import lombok.Getter;
import lombok.Setter;
import me.vorps.snowar.Data;
import me.vorps.snowar.Exceptions.SqlException;
import me.vorps.snowar.databases.Database;
import me.vorps.snowar.scenario.Scenario;
import org.bukkit.Bukkit;

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
    private static @Getter @Setter int timeBonus;
    private static @Getter int life;
    private static @Getter Earning earning;
    private static @Getter int hour;
    private static @Getter @Setter boolean fall;
    private static @Getter @Setter boolean cycle;
    private static @Getter @Setter boolean bonus;

    /**
     * Init variable server
     * @param result ResultSet
     * @throws SqlException
     */
    public static void init(ResultSet result) throws SqlException{
        Parameter.map = Database.SNOWAR.getDatabase().getString(result, 2);
        Parameter.label = Database.SNOWAR.getDatabase().getString(result, 3);
        Parameter.timeGame = Database.SNOWAR.getDatabase().getInt(result, 4);
        Parameter.earning = new Earning(Database.SNOWAR.getDatabase().getString(result, 5));
        Parameter.cooldownBall = Database.SNOWAR.getDatabase().getInt(result, 6);
        Parameter.nbrBall = Database.SNOWAR.getDatabase().getInt(result, 7);
        Parameter.timeBall = Database.SNOWAR.getDatabase().getInt(result, 8);
        Parameter.ball = Database.SNOWAR.getDatabase().getInt(result, 9);
        Parameter.damage = Database.SNOWAR.getDatabase().getDouble(result, 10);
        Parameter.timeBonus = Database.SNOWAR.getDatabase().getInt(result, 11);
        Parameter.life = Database.SNOWAR.getDatabase().getInt(result, 12);
        Parameter.hour = Database.SNOWAR.getDatabase().getInt(result, 13);
        Parameter.fall = Database.SNOWAR.getDatabase().getBoolean(result, 14);
        Parameter.cycle = Database.SNOWAR.getDatabase().getBoolean(result, 15);
        Parameter.bonus = Database.SNOWAR.getDatabase().getBoolean(result, 16);
        Bukkit.getWorlds().get(0).setGameRuleValue("doDaylightCycle", ""+cycle);
        Bukkit.getWorlds().get(0).setTime(hour);
    }
}
