package me.vorps.snowar.scenario;

import lombok.Getter;
import lombok.Setter;
import me.vorps.snowar.Data;
import me.vorps.snowar.Exceptions.SqlException;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.databases.Database;
import me.vorps.snowar.lang.Lang;
import me.vorps.snowar.menu.*;
import me.vorps.snowar.objects.Parameter;
import me.vorps.snowar.utils.Hour;
import me.vorps.snowar.utils.Weather;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project RushVolcano Created by Vorps on 27/04/2016 at 16:41.
 */
public class Scenario {

    public static void init(ResultSet resultSet) throws SqlException{
        timeMin = Database.SNOWAR.getDatabase().getInt(resultSet, 2);
        timeMax = Database.SNOWAR.getDatabase().getInt(resultSet, 3);
        nbPlayerMin = Database.SNOWAR.getDatabase().getInt(resultSet, 4);
        nbPlayerMax = Database.SNOWAR.getDatabase().getInt(resultSet, 5);
        speedBonusMin = Database.SNOWAR.getDatabase().getInt(resultSet, 6);
        speedBonusMax = Database.SNOWAR.getDatabase().getInt(resultSet, 7);
        damageMin = Database.SNOWAR.getDatabase().getDouble(resultSet, 8);
        damageMax = Database.SNOWAR.getDatabase().getDouble(resultSet, 9);
        coolDownBallMax = Database.SNOWAR.getDatabase().getInt(resultSet, 10);
        ballCoolDownBallMin = Database.SNOWAR.getDatabase().getInt(resultSet, 11);
        ballCoolDownBallMax = Database.SNOWAR.getDatabase().getInt(resultSet, 12);
        ballTimeMin = Database.SNOWAR.getDatabase().getInt(resultSet, 13);
        ballTimeMax = Database.SNOWAR.getDatabase().getInt(resultSet, 14);
    }

    private static int timeMin;
    private static int timeMax;
    private static int nbPlayerMin;
    private static int nbPlayerMax;
    private static int hour;
    private static int speedBonusMin;
    private static int speedBonusMax;
    private static double damageMin;
    private static double damageMax;
    private static int coolDownBallMax;
    private static int ballCoolDownBallMin;
    private static int ballCoolDownBallMax;
    private static int  ballTimeMin;
    private static int  ballTimeMax;
    private static @Getter int weather;

    public static void addWeather(){
        Scenario.weather++;
    }

    public static void addHour(){
        hour++;
    }

    private static @Getter @Setter Menu menu;

    public static void setWeather(){
        if(++weather >= 3) weather = 0;
        Weather weather = Weather.values()[Scenario.weather];
        weather.setWeather();
        ((MenuTimes) menu).updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.WEATHER.LORE", ((MenuTimes) menu).getPlayerData().getLang(), new Lang.Args(Lang.Parameter.VAR, weather.getLabel(((MenuTimes) menu).getPlayerData().getLang())))}, 6);
    }


    public static void addTimeBall(){
        if(Parameter.getTimeBall()+1 <= Scenario.ballTimeMax){
            Parameter.setTimeBall(Parameter.getTimeBall()+1);
            updateItemBallTime();
        }
    }

    public static void removeTimeBall(){
        if(Parameter.getTimeBall()-1 >= Scenario.ballTimeMin){
            Parameter.setTimeBall(Parameter.getTimeBall()-1);
            updateItemBallTime();
        }
    }

    private static void updateItemBallTime(){
        ((MenuCoolDown) menu).updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.TIME_BALL.LORE", ((MenuCoolDown) menu).getPlayerData().getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getTimeBall()))}, 5);
    }

    public static void addBallCoolDown(){
        if(Parameter.getNbrBall()+1 <= Scenario.ballCoolDownBallMax){
            Parameter.setNbrBall(Parameter.getNbrBall()+1);
            updateItemCooldownBall();
        }
    }

    public static void removeBallCoolDown(){
        if(Parameter.getNbrBall()-1 >= Scenario.ballCoolDownBallMin){
            Parameter.setNbrBall(Parameter.getNbrBall()-1);
            updateItemCooldownBall();
        }
    }


    private static void updateItemCooldownBall(){
        ((MenuCoolDown) menu).updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.BALL.LORE", ((MenuCoolDown) menu).getPlayerData().getLang(), new Lang.Args(Lang.Parameter.VAR, "§a"+Parameter.getNbrBall()))}, 3);
    }

    public static void addSpeedBonus(){
        if(Parameter.getTimeBonus()+1 <= Scenario.speedBonusMax){
            Parameter.setTimeBonus(Parameter.getTimeBonus()+1);
            updateItemSpeedBonus();
        }
    }

    public static void addDamage(){
        if(Parameter.getDamage()+1 <= Scenario.damageMax){
            Parameter.setDamage(Parameter.getDamage()+1);
            Scenario.updateItemDamage();
        }
    }

    public static void addTimeCoolDown(){
        if(Parameter.getCooldownBall()+1 <= Scenario.coolDownBallMax){
            Parameter.setCooldownBall(Parameter.getCooldownBall()+1);
            updateItemCooldownTime();
        }
    }

