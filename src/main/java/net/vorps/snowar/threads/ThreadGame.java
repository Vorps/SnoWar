package net.vorps.snowar.threads;

import net.vorps.api.lang.Lang;
import net.vorps.api.utils.ActionBar;
import net.vorps.api.utils.Title;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.Settings;
import net.vorps.snowar.SnoWar;
import net.vorps.snowar.bonus.Bonus;
import net.vorps.snowar.game.Victory;
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

	public ThreadGame(){
        this.state = 0;
        this.run();
	}
	
	private void run(){
        this.task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SnoWar.getInstance(), new Runnable() {
            @Override
            public void run() {
                String key = Timers.getKey("GAME");
                if(key != null) PlayerData.broadCast(key, new Lang.Args(Lang.Parameter.TIME,  Timers.color()+(Timers.getTime() > 60 ? Timers.getTime()/60 : Timers.getTime())));
                if(Timers.getTime() <= 10 && Timers.getTime() > 0){
                    Bukkit.getOnlinePlayers().forEach((Player player) -> {
                        player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
                        new Title(Timers.color()+Timers.getTime(), "", player);
                    });
                }
                Timers.updateTime();
                if(Timers.getTime()%Settings.getTimeMessage() == 0){
                    switch (state++){
                        case 0:
                            PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> {
                                if(playerData.getLife() > 0){
                                    if(playerData.getLife() == 1) ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.LIFE", playerData.getLang(), new Lang.Args(Lang.Parameter.LIFE, "1")), playerData.getPlayer());
                                    else ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.LIFES", playerData.getLang(), new Lang.Args(Lang.Parameter.LIFE, ""+playerData.getLife())), playerData.getPlayer());
                                } else ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.SPECTATOR", playerData.getLang()), playerData.getPlayer());
                            });
                            break;
                        case 1:
                            PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> {
                                if(playerData.getLife() > 0){
                                    if(playerData.getBonusData().isEmpty()) ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.BONUS.0", playerData.getLang()), playerData.getPlayer());
                                    else if(playerData.getBonusData().size() == 1){
                                        String bonus = "";
                                        for(Bonus bonus1 : playerData.getBonusData().keySet()) bonus = bonus1.toString();
                                        ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.BONUS.1", playerData.getLang(), new Lang.Args(Lang.Parameter.BONUS, bonus)), playerData.getPlayer());
                                    } else {
                                        String bonus = "";
                                        for(Bonus bonus1 : playerData.getBonusData().keySet()) bonus+=bonus1.toString()+"§7,";
                                        ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.BONUS.2", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+playerData.getBonusData().size()), new Lang.Args(Lang.Parameter.BONUS, bonus.substring(0, bonus.length()-1))), playerData.getPlayer());
                                    }
                                } else ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.PLAYER", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+PlayerData.getPlayerInGame())), playerData.getPlayer());
                            });
                            break;
                        case 2:
                            PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> {
                                Date date = new Date(Timers.getTime()*1000);
                                date.setHours(date.getHours()-1);
                                ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.TIME", playerData.getLang(), new Lang.Args(Lang.Parameter.TIME, ""+(Timers.getTime() > 3600 ? new SimpleDateFormat("HH:mm:ss") : new SimpleDateFormat("mm:ss")).format(date))), playerData.getPlayer());
                            });
                            break;
                        case 3:
                            PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> {
                                if(playerData.getLife() > 0){
                                    String spect = "";
                                    for(String spectator : playerData.getSpectator()) spect += spectator+"§7,";
                                    if(playerData.getSpectator().isEmpty()) ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.SPECTATOR.0", playerData.getLang()), playerData.getPlayer());
                                    else ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.SPECTATOR.1", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+playerData.getSpectator().size()), new Lang.Args(Lang.Parameter.PLAYER, spect.substring(0, spect.length()-1))), playerData.getPlayer());
                                } else {
                                    if(playerData.getPlayerView() == null) ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.MODE.VIEW.GLOBAL", playerData.getLang()), playerData.getPlayer());
                                    else ActionBar.sendActionBar(Lang.getMessage("SNO_WAR.ACTION.PLAYER",playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+PlayerData.getPlayerData(playerData.getPlayerView()))), playerData.getPlayer());
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
