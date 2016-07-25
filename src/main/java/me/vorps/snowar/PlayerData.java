package me.vorps.snowar;

import lombok.Getter;
import lombok.Setter;
import me.vorps.snowar.cooldowns.CoolDownBonus;
import me.vorps.snowar.cooldowns.CoolDowns;
import me.vorps.snowar.cooldowns.CooldownBall;
import me.vorps.snowar.databases.Database;
import me.vorps.snowar.objects.Bonus;
import me.vorps.snowar.objects.Parameter;
import me.vorps.snowar.scoreboard.SbLobby;
import me.vorps.snowar.scoreboard.ScoreBoard;
import me.vorps.snowar.threads.Timers;
import me.vorps.snowar.utils.*;
import me.vorps.snowar.utils.Location;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

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
    private @Getter ArrayList<String> spectator;
    private @Getter @Setter String playerView;
    private @Getter int ballShoot;
    private @Getter int ballTouch;
    private @Getter int bonus;
    private @Getter @Setter ArrayList<CoolDownBonus> bonusData;


    public void addBonus(){
        bonus++;
    }

    public void addKill(){
        kill++;
        scoreboard.updateValue("kill", Lang.getMessage("SNO_WAR.SB.KILL", lang, new Lang.Args(Lang.Parameter.VAR, ""+kill)));
        getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5, 2));
    }

    public static void removePlayerInGame(){
        playerInGame--;
    }

    public void addBall(){
        ball.add(Timers.getTime());
        if(ball.size() >= Parameter.getNbrBall()){
            if(ball.get(ball.size()-Parameter.getNbrBall()) <= Timers.getTime()+Parameter.getTimeBall()){
                new CoolDowns(getPlayer().getName(), Parameter.getCooldownBall(), "ball");
                new CooldownBall(getPlayer()).start();
            }
        }
    }

    public void addBallTouch(){
        ballTouch++;
        getPlayer().playSound(getPlayer().getLocation(), Sound.ARROW_HIT, 10, 10);
    }
    public void removeBall(){
        ballShoot++;
        if(ball.size() > 0){
            ball.remove(ball.size()-1);
        }
    }


    public void removeLife(String killer){
        scoreboard.updateValue("life", Lang.getMessage("SNO_WAR.SB.LIFE", lang, new Lang.Args(Lang.Parameter.VAR, ""+--life)));
        setTabList();
        if(life == 0){
            initInventory();
            getPlayer().setGameMode(GameMode.SPECTATOR);
            getPlayer().sendMessage(Settings.getTitle()+Lang.getMessageTmp("SNO_WAR.DEATH", lang, new Lang.Args(Lang.Parameter.KILLER, killer)));
            PlayerData.broadCast("SNO_WAR.DEATH.BROADCAST", new Lang.Args(Lang.Parameter.KILLER, killer), new Lang.Args(Lang.Parameter.PLAYER, getPlayer().getName()), new Lang.Args(Lang.Parameter.VAR, ""+--playerInGame));
            PlayerData.getPlayerData(killer).getPlayer().sendMessage(Settings.getTitle()+Lang.getMessageTmp("SNO_WAR.KILL", PlayerData.getPlayerData(killer).getLang(), new Lang.Args(Lang.Parameter.PLAYER, getPlayer().getName())));
            if(playerInGame == 1){
                Victory.onVictory(1);
            }
        } else {
            GameManager.teleportRandom(getPlayer());
            if(life == 1){
                getPlayer().sendMessage(Settings.getTitle()+Lang.getMessage("SNO_WAR.DEATH.1", lang, new Lang.Args(Lang.Parameter.KILLER, killer)));
            } else {
                getPlayer().sendMessage(Settings.getTitle()+Lang.getMessage("SNO_WAR.DEATH.2", lang, new Lang.Args(Lang.Parameter.KILLER, killer), new Lang.Args(Lang.Parameter.LIFE, ""+life)));
            }
            getPlayer().sendMessage(Settings.getTitle()+Lang.getMessage("SNO_WAR.KILL.1", lang, new Lang.Args(Lang.Parameter.KILLED, getPlayer().getName())));
        }
    }

    public void setTabList(){
        getPlayer().setPlayerListName(Data.getColors()[life/Data.getColors().length]+getPlayer().getName()+" §c"+life);
    }

    /**
     * Contructor create a data player
     * @param uuid UUID
     */
    public PlayerData(UUID uuid){
        Database.SNOWAR.tryConnectionDatabase();
        this.bonusData = new ArrayList<>();
        this.ball = new ArrayList<>();
        this.uuid = uuid;
        this.life = Data.getLife();
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
        spectator = new ArrayList<>();
        TabList.setPlayerList(getPlayer(), Settings.getTabListHeader(), Settings.getTabListFooter());
        getPlayer().getInventory().clear();
        getPlayer().getInventory().setArmorContents(null);
        getPlayer().setLevel(0);
        getPlayer().setFoodLevel(20);
        getPlayer().setHealth(20);
        if(GameState.isState(GameState.WAITING) || GameState.isState(GameState.INSTART)){
            getPlayer().setFoodLevel(20);
            getPlayer().setHealth(20);
            getPlayer().teleport(Location.getLocation("map_01_lobby"));
            getPlayer().setGameMode(GameMode.ADVENTURE);
            setScoreboard(new SbLobby(lang));
            playerInGame++;
        } else {
            life = 0;
            getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }

    private void initInventory(){
        getPlayer().getInventory().setItem(8, new me.vorps.snowar.menu.Item(Material.BED).withName(Lang.getMessage("SNO_WAR.ITEM.QUIT.LABEL", lang)).withLore(new String[] {Lang.getMessage("SNO_WAR.ITEM.QUIT.LORE", lang)}).get());
        if(GameState.isState(GameState.INGAME)){
            getPlayer().getInventory().setItem(0, new me.vorps.snowar.menu.Item("Notch").withName(Lang.getMessage("SNO_WAR.ITEM.SPECT.LABEL", lang)).withLore(new String[] {Lang.getMessage("SNO_WAR.ITEM.SPECT.LORE", lang)}).get());
        }
    }

    public void removePlayerData(){
        playerDataList.remove(name);
    }
    public void setScoreboard(ScoreBoard scoreboard){
        this.scoreboard = scoreboard;
        getPlayer().setScoreboard(this.scoreboard.getScoreBoard());
    }

    public String toString(){
        return Data.getColors()[life/Data.getColors().length]+getPlayer().getName();
    }

    private static @Getter HashMap<String, PlayerData> playerDataList;
    private static TreeMap<String, PlayerData> playerDataTrieLife;
    private static TreeMap<String, PlayerData> playerDataTrieKill;
    private static TreeMap<String, PlayerData> playerDataTrieBonus;
    private static TreeMap<String, PlayerData> playerDataTrieBall;
    private static @Getter int playerInGame;

    static {
        playerDataList = new HashMap<>();
        playerDataTrieLife = new TreeMap<>(new PlayerDataComparatorLife(playerDataList));
        playerDataTrieKill = new TreeMap<>(new PlayerDataComparatorKill(playerDataList));
        playerDataTrieBonus = new TreeMap<>(new PlayerDataComparatorBonus(playerDataList));
        playerDataTrieBall = new TreeMap<>(new PlayerDataComparatorBall(playerDataList));
    }

    public static TreeMap<String,PlayerData> triePlayerDataLife(){
        playerDataTrieLife.clear();
        playerDataTrieLife.putAll(playerDataList);
        return playerDataTrieLife;
    }

    public static TreeMap<String,PlayerData> triePlayerDataKills(){
        playerDataTrieKill.clear();
        playerDataTrieKill.putAll(playerDataList);
        return playerDataTrieKill;
    }

    public static TreeMap<String,PlayerData> triePlayerDataBonus(){
        playerDataTrieBonus.clear();
        playerDataTrieBonus.putAll(playerDataList);
        return playerDataTrieBonus;
    }

    public static TreeMap<String,PlayerData> triePlayerDataBall(){
        playerDataTrieBall.clear();
        playerDataTrieBall.putAll(playerDataList);
        return playerDataTrieBall;
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


    private static class PlayerDataComparatorLife implements Comparator<String> {
        private Map<String, PlayerData> base;
        private PlayerDataComparatorLife(Map<String, PlayerData> base) {
            this.base = base;
        }

        public int compare(String a, String b) {
            if (base.get(a).life < base.get(b).life) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private static class PlayerDataComparatorKill implements Comparator<String> {
        private Map<String, PlayerData> base;
        private PlayerDataComparatorKill(Map<String, PlayerData> base) {
            this.base = base;
        }

        public int compare(String a, String b) {
            if (base.get(a).kill < base.get(b).kill) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private static class PlayerDataComparatorBonus implements Comparator<String> {
        private Map<String, PlayerData> base;
        private PlayerDataComparatorBonus(Map<String, PlayerData> base) {
            this.base = base;
        }

        public int compare(String a, String b) {
            if (base.get(a).bonus < base.get(b).bonus) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private static class PlayerDataComparatorBall implements Comparator<String> {
        private Map<String, PlayerData> base;
        private PlayerDataComparatorBall(Map<String, PlayerData> base) {
            this.base = base;
        }

        public int compare(String a, String b) {
            if (base.get(a).ballTouch < base.get(b).ballTouch) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
