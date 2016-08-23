package me.vorps.snowar.listeners;

import me.vorps.snowar.game.GameState;
import me.vorps.snowar.PlayerData;
import me.vorps.syluriapi.lang.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener{

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        if(GameState.isState(GameState.WAITING) || GameState.isState(GameState.INSTART)){
            PlayerData.broadCast("SNO_WAR.CHAT.GAME", new Lang.Args(Lang.Parameter.PLAYER, ""+PlayerData.getPlayerData(player.getName())), new Lang.Args(Lang.Parameter.MESSAGE, e.getMessage()));
        } else {
            if(PlayerData.getPlayerData(player.getName()).getLife() == 0){
                PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> {
                    if(playerData.getLife() == 0) playerData.getPlayer().sendMessage(Lang.getMessage("SNO_WAR.CHAT.GAME", playerData.getLang(), new Lang.Args(Lang.Parameter.PLAYER, ""+PlayerData.getPlayerData(player.getName())), new Lang.Args(Lang.Parameter.MESSAGE, e.getMessage())));
                });
            } else {
                if(e.getMessage().startsWith("!")){
                    PlayerData.broadCast("SNO_WAR.CHAT.GLOBAL", new Lang.Args(Lang.Parameter.PLAYER, ""+PlayerData.getPlayerData(player.getName())), new Lang.Args(Lang.Parameter.MESSAGE, e.getMessage().replaceFirst("!", "")));
                } else {
                    PlayerData.broadCast("SNO_WAR.CHAT.GAME", new Lang.Args(Lang.Parameter.PLAYER, ""+PlayerData.getPlayerData(player.getName())), new Lang.Args(Lang.Parameter.MESSAGE, e.getMessage()));
                }
            }
		}
        e.setCancelled(true);
	}
}
