package me.vorps.snowar.scenario;

import lombok.Getter;
import lombok.Setter;
import me.vorps.snowar.Data;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.menu.*;
import me.vorps.snowar.objects.Parameter;
import me.vorps.snowar.utils.Hour;
import me.vorps.snowar.utils.Weather;
import me.vorps.syluriapi.Exceptions.SqlException;
import me.vorps.syluriapi.databases.Database;
import me.vorps.syluriapi.lang.Lang;
import me.vorps.syluriapi.menu.Item;
import me.vorps.syluriapi.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project RushVolcano Created by Vorps on 27/04/2016 at 16:41.
 */
public class Scenario {

    private static @Getter PlayerData playerData;
    private static @Getter @Setter Menu menu;
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
    private static int lifeMax;
    private static int lifeMin;

    public static void init(ResultSet resultSet) throws SqlException{
        Scenario.timeMin = Database.SNOWAR.getDatabase().getInt(resultSet, 2);
        Scenario.timeMax = Database.SNOWAR.getDatabase().getInt(resultSet, 3);
        Scenario.nbPlayerMin = Database.SNOWAR.getDatabase().getInt(resultSet, 4);
        Scenario.nbPlayerMax = Database.SNOWAR.getDatabase().getInt(resultSet, 5);
        Scenario.speedBonusMin = Database.SNOWAR.getDatabase().getInt(resultSet, 6);
        Scenario.speedBonusMax = Database.SNOWAR.getDatabase().getInt(resultSet, 7);
        Scenario.damageMin = Database.SNOWAR.getDatabase().getDouble(resultSet, 8);
        Scenario.damageMax = Database.SNOWAR.getDatabase().getDouble(resultSet, 9);
        Scenario.coolDownBallMax = Database.SNOWAR.getDatabase().getInt(resultSet, 10);
        Scenario.ballCoolDownBallMin = Database.SNOWAR.getDatabase().getInt(resultSet, 11);
        Scenario.ballCoolDownBallMax = Database.SNOWAR.getDatabase().getInt(resultSet, 12);
        Scenario.ballTimeMin = Database.SNOWAR.getDatabase().getInt(resultSet, 13);
        Scenario.ballTimeMax = Database.SNOWAR.getDatabase().getInt(resultSet, 14);
        Scenario.lifeMin = Database.SNOWAR.getDatabase().getInt(resultSet, 15);
        Scenario.lifeMax = Database.SNOWAR.getDatabase().getInt(resultSet, 16);
    }


    public static void setItem(int i){
        Scenario.menu.getMenu().getItem(i).setAmount(Scenario.menu.getMenu().getItem(i).getAmount()+1);
    }

    public static void updateItem(int index ,Item items){
        Scenario.menu.getMenu().setItem(index, items.get());
        Scenario.getPlayerData().getPlayer().updateInventory();
    }

    public static void updateItem(String[] lore, int index){
        ItemStack itemStack = Scenario.menu.getMenu().getItem(index);
        Scenario.menu.getMenu().setItem(index, new Item(itemStack.getType()).withName(itemStack.getItemMeta().getDisplayName()).withLore(lore).get());
        Scenario.getPlayerData().getPlayer().updateInventory();
    }

    public static void updateItem(byte data, String[] lore, int index){
        ItemStack itemStack =  Scenario.menu.getMenu().getItem(index);
        Scenario.menu.getMenu().setItem(index, new Item(itemStack.getType()).withData(data).withName(itemStack.getItemMeta().getDisplayName()).withLore(lore).get());
        Scenario.getPlayerData().getPlayer().updateInventory();
    }


    public static void addWeather(){
        Scenario.weather++;
    }

    public static void addHour(){
        Scenario.hour++;
    }

    public static void openScenario(PlayerData playerData){
        Scenario.playerData = playerData;
        Scenario.menu = new MenuScenario();
    }

    public static void closeScenario(){
        Scenario.playerData.getPlayer().closeInventory();
        Scenario.playerData = null;
        Scenario.setMenu(null);
    }


    public static void setWeather(){
        if(++Scenario.weather >= 3) Scenario.weather = 0;
        Weather weather = Weather.values()[Scenario.weather];
        weather.setWeather();
        Scenario.updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.WEATHER.LORE", Scenario.getPlayerData().getLang(), new Lang.Args(Lang.Parameter.VAR, weather.getLabel(Scenario.getPlayerData().getLang())))}, 6);
    }


    public static void addTimeBall(){
        if(Parameter.getTimeBall()+1 <= Scenario.ballTimeMax){
            Parameter.setTimeBall(Parameter.getTimeBall()+1);
            Scenario.updateItemBallTime();
        }
    }

    public static void removeTimeBall(){
        if(Parameter.getTimeBall()-1 >= Scenario.ballTimeMin){
            Parameter.setTimeBall(Parameter.getTimeBall()-1);
            Scenario.updateItemBallTime();
        }
    }

    private static void updateItemBallTime(){
        Scenario.updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.TIME_BALL.LORE", Scenario.getPlayerData().getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getTimeBall()))}, 5);
    }

    public static void addBallCoolDown(){
        if(Parameter.getNbrBall()+1 <= Scenario.ballCoolDownBallMax){
            Parameter.setNbrBall(Parameter.getNbrBall()+1);
            Scenario.updateItemCooldownBall();
        }
    }

