package me.vorps.snowar.threads;

import me.vorps.snowar.SnowWar;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadInStart{

	private int time;
    private int task;

	public ThreadInStart(int time){
		this.time = time;
        run();
	}

	private void run(){
        task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SnowWar.getInstance(), new Runnable() {
            @Override
            public void run() {
                /*
                if(time%600 == 0 && time > 0 || (time == 300 || time == 240 || time == 180 || time == 120)){
                    PlayerData.getPlayersList().values().forEach((PlayerData playerData) -> Bukkit.getPlayer(playerData.getName()).sendMessage(Settings.getTitle()+ Lang.getMessage("RUSH_VOLCANO.THREAD.LOBBY.MINUTES", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+(time/60)))));
                }
                if(time == 60){
                    PlayerData.getPlayersList().values().forEach((PlayerData playerData) -> Bukkit.getPlayer(playerData.getName()).sendMessage(Settings.getTitle()+Lang.getMessage("RUSH_VOLCANO.THREAD.LOBBY.MINUTE", playerData.getLang())));
                }
                if(time == 30 ||(time <= 10 && time > 1)){
                    PlayerData.getPlayersList().values().forEach((PlayerData playerData) -> Bukkit.getPlayer(playerData.getName()).sendMessage(Settings.getTitle()+Lang.getMessage("RUSH_VOLCANO.THREAD.LOBBY.SECONDES", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+time))));
                }
                if(time == 1){
                    PlayerData.getPlayersList().values().forEach((PlayerData playerData) -> Bukkit.getPlayer(playerData.getName()).sendMessage(Settings.getTitle()+Lang.getMessage("RUSH_VOLCANO.THREAD.LOBBY.SECONDE", playerData.getLang())));
                }
                if(time%600 == 0 && time > 0 || (time == 300 || time == 240 || time == 180 || time == 120) || time == 60 || time == 30 ||(time <= 10 && time > 0)){
                    Bukkit.getOnlinePlayers().forEach((Player player) -> {
                        player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
                        new Title("", "§a"+time).send(player);
                    });
                }
                if(time == 0){
                    Bukkit.getServer().getScheduler().cancelTask(task);
                    GameState.setState(GameState.INGAME);
                    Timers.run(Parameter.getTimeGame());
                }
                PlayerData.getPlayersList().values().forEach((PlayerData playerData) -> playerData.getBoard().updateValue("time", Lang.getMessage("RUSH_VOLCANO.SB.TIME", playerData.getLang(), new Lang.Args(Lang.Parameter.TIME, new SimpleDateFormat("mm:ss").format(new Date(time*1000))))));
                time--;
                */
            }
        }, 0L, 20L);
	}
}
