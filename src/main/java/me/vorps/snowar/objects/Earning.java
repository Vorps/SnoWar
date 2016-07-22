package me.vorps.snowar.objects;

import lombok.Getter;
import me.vorps.snowar.Exceptions.SqlException;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.databases.Database;
import me.vorps.snowar.utils.Lang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project RushVolcano Created by Vorps on 26/04/2016 at 20:53.
 */
public class Earning {

    private static @Getter Earning earning;

    private @Getter double kill;
    private @Getter String killDevice;
    private @Getter double ball;
    private @Getter String ballDevice;
    private @Getter double victory;
    private @Getter String victoryDevice;

    public Earning(String name) throws SqlException{
        ResultSet result = Database.SNOWAR.getDatabase().getData("earning", "e_name = '"+name+"'");
        try {
            result.next();
        } catch (SQLException e){
            //
        }
        kill = Database.SNOWAR.getDatabase().getDouble(result, 2);
        killDevice = Database.SNOWAR.getDatabase().getString(result, 3);
        ball = Database.SNOWAR.getDatabase().getDouble(result, 4);
        ballDevice = Database.SNOWAR.getDatabase().getString(result, 5);
        victory = Database.SNOWAR.getDatabase().getDouble(result, 8);
        victoryDevice = Database.SNOWAR.getDatabase().getString(result, 9);
        earning = this;
    }

    public void earning(String name, int kill, int ball, boolean victory){
        HashMap<String, Double> earning = new HashMap<>();
        earning.put(killDevice, kill*this.kill);
        earning.put(ballDevice, ball * this.ball);
        ArrayList<String> notification = new ArrayList<>();
        if(victory){
            earning.put(victoryDevice, this.victory);
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
