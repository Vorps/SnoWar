package me.vorps.snowar;


import me.vorps.snowar.objects.MapParameter;
import me.vorps.snowar.scoreboard.SbGame;
import me.vorps.snowar.utils.Lang;
import me.vorps.snowar.utils.MessageTitle;
import me.vorps.snowar.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

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
