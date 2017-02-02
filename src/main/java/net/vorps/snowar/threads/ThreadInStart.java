package net.vorps.snowar.threads;

import lombok.Getter;
import net.vorps.api.lang.Lang;
import net.vorps.api.type.GameState;
import net.vorps.api.utils.Title;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.SnoWar;
import net.vorps.snowar.objects.Parameter;
import net.vorps.snowar.scoreboard.SbLobby;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class ThreadInStart{

    private static @Getter int task;
    private boolean state;

	public ThreadInStart(){
        this.run();
	}

	private void run(){
        ThreadInStart.task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SnoWar.getInstance(), new Runnable() {
            @Override
            public void run() {
                String key = Timers.getKey("INSTART");
                if(key != null) PlayerData.broadCast(key, new Lang.Args(Lang.Parameter.TIME,  Timers.color()+(Timers.getTime() > 60 ? Timers.getTime()/60 : Timers.getTime())));
                if(Timers.getTime() == 30 ||(Timers.getTime() <= 10 && Timers.getTime() > 0)){
                    Bukkit.getOnlinePlayers().forEach((Player player) -> {
                        player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
                        new Title(Timers.color()+Timers.getTime(), "", player);
                    });
                }
                if(Timers.getTime() == 0){
                    Bukkit.getServer().getScheduler().cancelTask(task);
                    GameState.setState(GameState.INGAME);
                    Timers.run(Parameter.getTimeGame());
                }
                if(Timers.getTime() >= 60) PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> playerData.getScoreboard().updateValue("time",  Lang.getMessage(SbLobby.getKey(Timers.getTime()), playerData.getLang(), new Lang.Args(Lang.Parameter.TIME, ""+Timers.getTime()/60))));
                else PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> playerData.getScoreboard().updateValue("time",  Lang.getMessage(SbLobby.getKey(Timers.getTime()), playerData.getLang(), new Lang.Args(Lang.Parameter.TIME, ""+Timers.getTime()))));
                Timers.removeTime();
            }
        }, 0L, 20L);
	}
}
