package me.vorps.snowar.threads;

import io.netty.util.Timer;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.SnowWar;
import me.vorps.snowar.utils.ActionBar;
import me.vorps.snowar.utils.Lang;
import me.vorps.snowar.utils.Title;
import me.vorps.snowar.utils.Victory;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class ThreadGame{

    private int task;
    private int state;
    private boolean aBoolean;

	public ThreadGame(){
        state = 0;
        run();
	}
	
	private void run(){
        task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SnowWar.getInstance(), new Runnable() {
            @Override
            public void run() {
                aBoolean = false;
                if(Timers.getTime() > 0 && (Timers.getTime() == 300 || Timers.getTime() == 240 || Timers.getTime() == 180 || Timers.getTime() == 120)){
                    PlayerData.broadCast("SNO_WAR.THREAD.GAME.MINUTES", new Lang.Args(Lang.Parameter.TIME, ""+(Timers.getTime()/60)));
                }
                if(Timers.getTime() == 60){
                    PlayerData.broadCast("SNO_WAR.THREAD.GAME.MINUTE", new Lang.Args(Lang.Parameter.TIME, "1"));
                }
                if(Timers.getTime() <= 10 && Timers.getTime() > 1){
                    aBoolean = true;
                    PlayerData.broadCast("SNO_WAR.THREAD.GAME.SECONDES", new Lang.Args(Lang.Parameter.TIME, ""+Timers.getTime()));
                }
                if(Timers.getTime() == 1){
                    aBoolean = true;
                    PlayerData.broadCast("SNO_WAR.THREAD.GAME.SECONDE", new Lang.Args(Lang.Parameter.TIME, "1"));
                }
                if(aBoolean){
                    Bukkit.getOnlinePlayers().forEach((Player player) -> {
                        player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
                        new Title("", "§a"+Timers.getTime()).send(player);
                    });
                }
                Timers.updateTime();
                if(Timers.getTime()%10 == 0){
                    switch (state++){
                        case 0:
                            PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> {
                                if(playerData.getLife() == 0){
                                    ActionBar.sendActionBar("§eVie : §c"+playerData.getLife(), playerData.getPlayer());
                                } else {
                                    ActionBar.sendActionBar("§eMode : §aSpectateur", playerData.getPlayer());
                                }
                            });
                            break;
                        case 1:
                            PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> {
                                if(playerData.getLife() == 0){
                                    ActionBar.sendActionBar("§eBonus : ");
                                } else {
                                    ActionBar.sendActionBar("§eMode : §aSpectateur", playerData.getPlayer());
                                }
                            });
                            break;
                        case 2:
                            PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> {
                                Date date = new Date(Timers.getTime()*1000);
                                date.setHours(date.getHours()-1);
                                SimpleDateFormat simpleDateFormat;
                                if(Timers.getTime() > 3600){
                                    simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                                } else {
                                    simpleDateFormat = new SimpleDateFormat("mm:ss");
                                }
                                ActionBar.sendActionBar("§eTemps : §a"+simpleDateFormat.format(date), playerData.getPlayer());
                            });
                            break;
                        case 3:
                            PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> {
                                if(playerData.getLife() == 0){
                                    String spect = "";
                                    for(String spectator : playerData.getSpectator()){
                                        spect += " "+spectator;
                                    }
                                    if(spect.isEmpty()){
                                        ActionBar.sendActionBar("§eSpectateur : §7(§6"+playerData.getSpectator()+"§7)", playerData.getPlayer());
                                    } else {
                                        ActionBar.sendActionBar("§eSpectateur : §7(§6"+playerData.getSpectator()+"§7)"+spect, playerData.getPlayer());
                                    }
                                } else {
                                    if(playerData.getPlayerView() == null){
                                        ActionBar.sendActionBar("§eVue global", playerData.getPlayer());
                                    } else {
                                        ActionBar.sendActionBar("§eJoueur : "+PlayerData.getPlayerData(playerData.getPlayerView()), playerData.getPlayer());
                                    }
                                }
                            });
                            state = 0;
                            break;
                        default:
                            break;
                    }
                }
                if(Timers.getTime() == 0){
                    Bukkit.getServer().getScheduler().cancelTask(task);
                    Victory.onVictory(1);
                }
                Timers.setTime(Timers.getTime()-1);
            }
        }, 0L, 20L);
	}
}
