package me.vorps.snowar;

import lombok.Getter;
import lombok.Setter;
import me.vorps.snowar.cooldowns.CoolDowns;
import me.vorps.snowar.cooldowns.CooldownBall;
import me.vorps.snowar.databases.Database;
import me.vorps.snowar.game.GameManager;
import me.vorps.snowar.game.GameState;
import me.vorps.snowar.game.Victory;
import me.vorps.snowar.lang.Lang;
import me.vorps.snowar.menu.*;
import me.vorps.snowar.objects.Parameter;
import me.vorps.snowar.scoreboard.SbLobby;
import me.vorps.snowar.scoreboard.SbSpectator;
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
    private @Getter @Setter String playerView;
    private @Getter int ballShoot;
    private @Getter int ballTouch;
    private @Getter int bonus;
    private @Getter ArrayList<String> spectator;
    private @Getter @Setter HashMap<me.vorps.snowar.bonus.Bonus, Integer> bonusData;

    /**
     * Contructor create a data player
     * @param uuid UUID
     */
    public PlayerData(final UUID uuid){
        Database.SNOWAR.tryConnectionDatabase();
        this.bonusData = new HashMap<>();
        this.ball = new ArrayList<>();
        this.lang = "french"; // TODO: 26/07/2016 Lang charged 
        this.uuid = uuid;
        this.life = Parameter.getLife();
        this.name = getPlayer().getName();
        this.initPlayer();
        this.initInventory();
        PlayerData.playerDataList.put(this.name, this);
    }
    
    public void addBonus(){
        this.bonus++;
    }

    public void addKill(){
        this.kill++;
        this.scoreboard.updateValue("kill", Lang.getMessage("SNO_WAR.SB.KILL", this.lang, new Lang.Args(Lang.Parameter.VAR, ""+this.kill)));
        this.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5, 2));
    }

    public void resetLife(){
        this.life = 0;
    }
    
    public static void removePlayerInGame(){
        PlayerData.playerInGame--;
        if(!GameState.isState(GameState.FINISH)){
            PlayerData.getPlayerDataList().values().forEach((PlayerData playerDataList) -> playerDataList.getScoreboard().updateValue("player", Lang.getMessage("SNO_WAR.SB.PLAYER", playerDataList.lang, new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+PlayerData.playerInGame))));
        }
    }

    public void addBall(){
        this.ball.add(Timers.getTime());
        if(this.ball.size() >= Parameter.getNbrBall()){
            if(this.ball.get(this.ball.size()-Parameter.getNbrBall()) <= Timers.getTime()+Parameter.getTimeBall() && Parameter.getCooldownBall() > 0){
                new CoolDowns(getPlayer().getName(), Parameter.getCooldownBall(), "ball");
                new CooldownBall(getPlayer()).start();
            }
        }
    }

    public void addBallTouch(){
        this.ballTouch++;
        this.getPlayer().playSound(getPlayer().getLocation(), Sound.ARROW_HIT, 10, 10);
    }

    public void removeBall(){
        this.ballShoot++;
        if(this.ball.size() > 0){
            this.ball.remove(this.ball.size()-1);
        }
    }

    /**
     * Remove 1 life if life == 0 && playerInGame == 1 then finish
     * @param killer String
     */
    public void removeLife(final String killer){
        this.scoreboard.updateValue("life", Lang.getMessage("SNO_WAR.SB.LIFE", this.lang, new Lang.Args(Lang.Parameter.VAR, ""+--this.life)));
        this.setTabList();
        this.getPlayer().setHealth(20);
        if(this.life == 0){
            PlayerData.removePlayerInGame();
            this.getPlayer().sendMessage(Settings.getTitle()+Lang.getMessageTmp("SNO_WAR.DEATH", this.lang, new Lang.Args(Lang.Parameter.KILLER, killer)));
            PlayerData.broadCast("SNO_WAR.DEATH.BROADCAST", new Lang.Args(Lang.Parameter.KILLER, killer), new Lang.Args(Lang.Parameter.PLAYER, this.getPlayer().getName()), new Lang.Args(Lang.Parameter.VAR, ""+PlayerData.playerInGame));
            PlayerData.getPlayerData(killer).getPlayer().sendMessage(Settings.getTitle()+Lang.getMessageTmp("SNO_WAR.KILL", PlayerData.getPlayerData(killer).getLang(), new Lang.Args(Lang.Parameter.PLAYER, this.getPlayer().getName())));
            this.initPlayer();
            this.initInventory();
            if(PlayerData.playerInGame == 1){
                Victory.onVictory(1);
            }
        } else {
            GameManager.teleportRandom(this.getPlayer());
            if(this.life == 1){
                this.getPlayer().sendMessage(Settings.getTitle()+Lang.getMessage("SNO_WAR.DEATH.1", this.lang, new Lang.Args(Lang.Parameter.KILLER, killer)));
            } else {
                this.getPlayer().sendMessage(Settings.getTitle()+Lang.getMessage("SNO_WAR.DEATH.2", this.lang, new Lang.Args(Lang.Parameter.KILLER, killer), new Lang.Args(Lang.Parameter.LIFE, ""+this.life)));
            }
            this.getPlayer().sendMessage(Settings.getTitle()+Lang.getMessage("SNO_WAR.KILL.1", this.lang, new Lang.Args(Lang.Parameter.KILLED, this.name)));
        }
    }

    /**
     * Set tab list player color function life
     */
    public void setTabList(){
        this.getPlayer().setPlayerListName(Data.getCOLOR()[this.life/Data.getCOLOR().length]+this.name+" §c"+this.life);
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
        this.spectator = new ArrayList<>();
        TabList.setPlayerList(this.getPlayer(), Settings.getTabListHeader(), Settings.getTabListFooter());
        this.getPlayer().getInventory().clear();
        this.getPlayer().getInventory().setArmorContents(null);
        this.getPlayer().setLevel(0);
        this.getPlayer().setFoodLevel(20);
        this.getPlayer().setHealth(20);
        if(GameState.isState(GameState.WAITING) || GameState.isState(GameState.INSTART)){
            this.getPlayer().setFoodLevel(20);
            this.getPlayer().setHealth(20);
            this.getPlayer().teleport(Location.getLocation("map_01_lobby"));
            this.getPlayer().setGameMode(GameMode.ADVENTURE);
            this.setScoreboard(new SbLobby(this.lang));
            PlayerData.playerInGame++;
        } else {
            this.getPlayer().sendMessage(Lang.getMessage("SNO_WAR.JOIN.SPECTATOR_1", this.lang));
            this.getPlayer().sendMessage(Lang.getMessage("SNO_WAR.JOIN.SPECTATOR_2", this.lang));
            this.resetLife();
            this.getPlayer().setGameMode(GameMode.SPECTATOR);
            this.setScoreboard(new SbSpectator(this.lang));
            this.getPlayer().setPlayerListName("§7"+getPlayer().getName());
        }
    }

    /**
     * Init inventory player
     */
    private void initInventory(){
        if(GameState.isState(GameState.WAITING) || GameState.isState(GameState.INSTART)){
            if(getPlayer().hasPermission("rushvolcano.scenario") && Data.isScenario()) {
                this.getPlayer().getInventory().setItem(0, new me.vorps.snowar.menu.Item(Material.REDSTONE_COMPARATOR).withName(Lang.getMessage("SNO_WAR.INVENTORY.LOBBy.SCENARISE", lang)).get());
            }
        }
        this.getPlayer().getInventory().setItem(8, new me.vorps.snowar.menu.Item(Material.BED).withName(Lang.getMessage("SNO_WAR.ITEM.QUIT.LABEL", this.lang)).withLore(new String[] {Lang.getMessage("SNO_WAR.ITEM.QUIT.LORE", this.lang)}).get());
        if(GameState.isState(GameState.INGAME)){
            this.getPlayer().getInventory().setItem(0, new me.vorps.snowar.menu.Item("Notch").withName(Lang.getMessage("SNO_WAR.ITEM.SPECT.LABEL", this.lang)).withLore(new String[] {Lang.getMessage("SNO_WAR.ITEM.SPECT.LORE", this.lang)}).get());
        }
    }

    public void removePlayerData(){
        PlayerData.playerDataList.remove(this.name);
    }

    public void setScoreboard(final ScoreBoard scoreboard){
        this.scoreboard = scoreboard;
        this.getPlayer().setScoreboard(this.scoreboard.getScoreBoard());
    }

    @Override
    public String toString(){
        return Data.getCOLOR()[this.life/Data.getCOLOR().length]+this.name;
    }

    private static @Getter HashMap<String, PlayerData> playerDataList;
    private static TreeMap<String, PlayerData> playerDataTrieLife;
    private static TreeMap<String, PlayerData> playerDataTrieKill;
    private static TreeMap<String, PlayerData> playerDataTrieBonus;
    private static TreeMap<String, PlayerData> playerDataTrieBall;
    private static @Getter int playerInGame;

    static {
        PlayerData.playerDataList = new HashMap<>();
        PlayerData.playerDataTrieLife = new TreeMap<>(new PlayerDataComparatorLife(PlayerData.playerDataList));
        PlayerData.playerDataTrieKill = new TreeMap<>(new PlayerDataComparatorKill(PlayerData.playerDataList));
        PlayerData.playerDataTrieBonus = new TreeMap<>(new PlayerDataComparatorBonus(PlayerData.playerDataList));
        PlayerData.playerDataTrieBall = new TreeMap<>(new PlayerDataComparatorBall(PlayerData.playerDataList));
    }

    /**
     * Trie PlayerData life
     * @return TreeMap<StringPlayerData>
     */
    public static TreeMap<String,PlayerData> triePlayerDataLife(){
        PlayerData.playerDataTrieLife.clear();
        PlayerData.playerDataTrieLife.putAll(PlayerData.playerDataList);
        return PlayerData.playerDataTrieLife;
    }

    /**
     * Trie PlayerData kill
     * @return TreeMap<StringPlayerData>
     */
    public static TreeMap<String,PlayerData> triePlayerDataKills(){
        PlayerData.playerDataTrieKill.clear();
        PlayerData.playerDataTrieKill.putAll(PlayerData.playerDataList);
        return PlayerData.playerDataTrieKill;
    }

    /**
     * Trie PlayerData bonus
     * @return TreeMap<StringPlayerData>
     */
    public static TreeMap<String,PlayerData> triePlayerDataBonus(){
        PlayerData.playerDataTrieBonus.clear();
        PlayerData.playerDataTrieBonus.putAll(PlayerData.playerDataList);
        return PlayerData.playerDataTrieBonus;
    }

    /**
     * Trie PlayerData ball
     * @return TreeMap<StringPlayerData>
     */
    public static TreeMap<String,PlayerData> triePlayerDataBall(){
        PlayerData.playerDataTrieBall.clear();
        PlayerData.playerDataTrieBall.putAll(PlayerData.playerDataList);
        return PlayerData.playerDataTrieBall;
    }

    /**
     * Get PlayerData
     * @param namePlayer String
     * @return PlayerData
     */
    public static PlayerData getPlayerData(final String namePlayer){
        return PlayerData.playerDataList.get(namePlayer);
    }

    public static void clear(){
        PlayerData.playerDataList.clear();
    }

    /**
     * Send message all playerData (Lang)
     * @param key String
     * @param args Lang.Args
     */
    public static void broadCast(final String key, final Lang.Args... args){
        PlayerData.playerDataList.values().forEach((PlayerData playerData) -> playerData.getPlayer().sendMessage(Settings.getTitle()+Lang.getMessage(key, playerData.lang, args)));
    }


    /**
     * Trie player Life  First.life > Last.life
     */
    private static class PlayerDataComparatorLife implements Comparator<String> {
        private Map<String, PlayerData> base;
        private PlayerDataComparatorLife(Map<String, PlayerData> base) {
            this.base = base;
        }

        public int compare(String a, String b) {
            if (this.base.get(a).life < this.base.get(b).life) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    /**
     * Trie player Kill  First.kill > Last.kill
     */
    private static class PlayerDataComparatorKill implements Comparator<String> {
        private Map<String, PlayerData> base;
        private PlayerDataComparatorKill(Map<String, PlayerData> base) {
            this.base = base;
        }

        public int compare(String a, String b) {
            if (this.base.get(a).kill < this.base.get(b).kill) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    /**
     * Trie player Bonus  First.bonus > Last.bonus
     */
    private static class PlayerDataComparatorBonus implements Comparator<String> {
        private Map<String, PlayerData> base;
        private PlayerDataComparatorBonus(Map<String, PlayerData> base) {
            this.base = base;
        }

        public int compare(String a, String b) {
            if (this.base.get(a).bonus < this.base.get(b).bonus) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    /**
     * Trie player Ball  First.ball > Last.ball
     */
    private static class PlayerDataComparatorBall implements Comparator<String> {
        private Map<String, PlayerData> base;
        private PlayerDataComparatorBall(Map<String, PlayerData> base) {
            this.base = base;
        }

        public int compare(String a, String b) {
            if (this.base.get(a).ballTouch < this.base.get(b).ballTouch) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
