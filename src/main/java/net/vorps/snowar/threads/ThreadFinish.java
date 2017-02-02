package net.vorps.snowar.threads;

import net.vorps.api.lang.Lang;
import net.vorps.api.utils.Title;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.SnoWar;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ThreadFinish{

    private int task;

	public ThreadFinish(){
        this.run();
	}
	
	private void run(){
        this.task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SnoWar.getInstance(), new Runnable() {
            @Override
            public void run() {
                String key = Timers.getKey("FINISH");
                if(key != null) PlayerData.broadCast(key, new Lang.Args(Lang.Parameter.TIME,  Timers.color()+(Timers.getTime() > 60 ? Timers.getTime()/60 : Timers.getTime())));
                if(Timers.getTime() == 30 || (Timers.getTime() <= 10 && Timers.getTime() > 0)){
                    Bukkit.getOnlinePlayers().forEach((Player player) -> {
                        player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
                        new Title(Timers.color()+Timers.getTime(), "", player);
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
