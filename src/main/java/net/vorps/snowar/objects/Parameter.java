package net.vorps.snowar.objects;

import lombok.Getter;
import lombok.Setter;
import net.vorps.api.Exceptions.SqlException;
import net.vorps.api.databases.Database;
import net.vorps.snowar.utils.Hour;
import net.vorps.snowar.utils.Weather;
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
    private static @Getter @Setter boolean coolDownBallState;
    private static @Getter @Setter int nbrBall;
    private static @Getter @Setter int timeBall;
    private static @Getter @Setter int ball;
    private static @Getter @Setter double damage;
    private static @Getter @Setter int timeBonus;
    private static @Getter @Setter int life;
    private static @Getter Earning earning;
    private static @Getter @Setter Hour hour;
    private static @Getter @Setter boolean fall;
    private static @Getter @Setter boolean cycle;
    private static @Getter @Setter boolean bonus;
    private static @Getter @Setter Weather weather;

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
        Parameter.hour = Hour.valueOf(Database.SNOWAR.getDatabase().getString(result, 13));
        Parameter.fall = Database.SNOWAR.getDatabase().getBoolean(result, 14);
        Parameter.cycle = Database.SNOWAR.getDatabase().getBoolean(result, 15);
        Parameter.bonus = Database.SNOWAR.getDatabase().getBoolean(result, 16);
        Parameter.weather = Weather.valueOf(Database.SNOWAR.getDatabase().getString(result, 17));
        Parameter.coolDownBallState = Parameter.cooldownBall > 0;
        Bukkit.getWorlds().get(0).setGameRuleValue("doDaylightCycle", ""+Parameter.cycle);
        Weather.setWeather();
        Hour.setHour();
    }
}
