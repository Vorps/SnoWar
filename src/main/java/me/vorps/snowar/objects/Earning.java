package me.vorps.snowar.objects;

import lombok.Getter;
import me.vorps.snowar.Exceptions.SqlException;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.databases.Database;
import me.vorps.snowar.lang.Lang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project RushVolcano Created by Vorps on 26/04/2016 at 20:53.
 */
public class Earning {

    private @Getter double kill;
    private @Getter String killDevice;
    private @Getter double ball;
    private @Getter String ballDevice;
    private @Getter double victory;
    private @Getter String victoryDevice;

    /**
     * Init Earning parameter
     * @param name String
     * @throws SqlException
     */
    public Earning(String name) throws SqlException{
        ResultSet result = Database.SNOWAR.getDatabase().getData("earning", "e_name = '"+name+"'");
        try {
            result.next();
        } catch (SQLException e){
            //
        }
        this.kill = Database.SNOWAR.getDatabase().getDouble(result, 2);
        this.killDevice = Database.SNOWAR.getDatabase().getString(result, 3);
        this.ball = Database.SNOWAR.getDatabase().getDouble(result, 4);
        this.ballDevice = Database.SNOWAR.getDatabase().getString(result, 5);
        this.victory = Database.SNOWAR.getDatabase().getDouble(result, 6);
        this.victoryDevice = Database.SNOWAR.getDatabase().getString(result, 7);
    }

    /**
     * Calcul earning player
     * @param name String
     * @param kill int
     * @param ball int
     * @param victory boolean
     */
    public void earning(String name, int kill, int ball, boolean victory){
        HashMap<String, Double> earning = new HashMap<>();
        earning.put(this.killDevice, kill * this.kill);
        earning.put(this.ballDevice, ball * this.ball);
        ArrayList<String> notification = new ArrayList<>();
        if(victory){
            earning.put(this.victoryDevice, this.victory);
        }
        for(String device : earning.keySet()){
            // TODO: 22/07/2016 Earning earning.get(device)
            if(device.substring(0, 1).equalsIgnoreCase("e")){
                notification.add(Lang.getMessage("SNO_WAR.EARNING.1", PlayerData.getPlayerData(name).getLang(), new Lang.Args(Lang.Parameter.DEVICE, device), new Lang.Args(Lang.Parameter.VAR, ""+earning.get(device))));
            } else {
                notification.add(Lang.getMessage("SNO_WAR.EARNING.2", PlayerData.getPlayerData(name).getLang(), new Lang.Args(Lang.Parameter.DEVICE, device), new Lang.Args(Lang.Parameter.VAR, ""+earning.get(device))));
            }
        }
        try {
            for(String notif : notification){
                Database.SNOWAR.getDatabase().insertTable("notification",  name, notif, "GAME");
            }
        } catch (SqlException e){
            e.printStackTrace();
        }
    }
}
