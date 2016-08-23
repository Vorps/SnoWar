package me.vorps.snowar.objects;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.cooldowns.CoolDownBonus;
import me.vorps.syluriapi.Exceptions.PercentException;
import me.vorps.syluriapi.Exceptions.SqlException;
import me.vorps.syluriapi.databases.Database;
import me.vorps.syluriapi.lang.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

import java.sql.ResultSet;
import java.util.Random;

/**
 * Project SnoWar Created by Vorps on 23/07/2016 at 21:02.
 */
public class Bonus {

    /**
     * Load Bonus
     * @param resultSet ResultSet
     * @throws SqlException
     */
    public Bonus(ResultSet resultSet) throws SqlException{
        try {
            java.lang.reflect.Constructor constructor = Class.forName ("me.vorps.snowar.bonus."+Database.SNOWAR.getDatabase().getString(resultSet, 1)).getConstructor (int.class, double.class, String.class, String.class, boolean.class, String.class, String.class, String.class);
            constructor.newInstance (Database.SNOWAR.getDatabase().getInt(resultSet, 2), Database.SNOWAR.getDatabase().getDouble(resultSet, 3), Database.SNOWAR.getDatabase().getString(resultSet, 4), Database.SNOWAR.getDatabase().getString(resultSet, 5), Database.SNOWAR.getDatabase().getBoolean(resultSet, 6), Database.SNOWAR.getDatabase().getString(resultSet, 7), Database.SNOWAR.getDatabase().getString(resultSet, 8), Database.SNOWAR.getDatabase().getString(resultSet, 9));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Init bonus
     * @throws PercentException if not 100%
     */
    public static void init() throws PercentException{
        double percent = 0;
        for(me.vorps.snowar.bonus.Bonus bonus : me.vorps.snowar.bonus.Bonus.getBonusList()){
            percent+= bonus.getPercent();
        }
        if(percent != 100) throw new PercentException("Error ! Total percent bonus not 100 %", new Throwable("Error ! System bonus prevaricate"));
    }

    /**
     * Give Bonus Select Bonus percent
     * @param playerData PlayerData
     */
    public static  me.vorps.snowar.bonus.Bonus give(PlayerData playerData){
        me.vorps.snowar.bonus.Bonus bonus = null;
        playerData.getPlayer().playSound(playerData.getPlayer().getLocation(), Sound.FIZZ, 10, 10);
        int luck = new Random().nextInt(100);
        int var = 0;
        for(me.vorps.snowar.bonus.Bonus bonusList : me.vorps.snowar.bonus.Bonus.getBonusList()){
            var+= bonusList.getPercent();
            if(var >= luck) {
                Bonus.giveBonus(bonusList, playerData);
                bonus = bonusList;
                break;
            }
        }
        return bonus;
    }

    /**
     * Give Bonus Player
     * @param bonus me.vorps.snowar.bonus.Bonus
     * @param playerData PlayerData
     */
    private static void giveBonus(me.vorps.snowar.bonus.Bonus bonus, PlayerData playerData){
        me.vorps.snowar.bonus.Bonus bonus1 = null;
        for(me.vorps.snowar.bonus.Bonus bonusList :  playerData.getBonusData().keySet()){
            if(!bonusList.isPersistence()){
                bonusList.onDisable(playerData);
                Bukkit.getScheduler().cancelTask(playerData.getBonusData().get(bonusList));
                playerData.getPlayer().sendMessage(Lang.getMessage(bonus.getDisable(), playerData.getLang()));
                bonus1 = bonusList;
            }
        }
        if(bonus1 != null){
            playerData.getBonusData().remove(bonus1);
        }
        if(bonus.getTime() > 0){
            playerData.getBonusData().put(bonus, new CoolDownBonus(bonus, playerData).getTask());
        } else if(bonus.isPersistence()){
            playerData.getBonusData().put(bonus, 0);
        }
        playerData.addBonus();
        playerData.getPlayer().sendMessage(Lang.getMessage(bonus.getEnable(), playerData.getLang()));
        bonus.onEnable(playerData);
    }
}
