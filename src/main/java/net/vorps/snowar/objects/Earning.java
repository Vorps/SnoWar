package net.vorps.snowar.objects;

import lombok.Getter;
import net.vorps.api.Exceptions.SqlException;
import net.vorps.api.databases.Database;
import net.vorps.api.players.PlayerData;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        if(victory) earning.put(this.victoryDevice, this.victory);
        for(String device : earning.keySet()) PlayerData.addMoney(Bukkit.getPlayer(name).getUniqueId(), device, earning.get(device));
    }
}
