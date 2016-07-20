package me.vorps.snowar.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		/*
        Player player = e.getPlayer();
		PlayerData playerData = PlayerData.getPlayerData(player.getName());
		if(GameState.isState(GameState.WAITING) || GameState.isState(GameState.INSTART)){
            PlayerData.broadCast("RUSH_VOLCANO.QUIT.LOBBY", new Lang.Args(Lang.Parameter.PLAYER, ""+player.getName()), new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+(Bukkit.getOnlinePlayers().size()-1)), new Lang.Args(Lang.Parameter.NBR_MAX_PLAYER, ""+ Data.getNumberPlayer()));


            layerData.getPlayersList().values().forEach((PlayerData playerDataList) -> playerDataList.getBoard().updateValue("player", Lang.getMessage("RUSH_VOLCANO.SB_NAME.LOBBY.PLAYER", PlayerData.getPlayerData(player.getName()).getLang(), new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+(Bukkit.getOnlinePlayers().size()-1)))));
			LobbyManager.updateInventory();
			if(GameState.isState(GameState.INSTART)){
                GameState.setState(GameState.WAITING);
				Bukkit.getScheduler().cancelAllTasks();
                PlayerData.getPlayersList().values().forEach((PlayerData playerData1) -> playerData1.getBoard().updateValue("time", Lang.getMessage("RUSH_VOLCANO.SB.TIME", playerData.getLang(), new Lang.Args(Lang.Parameter.TIME, new SimpleDateFormat("mm:ss").format(new Date(0*1000))))));
			}
		} else {
            Team team = playerData.getTeam();
            Team.removePlayer(player.getName());
			if(team != null && GameState.isState(GameState.INGAME)){
                PlayerData.getPlayersList().values().forEach((PlayerData playerDataList) -> Bukkit.getPlayer(playerDataList.getName()).sendMessage(Settings.getTitle()+Lang.getMessage("RUSH_VOLCANO.QUIT.GAME", playerDataList.getLang(), new Lang.Args(Lang.Parameter.PLAYER, player.getName()))));
                Stats.updateStats(player.getName(), new int[]{playerData.getKills(), playerData.getDead(), playerData.getWool(), playerData.getAllGolds(), (3000-Timers.getThreadGame().getTimeGame()), 0, 1});
                PlayerData.getPlayersList().values().forEach((PlayerData playerDataList) -> {
                    if(playerDataList.getTeam() != null){
                        if(SbGame.getListTeamSb().contains(team.getName())){
                            playerDataList.getBoard().updateValue(team.getName(), Lang.getMessage("RUSH_VOLCANO.SB.TEAM", playerDataList.getLang(), new Lang.Args[] {new Lang.Args(Lang.Parameter.TEAM, team.showName(playerDataList.getLang())), new Lang.Args(Lang.Parameter.POINT, ""+(team.getNoyau()* MapParameter.getNumberNoyau())/100), new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+team.nbrPlayer())}));
                        }
                    } else {
                        if(SbSpectator.getListTeamSb().contains(team.getName())){
                            playerDataList.getBoard().updateValue(team.getName(), Lang.getMessage("RUSH_VOLCANO.SB.TEAM", playerDataList.getLang(), new Lang.Args[] {new Lang.Args(Lang.Parameter.TEAM, team.showName(playerDataList.getLang())), new Lang.Args(Lang.Parameter.POINT, ""+(team.getNoyau()* MapParameter.getNumberNoyau())/100), new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+team.nbrPlayer())}));
                        }
                    }
                });
                if(Team.teamInGame().size() == 1){
                    Victory.onVictory(2);
                } else {
                    team.finish(2);
                }
			}
		}
        if(GameState.isState(GameState.FINISH) && Bukkit.getOnlinePlayers().size()-1 == 0){
            Bukkit.getServer().shutdown();
        }
        e.setQuitMessage(null);
        playerData.deletePlayerData();
	*/
    }
}
