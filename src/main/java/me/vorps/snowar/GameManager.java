package me.vorps.snowar;


import me.vorps.snowar.scoreboard.SbGame;
import me.vorps.snowar.utils.Item;
import me.vorps.snowar.utils.Title;

public class GameManager {

	public static void startGame(){
        PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> {
            playerData.setScoreboard(new SbGame(playerData));
            playerData.getPlayer().getInventory().clear();
            playerData.getPlayer().getInventory().setItem(0, new Item(256).withName("§6Recharger").get());
        });
        new Title("§fSno§cWar", "§aBonne chance").broadcast();
	}
	
	public static void stopGame(){
	}
}
