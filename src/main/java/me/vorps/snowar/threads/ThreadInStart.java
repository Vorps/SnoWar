package me.vorps.snowar.threads;

import lombok.Getter;
import me.vorps.snowar.game.GameState;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.SnowWar;
import me.vorps.snowar.objects.Parameter;
import me.vorps.snowar.scoreboard.SbLobby;
import me.vorps.syluriapi.lang.Lang;
import me.vorps.syluriapi.utils.Title;
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
        ThreadInStart.task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SnowWar.getInstance(), new Runnable() {
            @Override
            public void run() {
                state = false;
                if(Timers.getTime()%600 == 0 && Timers.getTime() != 0 || (Timers.getTime() >= 300 && Timers.getTime() > 60 && Timers.getTime()%60 == 0)){
                    PlayerData.broadCast("SNO_WAR.THREAD.INSTART.MINUTES", new Lang.Args(Lang.Parameter.TIME, Timers.color()+Timers.getTime()/60));
                }
                if(Timers.getTime() == 60){
                    PlayerData.broadCast("SNO_WAR.THREAD.INSTART.MINUTE", new Lang.Args(Lang.Parameter.TIME, Timers.color()+"1"));
                }
                if(Timers.getTime() == 30 ||(Timers.getTime() <= 10 && Timers.getTime() > 1)){
                    state = true;
                    PlayerData.broadCast("SNO_WAR.THREAD.INSTART.SECONDES", new Lang.Args(Lang.Parameter.TIME, Timers.color()+Timers.getTime()));
                }
                if(Timers.getTime() == 1){
                    state = true;
                    PlayerData.broadCast("SNO_WAR.THREAD.INSTART.SECONDE", new Lang.Args(Lang.Parameter.TIME, Timers.color()+"1"));
                }
                if(state){
                    Bukkit.getOnlinePlayers().forEach((Player player) -> {
                        player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
                        new Title(Timers.color()+Timers.getTime(), "").send(player);
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
                Timers.removeTime();
            }
        }, 0L, 20L);
	}
}
