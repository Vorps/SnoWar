package me.vorps.snowar;

import lombok.Getter;
import me.vorps.snowar.databases.Database;
import me.vorps.snowar.scoreboard.SbLobby;
import me.vorps.snowar.scoreboard.ScoreBoard;
import me.vorps.snowar.utils.Lang;
import me.vorps.snowar.utils.Location;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.UUID;

/**
 * Project SnoWar Created by Vorps on 19/07/2016 at 22:57.
 */
public class PlayerData {

    private UUID uuid;
    private String name;
    private @Getter String lang;
    private @Getter ScoreBoard scoreboard;

    /**
     * Contructor create a data player
     * @param uuid UUID
     */
    public PlayerData(UUID uuid){
        Database.SNOWAR.tryConnectionDatabase();
        this.uuid = uuid;
        lang = "french";
        name = getPlayer().getName();
        initPlayer();
        playerDataList.put(name, this);
    }

    /**
     * @return Player
     */
    public Player getPlayer(){
        return Bukkit.getPlayer(uuid);
    }

    /**
     * Initialize player
     */
    private void initPlayer(){
        getPlayer().getInventory().clear();
        getPlayer().getInventory().setArmorContents(null);
        if(GameState.isState(GameState.WAITING) || GameState.isState(GameState.INSTART)){
            getPlayer().setFoodLevel(20);
            getPlayer().setHealth(20);
            getPlayer().teleport(Location.getLocation("map_01_lobby"));
            getPlayer().setGameMode(GameMode.ADVENTURE);
            setScoreboard(new SbLobby(lang));
        } else {
            getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }

    public void removePlayerData(){
        playerDataList.remove(name);
    }
    public void setScoreboard(ScoreBoard scoreboard){
        this.scoreboard = scoreboard;
        getPlayer().setScoreboard(this.scoreboard.getScoreBoard());
    }

    private static @Getter HashMap<String, PlayerData> playerDataList;

    static {
        playerDataList = new HashMap<>();
    }

    /**
     * Get PlayerData
     * @param namePlayer String
     * @return PlayerData
     */
    public static PlayerData getPlayerData(String namePlayer){
        return playerDataList.get(namePlayer);
    }

    /**
     * Get current number players
     * @return int
     */
    public static int size(){
        return playerDataList.size();
    }

    public static void clear(){
        playerDataList.clear();
    }

    public static void broadCast(String key, Lang.Args... args){
        playerDataList.values().forEach((PlayerData playerData) -> playerData.getPlayer().sendMessage(Settings.getTitle()+Lang.getMessage(key, playerData.lang, args)));
    }
}