    public static void removeTimeCoolDown(){
        if(Parameter.getCooldownBall()-1 >= 0){
            Parameter.setCooldownBall(Parameter.getCooldownBall()-1);
            updateItemCooldownTime();
        }
    }

    private static void updateItemCooldownTime(){
        ((MenuCoolDown) menu).updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.TIME_BALL.LORE", ((MenuCoolDown) menu).getPlayerData().getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getTimeBall()))}, 1);
    }

    public static void removeDamage(){
        if(Parameter.getDamage()-1 >= Scenario.damageMin){
            Parameter.setDamage(Parameter.getDamage()-1);
            Scenario.updateItemDamage();
        }
    }

    private static void updateItemDamage(){
        ((MenuScenario) menu).updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.DAMAGE_LORE", ((MenuScenario) menu).getPlayerData().getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getDamage()))}, 7);
    }

    public static void setBonus(){
        Parameter.setBonus(!Parameter.isBonus());
        if(Parameter.isBonus())  ((MenuScenario) menu).updateItem(5, new Item(80).withName(Lang.getMessage("SNO_WAR.SCENARIO.BONUS_LABEL", ((MenuScenario) menu).getPlayerData().getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LORE", ((MenuScenario) menu).getPlayerData().getLang())}));
    }

    public static void removeSpeedBonus(){
        if(Parameter.getTimeBonus()-1 >= Scenario.speedBonusMin){
            Parameter.setTimeBonus(Parameter.getTimeBonus()-1);
            updateItemSpeedBonus();
        }
    }

    private static void updateItemSpeedBonus(){
        ((MenuBonus)menu).updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_SPEED_BONUS.LORE",  ((MenuBonus)menu).getPlayerData().getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getTimeBonus()))}, ((MenuBonus)menu).getSize()-6);
    }

    public static void addTime(){
        if(Parameter.getTimeGame()+60 <= Scenario.timeMax){
            Parameter.setTimeGame(Parameter.getTimeGame()+60);
            updateItemTime();
        }
    }

    public static void setCoolDown(){
        Parameter.setCoolDownBallState(!Parameter.isCoolDownBallState());
        if(Parameter.isCoolDownBallState())  ((MenuScenario) menu).updateItem(8, new Item(349).withData((byte) 3).withName(Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LABEL", ((MenuScenario) menu).getPlayerData().getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LORE", ((MenuScenario) menu).getPlayerData().getLang())}));
    }

    public static void removeTime(){
        if(Parameter.getTimeGame()-60 >= Scenario.timeMin){
            Parameter.setTimeGame(Parameter.getTimeGame()-60);
            updateItemTime();
        }
    }

    public static void addHourFunction(){
        if(++hour >= 4) hour = 0;
        Hour hourVar = Hour.values()[hour];
        Bukkit.getWorlds().get(0).setTime(hourVar.getTime());
        ((MenuTimes) menu).updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.HOUR.LORE", ((MenuTimes) menu).getPlayerData().getLang(), new Lang.Args(Lang.Parameter.VAR, hourVar.getLabel().get(((MenuTimes) menu).getPlayerData().getLang())))}, 2);
    }

    public static void addNbrPlayer(){
        if(Data.getNbPlayerMax()+1 <= Scenario.nbPlayerMax){
            Data.setNbPlayerMax(Data.getNbPlayerMax()+1);
            updateItemNbrPlayer();
        }
    }

    public static void removeNbrPlayer(){
        if(Data.getNbPlayerMax()-1 >= Scenario.nbPlayerMin){
            Data.setNbPlayerMax(Data.getNbPlayerMax()-1);
            updateItemNbrPlayer();
        }
    }

    public static void setFall(){
        Parameter.setFall(!Parameter.isFall());
        if(Parameter.isFall()) ((MenuScenario) menu).updateItem((byte) 10,new String[] {Lang.getMessage("SNO_WAR.SCENARIO_ENABLE", ((MenuScenario) menu).getPlayerData().getLang())}, 3);
        else ((MenuScenario) menu).updateItem((byte) 8,new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", ((MenuScenario) menu).getPlayerData().getLang())}, 3);

    }

    public static void setCycle(){
        Parameter.setCycle(!Parameter.isCycle());
        Bukkit.getWorlds().get(0).setGameRuleValue("doDaylightCycle", ""+Parameter.isCycle());
        if(Parameter.isCycle()) ((MenuTimes) menu).updateItem((byte) 10,new String[] {Lang.getMessage("SNO_WAR.SCENARIO_ENABLE", ((MenuTimes) menu).getPlayerData().getLang())}, 4);
        else ((MenuTimes) menu).updateItem((byte) 8,new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", ((MenuTimes) menu).getPlayerData().getLang())}, 4);
    }


    private static void updateItemTime(){
        Date date = new Date(Parameter.getTimeGame()*1000);
        date.setHours(date.getHours()-1);
        ((MenuScenario) menu).updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.TIME_GAME", ((MenuScenario) menu).getPlayerData().getLang(), new Lang.Args(Lang.Parameter.TIME, new SimpleDateFormat("HH:mm:ss").format(date)))}, 0);
    }

    private static void updateItemNbrPlayer(){
        ((MenuScenario) menu).updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.PLAYER.LORE", ((MenuScenario) menu).getPlayerData().getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Data.getNbPlayerMax()))}, 4);
    }
}
