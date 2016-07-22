package me.vorps.snowar.threads;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.SnowWar;
import me.vorps.snowar.utils.Lang;
import me.vorps.snowar.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadFinish{

	private int time;
    private int task;
    private boolean state;

	public ThreadFinish(int time){
		this.time = time;
        run();
	}
	
	private void run(){
        task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SnowWar.getInstance(), new Runnable() {
            @Override
            public void run() {
                state = false;
                if(Timers.getTime()%600 == 0 && Timers.getTime() != 0 || (Timers.getTime() >= 300 && Timers.getTime() > 60 && Timers.getTime()%60 == 0)){
                    PlayerData.broadCast("SNO_WAR.THREAD.FINISH.MINUTES", new Lang.Args(Lang.Parameter.TIME, ""+Timers.getTime()/60));
                }
                if(Timers.getTime() == 60){
                    PlayerData.broadCast("SNO_WAR.THREAD.FINISH.MINUTE", new Lang.Args(Lang.Parameter.TIME, "1"));
                }
                if(Timers.getTime() == 30 ||(Timers.getTime() <= 10 && Timers.getTime() > 1)){
                    state = true;
                    PlayerData.broadCast("SNO_WAR.THREAD.FINISH.SECONDES", new Lang.Args(Lang.Parameter.TIME, ""+Timers.getTime()));
                }
                if(Timers.getTime() == 1){
                    state = true;
                    PlayerData.broadCast("SNO_WAR.THREAD.FINISH.SECONDE", new Lang.Args(Lang.Parameter.TIME, "1"));
                }
                if(state){
                    Bukkit.getOnlinePlayers().forEach((Player player) -> {
                        player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
                        new Title("", "§a"+Timers.getTime()).send(player);
                    });
                }
                if(time == 0){
                    Bukkit.getServer().getScheduler().cancelTask(task);
                    Bukkit.getServer().shutdown();
                }
                if(state){
                    Bukkit.getOnlinePlayers().forEach((Player player) -> {
                        player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
                        new Title("", "§a"+time).send(player);
                    });
                }
                Timers.updateTime();
                time--;
            }
        }, 0L, 20L);
	}
}
