package net.vorps.snowar.scenario;

import lombok.Getter;
import lombok.Setter;
import net.vorps.api.Exceptions.SqlException;
import net.vorps.api.databases.Database;
import net.vorps.api.lang.Lang;
import net.vorps.api.menu.Item;
import net.vorps.api.menu.Menu;
import net.vorps.snowar.Data;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.objects.Parameter;
import net.vorps.snowar.utils.Hour;
import net.vorps.snowar.utils.Weather;
import net.vorps.snowar.menu.MenuBonus;
import net.vorps.snowar.menu.MenuScenario;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project RushVolcano Created by Vorps on 27/04/2016 at 16:41.
 */
public class Scenario {

    private PlayerData playerData;
    private @Getter @Setter Menu menu;

    private static @Getter int timeMin;
    private static @Getter int timeMax;
    private static @Getter int nbPlayerMin;
    private static @Getter int nbPlayerMax;
    private static @Getter @Setter int hour;
    private static @Getter int speedBonusMin;
    private static @Getter int speedBonusMax;
    private static @Getter double damageMin;
    private static @Getter double damageMax;
    private static @Getter int coolDownBallMax;
    private static @Getter int ballCoolDownBallMin;
    private static @Getter int ballCoolDownBallMax;
    private static @Getter int  ballTimeMin;
    private static @Getter int  ballTimeMax;
    private static @Getter @Setter int weather;
    private static @Getter int lifeMax;
    private static @Getter int lifeMin;


    public Scenario(PlayerData playerData){
        this.menu = new MenuScenario(playerData);
        this.playerData = playerData;
    }

    public void setItem(int i){
        this.menu.getMenu().getItem(i).setAmount(this.menu.getMenu().getItem(i).getAmount()+1);
    }

    public void updateItem(int index ,Item items){
        this.menu.getMenu().setItem(index, items.get());
        this.playerData.getPlayer().updateInventory();
    }

    public void updateItem(String[] lore, int index){
        ItemStack itemStack = this.menu.getMenu().getItem(index);
        this.menu.getMenu().setItem(index, new Item(itemStack.getType()).withName(itemStack.getItemMeta().getDisplayName()).withLore(lore).get());
        this.playerData.getPlayer().updateInventory();
    }

    public void updateItem(byte data, String[] lore, int index){
        ItemStack itemStack =  this.menu.getMenu().getItem(index);
        this.menu.getMenu().setItem(index, new Item(itemStack.getType()).withData(data).withName(itemStack.getItemMeta().getDisplayName()).withLore(lore).get());
        this.playerData.getPlayer().updateInventory();
    }

    public void closeScenario(){
        this.playerData.getPlayer().closeInventory();
        this.playerData.setScenario(null);
    }

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
        Scenario.hour = Parameter.getHour().ordinal();
        Scenario.weather = Parameter.getWeather().ordinal();
    }
}