    public static void removeBallCoolDown(){
        if(Parameter.getNbrBall()-1 >= Scenario.ballCoolDownBallMin){
            Parameter.setNbrBall(Parameter.getNbrBall()-1);
            Scenario.updateItemCooldownBall();
        }
    }


    private static void updateItemCooldownBall(){
        Scenario.updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.BALL.LORE", Scenario.getPlayerData().getLang(), new Lang.Args(Lang.Parameter.VAR, "§a"+Parameter.getNbrBall()))}, 3);
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
        Scenario.updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.TIME_BALL.LORE", Scenario.getPlayerData().getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getTimeBall()))}, 1);
    }

    public static void removeDamage(){
        if(Parameter.getDamage()-1 >= Scenario.damageMin){
            Parameter.setDamage(Parameter.getDamage()-1);
            Scenario.updateItemDamage();
        }
    }

    private static void updateItemDamage(){
        Scenario.updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.DAMAGE_LORE", Scenario.getPlayerData().getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getDamage()))}, 7);
    }

    public static void setBonus(){
        Parameter.setBonus(!Parameter.isBonus());
        if(Parameter.isBonus())  Scenario.updateItem(5, new Item(80).withName(Lang.getMessage("SNO_WAR.SCENARIO.BONUS_LABEL", Scenario.getPlayerData().getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LORE", Scenario.getPlayerData().getLang())}));
    }

    public static void removeSpeedBonus(){
        if(Parameter.getTimeBonus()-1 >= Scenario.speedBonusMin){
            Parameter.setTimeBonus(Parameter.getTimeBonus()-1);
            updateItemSpeedBonus();
        }
    }

    private static void updateItemSpeedBonus(){
        ((MenuBonus) Scenario.menu).updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO_SPEED_BONUS.LORE",  ((MenuBonus) Scenario.menu).getPlayerData().getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getTimeBonus()))}, ((MenuBonus) Scenario.menu).getSize()-6);
    }

    public static void addTime(){
        if(Parameter.getTimeGame()+60 <= Scenario.timeMax){
            Parameter.setTimeGame(Parameter.getTimeGame()+60);
            Scenario.updateItemTime();
        }
    }

    public static void setCoolDown(){
        Parameter.setCoolDownBallState(!Parameter.isCoolDownBallState());
        if(Parameter.isCoolDownBallState())  Scenario.updateItem(8, new Item(349).withData((byte) 3).withName(Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LABEL", Scenario.getPlayerData().getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.COOLDOWN_BALL_LORE", Scenario.getPlayerData().getLang())}));
    }

    public static void removeTime(){
        if(Parameter.getTimeGame()-60 >= Scenario.timeMin){
            Parameter.setTimeGame(Parameter.getTimeGame()-60);
            updateItemTime();
        }
    }

    public static void addHourFunction(){
        if(++Scenario.hour >= 4) Scenario.hour = 0;
        Hour hourVar = Hour.values()[Scenario.hour];
        Bukkit.getWorlds().get(0).setTime(hourVar.getTime());
        Scenario.updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.HOUR.LORE", Scenario.getPlayerData().getLang(), new Lang.Args(Lang.Parameter.VAR, hourVar.getLabel().get(Scenario.getPlayerData().getLang())))}, 2);
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
        if(Parameter.isFall()) Scenario.updateItem((byte) 10,new String[] {Lang.getMessage("SNO_WAR.SCENARIO_ENABLE", Scenario.getPlayerData().getLang())}, 3);
        else Scenario.updateItem((byte) 8,new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", Scenario.getPlayerData().getLang())}, 3);

    }

    public static void setCycle(){
        Parameter.setCycle(!Parameter.isCycle());
        Bukkit.getWorlds().get(0).setGameRuleValue("doDaylightCycle", ""+Parameter.isCycle());
        if(Parameter.isCycle()) Scenario.updateItem((byte) 10,new String[] {Lang.getMessage("SNO_WAR.SCENARIO_ENABLE", Scenario.getPlayerData().getLang())}, 4);
        else Scenario.updateItem((byte) 8,new String[] {Lang.getMessage("SNO_WAR.SCENARIO_DISABLE", Scenario.getPlayerData().getLang())}, 4);
    }


    private static void updateItemTime(){
        Date date = new Date(Parameter.getTimeGame()*1000);
        date.setHours(date.getHours()-1);
        Scenario.updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.TIME_GAME", Scenario.getPlayerData().getLang(), new Lang.Args(Lang.Parameter.TIME, new SimpleDateFormat("HH:mm:ss").format(date)))}, 0);
    }

    private static void updateItemNbrPlayer(){
        Scenario.updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.PLAYER.LORE", Scenario.getPlayerData().getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Data.getNbPlayerMax()))}, 3);
    }

    public static void addLife(){
        if(Parameter.getLife()+1 <= Scenario.lifeMax){
            Parameter.setLife(Parameter.getLife()+1);
        }
        Scenario.updateLife();
    }

    public static void removeLife(){
        if(Parameter.getLife()-1 >= Scenario.lifeMin){
            Parameter.setLife(Parameter.getLife()-1);
        }
        Scenario.updateLife();
    }

    private static void updateLife(){
        Scenario.updateItem(new String[] {Lang.getMessage("SNO_WAR.SCENARIO.LIFE.LORE", Scenario.getPlayerData().getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Parameter.getLife()))}, 5);
    }
}
