package me.vorps.snowar.threads;

import me.vorps.snowar.GameState;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.SnowWar;
import me.vorps.snowar.utils.Lang;
import me.vorps.snowar.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadInStart{

    private int task;
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
                    state = true;
                    PlayerData.broadCast("SNO_WAR.THREAD.INSTART.MINUTES", new Lang.Args(Lang.Parameter.TIME, ""+Timers.getTime()/60));
                }
                if(Timers.getTime() == 60){
                    state = true;
                    PlayerData.broadCast("SNO_WAR.THREAD.INSTART.MINUTE", new Lang.Args(Lang.Parameter.TIME, "1"));
                }
                if(Timers.getTime() == 30 ||(Timers.getTime() <= 10 && Timers.getTime() > 1)){
                    state = true;
                    PlayerData.broadCast("SNO_WAR.THREAD.INSTART.SECONDS", new Lang.Args(Lang.Parameter.TIME, ""+Timers.getTime()));
                }
                if(Timers.getTime() == 1){
                    state = true;
                    PlayerData.broadCast("SNO_WAR.THREAD.INSTART.SECOND", new Lang.Args(Lang.Parameter.TIME, "1"));
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
                    Timers.run(Parameter.getTime()Game());
                }
                PlayerData.getPlayersList().values().forEach((PlayerData playerData) -> playerData.getBoard().updateValue("Timers.getTime()", Lang.getMessage("RUSH_VOLCANO.SB.Timers.getTime()", playerData.getLang(), new Lang.Args(Lang.Parameter.Timers.getTime(), new SimpleDateFormat("mm:ss").format(new Date(Timers.getTime()*1000))))));
                Timers.setTime(Timers.getTime()-1);
            }
        }, 0L, 20L);
	}
}
