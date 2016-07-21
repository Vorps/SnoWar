package me.vorps.snowar;

import io.netty.util.Timer;
import lombok.Getter;
import lombok.Setter;
import me.vorps.snowar.cooldowns.CoolDowns;
import me.vorps.snowar.databases.Database;
import me.vorps.snowar.scoreboard.SbLobby;
import me.vorps.snowar.scoreboard.ScoreBoard;
import me.vorps.snowar.threads.Timers;
import me.vorps.snowar.utils.Item;
import me.vorps.snowar.utils.Lang;
import me.vorps.snowar.utils.Location;
import me.vorps.snowar.utils.TabList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
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
    private @Getter int kill;
    private @Getter int life;
    private @Getter @Setter boolean god;
    private @Getter @Setter String playerLastDamage;
    private @Getter @Setter ArrayList<Integer> ball;


    public void addKill(){
        kill++;
        getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5, 2));
    }

    public void addBall(){
        ball.add(Timers.getTime());
        if(ball.size() >= 5){
            if(ball.get(ball.size()-6) <= Timers.getTime()-5){
                new CoolDowns(getPlayer().getName(), 2, "ball");
            }
        }
    }

    public void removeBall(){
        ball.remove(ball.size()-1);
    }

    public void removeLife(){
        life--;
        if(life == 0){
            getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }
    /**
     * Contructor create a data player
     * @param uuid UUID
     */
    public PlayerData(UUID uuid){
        Database.SNOWAR.tryConnectionDatabase();
        this.ball = new ArrayList<>();
        this.uuid = uuid;
        lang = "french";
        name = getPlayer().getName();
        initPlayer();
        initInventory();
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
        TabList.setPlayerList(getPlayer(), Settings.getTabListHeader(), Settings.getTabListFooter());
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

    private void initInventory(){
        getPlayer().getInventory().setItem(8, new Item(Material.BED).withName("§6Quitter").withLore(new String[] {"§7Quitter la partie"}).get());
        if(GameState.isState(GameState.INGAME)){
            getPlayer().getInventory().setItem(0, new Item("Notch").withName("§6Joueurs").withLore(new String[] {"§7Vous téléport a un joueur en jeu"}).get());
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
