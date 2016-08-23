package me.vorps.snowar.threads;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.SnowWar;
import me.vorps.syluriapi.lang.Lang;
import me.vorps.syluriapi.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ThreadFinish{

    private int task;
    private boolean state;

	public ThreadFinish(){
        this.run();
	}
	
	private void run(){
        this.task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SnowWar.getInstance(), new Runnable() {
            @Override
            public void run() {
                state = false;
                if(Timers.getTime()%600 == 0 && Timers.getTime() != 0 || (Timers.getTime() >= 300 && Timers.getTime() > 60 && Timers.getTime()%60 == 0)){
                    PlayerData.broadCast("SNO_WAR.THREAD.FINISH.MINUTES", new Lang.Args(Lang.Parameter.TIME, Timers.color()+Timers.getTime()/60));
                }
                if(Timers.getTime() == 60){
                    PlayerData.broadCast("SNO_WAR.THREAD.FINISH.MINUTE", new Lang.Args(Lang.Parameter.TIME, Timers.color()+"1"));
                }
                if(Timers.getTime() == 30 ||(Timers.getTime() <= 10 && Timers.getTime() > 1)){
                    state = true;
                    PlayerData.broadCast("SNO_WAR.THREAD.FINISH.SECONDES", new Lang.Args(Lang.Parameter.TIME, Timers.color()+Timers.getTime()));
                }
                if(Timers.getTime() == 1){
                    state = true;
                    PlayerData.broadCast("SNO_WAR.THREAD.FINISH.SECONDE", new Lang.Args(Lang.Parameter.TIME, Timers.color()+"1"));
                }
                if(state){
                    Bukkit.getOnlinePlayers().forEach((Player player) -> {
                        player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
                        new Title(Timers.color()+Timers.getTime(), "").send(player);
                    });
                }
                if(Timers.getTime() == 0){
                    Bukkit.getServer().getScheduler().cancelTask(task);
                    Bukkit.getServer().shutdown();
                }
                Timers.updateTime();
                Timers.removeTime();
            }
        }, 0L, 20L);
	}
}
