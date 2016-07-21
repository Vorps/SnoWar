package me.vorps.snowar.threads;

import lombok.Getter;
import me.vorps.snowar.GameState;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.SnowWar;
import me.vorps.snowar.objects.Parameter;
import me.vorps.snowar.scoreboard.SbLobby;
import me.vorps.snowar.utils.Lang;
import me.vorps.snowar.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadInStart{

    private static @Getter int task;
    private boolean state;

	public ThreadInStart(){
        run();
	}

	private void run(){
        task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SnowWar.getInstance(), new Runnable() {
            @Override
            public void run() {
                state = false;
                if(Timers.getTime()%600 == 0 && Timers.getTime() != 0 || (Timers.getTime() >= 300 && Timers.getTime() > 60 && Timers.getTime()%60 == 0)){
                    PlayerData.broadCast("SNO_WAR.THREAD.INSTART.MINUTES", new Lang.Args(Lang.Parameter.TIME, ""+Timers.getTime()/60));
                }
                if(Timers.getTime() == 60){
                    PlayerData.broadCast("SNO_WAR.THREAD.INSTART.MINUTE", new Lang.Args(Lang.Parameter.TIME, "1"));
                }
                if(Timers.getTime() == 30 ||(Timers.getTime() <= 10 && Timers.getTime() > 1)){
                    state = true;
                    PlayerData.broadCast("SNO_WAR.THREAD.INSTART.SECONDES", new Lang.Args(Lang.Parameter.TIME, ""+Timers.getTime()));
                }
                if(Timers.getTime() == 1){
                    state = true;
                    PlayerData.broadCast("SNO_WAR.THREAD.INSTART.SECONDE", new Lang.Args(Lang.Parameter.TIME, "1"));
                }
                if(state){
                    Bukkit.getOnlinePlayers().forEach((Player player) -> {
                        player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
                        new Title("", "§a"+Timers.getTime()).send(player);
                    });
                }
                if(Timers.getTime() == 0){
                    Bukkit.getServer().getScheduler().cancelTask(task);
                    GameState.setState(GameState.INGAME);
                    Timers.run(Parameter.getTimeGame());
                }
                if(Timers.getTime() >= 60){
                    PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> playerData.getScoreboard().updateValue("time",  Lang.getMessage(SbLobby.getKey(Timers.getTime()), playerData.getLang(), new Lang.Args(Lang.Parameter.TIME, ""+Timers.getTime()/60))));
                } else {
                    PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> playerData.getScoreboard().updateValue("time",  Lang.getMessage(SbLobby.getKey(Timers.getTime()), playerData.getLang(), new Lang.Args(Lang.Parameter.TIME, ""+Timers.getTime()))));
                }
                Timers.setTime(Timers.getTime()-1);
            }
        }, 0L, 20L);
	}
}
