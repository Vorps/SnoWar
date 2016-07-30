package me.vorps.snowar.game;


import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.snowar.cooldowns.CoolDownSpawnBonus;
import me.vorps.snowar.objects.MapParameter;
import me.vorps.snowar.objects.Parameter;
import me.vorps.snowar.scoreboard.SbFinish;
import me.vorps.snowar.scoreboard.SbGame;
import me.vorps.snowar.lang.Lang;
import me.vorps.snowar.utils.MessageTitle;
import me.vorps.snowar.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
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
            playerData.getPlayer().getInventory().setItem(0, me.vorps.snowar.utils.Item.getItem("shovel", playerData.getLang()).get());
            new Title(messageTitle.getTitle().get(playerData.getLang()), messageTitle.getSubTitle().get(playerData.getLang())).send(playerData.getPlayer());
            playerData.setTabList();
        });
        PlayerData.broadCast("SNO_WAR.STARGAME");
        GameManager.teleport();
        if(Parameter.isBonus()){
            new CoolDownSpawnBonus();
        }
	}

    public static void teleport(){
        int elp = 360/PlayerData.getPlayerInGame();
        int var = 0;
        for(PlayerData playerData : PlayerData.getPlayerDataList().values()){
            System.out.println(var);
            System.out.println(Math.cos(Math.toRadians(var)));
            System.out.println(Math.sin(Math.toRadians(var)));
            playerData.getPlayer().teleport(new Location(Bukkit.getWorlds().get(0), Math.cos(Math.toRadians(var))*MapParameter.getDistance()+MapParameter.getSpawnGame().getX(),  MapParameter.getSpawnGame().getY(), Math.sin(Math.toRadians(var))*MapParameter.getDistance()+MapParameter.getSpawnGame().getZ(), (float) var <= 90 ? var+90 : var-270, (float)0));
            var+=elp;
        }

    }

    public static void teleportRandom(Player player){
        int var = new Random().nextInt(360);
        player.teleport(new Location(Bukkit.getWorlds().get(0), Math.cos(Math.toRadians(var))*MapParameter.getDistance()+MapParameter.getSpawnGame().getX(),  MapParameter.getSpawnGame().getY(), Math.sin(Math.toRadians(var))*MapParameter.getDistance()+MapParameter.getSpawnGame().getZ(), (float) var <= 90 ? var+90 : var-270, (float)0));
    }
	
	public static void stopGame(){
        for(Player player : Bukkit.getOnlinePlayers()){
            player.playSound(player.getLocation(), Sound.EXPLODE, 10, 10);
            if(Settings.isShowStatFinish()){
                PlayerData playerData = PlayerData.getPlayerData(player.getName());
                playerData.setScoreboard(new SbFinish(playerData.getLang()));
            } else {
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(MapParameter.getSpawnFinish());
            player.getInventory().clear();
            player.sendMessage(Settings.getTitle()+Lang.getMessage("SNO_WAR.STOPGAME", PlayerData.getPlayerData(player.getName()).getLang(), new Lang.Args(Lang.Parameter.VAR, ""+Settings.getTimeFinish())));
        }
        Bukkit.getWorlds().get(0).setTime(18000);
	}
}
