package me.vorps.snowar;


import me.vorps.snowar.scoreboard.SbGame;
import me.vorps.snowar.utils.MessageTitle;
import me.vorps.snowar.utils.Title;

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
	}
}
