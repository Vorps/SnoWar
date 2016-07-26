package me.vorps.snowar.threads;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.snowar.SnowWar;
import me.vorps.snowar.bonus.Bonus;
import me.vorps.snowar.utils.ActionBar;
import me.vorps.snowar.lang.Lang;
import me.vorps.snowar.utils.Title;
import me.vorps.snowar.game.Victory;
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
                    PlayerData.broadCast("SNO_WAR.THREAD.GAME.MINUTES", new Lang.Args(Lang.Parameter.TIME, Timers.color()+(Timers.getTime()/60)));
                }
                if(Timers.getTime() == 60){
                    PlayerData.broadCast("SNO_WAR.THREAD.GAME.MINUTE", new Lang.Args(Lang.Parameter.TIME, Timers.color()+"1"));
                }
                if(Timers.getTime() <= 10 && Timers.getTime() > 1){
                    aBoolean = true;
                    PlayerData.broadCast("SNO_WAR.THREAD.GAME.SECONDES", new Lang.Args(Lang.Parameter.TIME, Timers.color()+Timers.getTime()));
                }
                if(Timers.getTime() == 1){
                    aBoolean = true;
                    PlayerData.broadCast("SNO_WAR.THREAD.GAME.SECONDE", new Lang.Args(Lang.Parameter.TIME, Timers.color()+"1"));
                }
                if(aBoolean){
                    Bukkit.getOnlinePlayers().forEach((Player player) -> {
                        player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
                        new Title(Timers.color()+Timers.getTime(), "").send(player);
                    });
                }
                Timers.updateTime();
                if(Timers.getTime()%Settings.getTimeMessage() == 0){
                    switch (state++){
                        case 0:
                            PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> {
                                if(playerData.getLife() > 0){
                                    if(playerData.getLife() == 1){
                                        ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.LIFE", playerData.getLang(), new Lang.Args(Lang.Parameter.LIFE, "1")), playerData.getPlayer());
                                    } else {
                                        ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.LIFES", playerData.getLang(), new Lang.Args(Lang.Parameter.LIFE, ""+playerData.getLife())), playerData.getPlayer());
                                    }
                                } else {
                                    ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.SPECTATOR", playerData.getLang()), playerData.getPlayer());
                                }
                            });
                            break;
                        case 1:
                            PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> {
                                if(playerData.getLife() > 0){
                                    if(playerData.getBonusData().isEmpty()){
                                        ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.BONUS.0", playerData.getLang()), playerData.getPlayer());
                                    } else if(playerData.getBonusData().size() == 1){
                                        String bonus = "";
                                        for(Bonus bonus1 : playerData.getBonusData().keySet()){
                                            bonus = bonus1.toString();
                                        }
                                        ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.BONUS.1", playerData.getLang(), new Lang.Args(Lang.Parameter.BONUS, bonus)), playerData.getPlayer());
                                    } else {
                                        String bonus = "";
                                        for(Bonus bonus1 : playerData.getBonusData().keySet()){
                                            bonus+=bonus1.toString()+"§7,";
                                        }
                                        ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.BONUS.2", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+playerData.getBonusData().size()), new Lang.Args(Lang.Parameter.BONUS, bonus.substring(0, bonus.length()-1))), playerData.getPlayer());
                                    }
                                } else {
                                    ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.PLAYER", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+PlayerData.getPlayerInGame())), playerData.getPlayer());
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
                                ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.TIME", playerData.getLang(), new Lang.Args(Lang.Parameter.TIME, ""+simpleDateFormat.format(date))), playerData.getPlayer());
                            });
                            break;
                        case 3:
                            PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> {
                                if(playerData.getLife() > 0){
                                    String spect = "";
                                    for(String spectator : playerData.getSpectator()){
                                        spect += spectator+"§7,";
                                    }
                                    if(playerData.getSpectator().isEmpty()){
                                        ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.SPECTATOR.0", playerData.getLang()), playerData.getPlayer());
                                    } else {
                                        ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.SPECTATOR.1", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+playerData.getSpectator().size()), new Lang.Args(Lang.Parameter.PLAYER, spect.substring(0, spect.length()-1))), playerData.getPlayer());
                                    }
                                } else {
                                    if(playerData.getPlayerView() == null){
                                        ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.MODE.VIEW.GLOBAL", playerData.getLang()), playerData.getPlayer());
                                    } else {
                                        ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.PLAYER",playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+PlayerData.getPlayerData(playerData.getPlayerView()))), playerData.getPlayer());
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
