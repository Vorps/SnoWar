package me.vorps.snowar.scoreboard;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.snowar.threads.Timers;
import me.vorps.snowar.utils.Lang;
import org.bukkit.scoreboard.DisplaySlot;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project RushVolcano Created by Vorps on 25/04/2016 at 22:25.
 */
public class SbGame extends ScoreBoard{

    public SbGame(PlayerData playerData){
        super(DisplaySlot.SIDEBAR, Lang.getMessage("SNO_WAR.SB.NAME", playerData.getLang()));
        Date date = new Date(Timers.getTime()*1000);
        date.setHours(date.getHours()-1);
        String lang = playerData.getLang();
        super.add("8", "  ", 8);
        super.add("7", "§7"+Lang.getMessage("SNO_WAR.SB.SPACE", lang), 7);
        SimpleDateFormat simpleDateFormat;
        if(Timers.getTime() > 3600){
            simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        } else {
            simpleDateFormat = new SimpleDateFormat("mm:ss");
        }
        super.add("time", Lang.getMessage("SNO_WAR.SB.TIME",  lang, new Lang.Args(Lang.Parameter.TIME, simpleDateFormat.format(date))), 6);
        super.add("5", "§f§7"+Lang.getMessage("SNO_WAR.SB.SPACE", lang), 5);
        super.add("player", Lang.getMessage("SNO_WAR.SB.PLAYER", lang, new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+PlayerData.size())), 6);
        super.add("kill", Lang.getMessage("SNO_WAR.SB.KILL", lang, new Lang.Args(Lang.Parameter.VAR, ""+playerData.getKill())), 3);
        super.add("2", " ", 2);
        super.add("ip", Settings.getIp(), 1);
    }
}
