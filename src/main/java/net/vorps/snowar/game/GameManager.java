package net.vorps.snowar.game;


import net.vorps.api.lang.Lang;
import net.vorps.api.utils.*;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.Settings;
import net.vorps.snowar.cooldowns.CoolDownSpawnBonus;
import net.vorps.snowar.objects.MapParameter;
import net.vorps.snowar.objects.Parameter;
import net.vorps.snowar.scoreboard.SbFinish;
import net.vorps.snowar.scoreboard.SbGame;
import net.vorps.snowar.utils.LocationFix;
import org.bukkit.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class GameManager {

	public static void startGame(){
        MessageTitle messageTitle = MessageTitle.getMessageTitle("TITLE.START");
        PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> {
            playerData.setScoreboard(new SbGame(playerData));
            playerData.getPlayer().getInventory().clear();
            playerData.getPlayer().getInventory().setItem(0, Item.getItem("shovel", playerData.getLang()).get());
            new Title(messageTitle.getTitle(playerData.getLang()), messageTitle.getSubTitle(playerData.getLang()), playerData.getPlayer());
            playerData.setTabList();
        });
        PlayerData.broadCast("SNO_WAR.STARGAME");
        GameManager.teleport();
        if(Parameter.isBonus()){
            new CoolDownSpawnBonus();
        }
        Info.setInfo(false, true, MapParameter.getName(), true);
	}

    public static void teleport(){
        int elp = 360/PlayerData.getPlayerInGame();
        int angle = 0;
        for(PlayerData playerData : PlayerData.getPlayerDataList().values()){
            playerData.getPlayer().teleport(GameManager.getLocation(angle));
            angle+=elp;
        }
    }

    public static void teleportRandom(Player player){
        player.teleport(GameManager.getLocation(new Random().nextInt(360)));
    }

    private static Location getLocation(int angle){
        return LocationFix.getLocation(new Location(Bukkit.getWorlds().get(0), Math.cos(Math.toRadians(angle))*MapParameter.getDistance()+MapParameter.getSpawnGame().getX(),  MapParameter.getSpawnGame().getY(), Math.sin(Math.toRadians(angle))*MapParameter.getDistance()+MapParameter.getSpawnGame().getZ(), (float) angle <= 90 ? angle+90 : angle-270, (float)0));
    }
	
	public static void stopGame(){
        Hologram.removeAll();
        for(Player player : Bukkit.getOnlinePlayers()){
            player.playSound(player.getLocation(), Sound.EXPLODE, 10, 10);
            if(Settings.isShowStatFinish()){
                PlayerData playerData = PlayerData.getPlayerData(player.getName());
                playerData.setScoreboard(new SbFinish(playerData.getLang()));
            } else player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(MapParameter.getSpawnFinish());
            player.getInventory().clear();
            player.sendMessage(Settings.getTitle()+ Lang.getMessage("SNO_WAR.STOPGAME", PlayerData.getPlayerData(player.getName()).getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Settings.getTimeFinish())));
        }
        Bukkit.getWorlds().get(0).setTime(18000);
        Info.setInfo(false, false, MapParameter.getName(), true);
	}
}
