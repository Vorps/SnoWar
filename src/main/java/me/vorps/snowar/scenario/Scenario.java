package me.vorps.snowar.scenario;

import lombok.Getter;
import lombok.Setter;
import me.vorps.snowar.Data;
import me.vorps.snowar.Exceptions.SqlException;
import me.vorps.snowar.databases.Database;
import me.vorps.snowar.lang.Lang;
import me.vorps.snowar.lang.LangSetting;
import me.vorps.snowar.menu.*;
import me.vorps.snowar.objects.Parameter;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Project RushVolcano Created by Vorps on 27/04/2016 at 16:41.
 */
public class Scenario {

    public enum Hour {
        MORNING(23000, "morning"),
        DAY(0, "day"),
        EVENING(12000, "evening"),
        NIGHT(13500, "night");

        private long time;
        private @Getter HashMap<String, String> label;

        Hour(long time, String label){
            this.label = new HashMap<>();
            this.time = time;
            for(LangSetting langSetting : LangSetting.getListLangSetting().values()){
                this.label.put(langSetting.getName(), "§a"+Lang.getMessage("SNO_WAR.SCENARIO."+label.toUpperCase(), langSetting.getName()));
            }
        }

        public static Hour getHour(long time){
            Hour hour = DAY;
            for(Hour hourTmp : Hour.values()){
                Scenario.hour++;
                if(hourTmp.time == time){
                    hour = hourTmp;
                    break;
                }
            }
            return hour;
        }
    }

    public enum Weather{
        SUN(""),
        RAIN(""),
        THUNDER("");

        private String label;

        Weather(String label){
            this.label = label;
        }
    }

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

    private static @Getter @Setter Menu menu;

    public static void addTimeBall(){
        if(Parameter.getTimeBall()+1 <= Scenario.ballTimeMax){
            Parameter.setTimeBall(Parameter.getTimeBall()+1);
            updateItemBallTime();
        }
    }

    public static void removeTimeBall(){
        if(Parameter.getTimeBall()-1 >= Scenario.ballCoolDownBallMin){
            Parameter.setTimeBall(Parameter.getTimeBall()-1);
            updateItemBallTime();
        }
    }

    private static void updateItemBallTime(){
        ((MenuCoolDown) menu).updateItem(new String[] {"§a"+Parameter.getTimeBall()}, 5);
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
        ((MenuCoolDown) menu).updateItem(new String[] {"§a"+Parameter.getNbrBall()}, 3);
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
        ((MenuCoolDown) menu).updateItem(new String[] {"§a"+Parameter.getCooldownBall()}, 1);
    }

    public static void removeDamage(){
        if(Parameter.getDamage()-1 >= Scenario.damageMin){
            Parameter.setDamage(Parameter.getDamage()-1);
            Scenario.updateItemDamage();
        }
    }

    private static void updateItemDamage(){
        ((MenuScenario) menu).updateItem(new String[] {"§a"+Parameter.getDamage()}, 6);
    }

    public static void setBonus(){
        Parameter.setBonus(!Parameter.isBonus());
        if(Parameter.isBonus())  ((MenuScenario) menu).updateItem(5, new Item(Material.SNOW_BLOCK).withName("§6Bonus").withLore(new String[] {"§7Configurer les bonus du jeu"}));
    }

    public static void removeSpeedBonus(){
        if(Parameter.getTimeBonus()-1 >= Scenario.speedBonusMin){
            Parameter.setTimeBonus(Parameter.getTimeBonus()-1);
            updateItemSpeedBonus();
        }
    }

    private static void updateItemSpeedBonus(){
        ((MenuBonus)menu).updateItem(new String[] {"§a"+Parameter.getTimeBonus()}, ((MenuBonus)menu).getSize()-6);
    }

    public static void addTime(){
        if(Parameter.getTimeGame()+60 <= Scenario.timeMax){
            Parameter.setTimeGame(Parameter.getTimeGame()+60);
            updateItemTime();
        }
    }

    public static void setCoolDown(){
        Parameter.setCoolDownBallState(!Parameter.isCoolDownBallState());
        if(Parameter.isCoolDownBallState())  ((MenuScenario) menu).updateItem(7, new Item(349).withData((byte) 3).withName("§6Cooldown").withLore(new String[] {"§7Configurer le cooldown Ball"}));
    }

    public static void removeTime(){
        if(Parameter.getTimeGame()-60 >= Scenario.timeMin){
            Parameter.setTimeGame(Parameter.getTimeGame()-60);
            updateItemTime();
        }
    }

    public static void addHour(String lang){
        if(++hour >= 4) hour = 0;
        Hour hourVar = Hour.values()[hour];
        Bukkit.getWorlds().get(0).setTime(hourVar.time);
        ((MenuTimes) menu).updateItem(new String[] {hourVar.label.get(lang)}, 1);
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
        if(Parameter.isFall()) ((MenuScenario) menu).updateItem((byte) 10,new String[] {"§aActivé"}, 3);
        else ((MenuScenario) menu).updateItem((byte) 8,new String[] {"§cDésactivé"}, 3);

    }

    public static void setCycle(){
        Parameter.setCycle(!Parameter.isCycle());
        Bukkit.getWorlds().get(0).setGameRuleValue("doDaylightCycle", ""+Parameter.isCycle());
        if(Parameter.isCycle()) ((MenuTimes) menu).updateItem((byte) 10,new String[] {"§aActivé"}, 4);
        else ((MenuTimes) menu).updateItem((byte) 8,new String[] {"§cDésactivé"}, 4);
    }


    private static void updateItemTime(){
        Date date = new Date(Parameter.getTimeGame()*1000);
        date.setHours(date.getHours()-1);
        ((MenuScenario) menu).updateItem(new String[] {"§6Temps : §a"+new SimpleDateFormat("HH:mm:ss").format(date)}, 0);
    }

    private static void updateItemNbrPlayer(){
        ((MenuScenario) menu).updateItem(new String[] {"§a"+Data.getNbPlayerMax()}, 2);
    }
}
