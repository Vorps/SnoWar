package net.vorps.snowar.scoreboard;

import net.vorps.api.lang.Lang;
import net.vorps.api.scoreboard.ScoreBoard;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.Settings;
import net.vorps.snowar.threads.Timers;
import org.bukkit.scoreboard.DisplaySlot;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class SbGame extends ScoreBoard{

    public SbGame(final PlayerData playerData){
        super(DisplaySlot.SIDEBAR, Lang.getMessage("SNO_WAR.SB.NAME", playerData.getLang()));
        Date date = new Date(Timers.getTime()*1000);
        date.setHours(date.getHours()-1);
        String lang = playerData.getLang();
        super.add("8", "  ", 8);
        super.add("7", "§7"+Lang.getMessage("SNO_WAR.SB.SPACE", lang), 8);
        super.add("time", Lang.getMessage("SNO_WAR.SB.TIME",  lang, new Lang.Args(Lang.Parameter.TIME, (Timers.getTime() > 3600 ? new SimpleDateFormat("HH:mm:ss") : new SimpleDateFormat("mm:ss")).format(date))), 7);
        super.add("5", "§f§7"+Lang.getMessage("SNO_WAR.SB.SPACE", lang), 5);
        super.add("player", Lang.getMessage("SNO_WAR.SB.PLAYER", lang, new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+PlayerData.getPlayerInGame())), 6);
        super.add("kill", Lang.getMessage("SNO_WAR.SB.KILL", lang, new Lang.Args(Lang.Parameter.VAR, ""+playerData.getKill())), 4);
        super.add("life", Lang.getMessage("SNO_WAR.SB.LIFE", lang, new Lang.Args(Lang.Parameter.VAR, ""+playerData.getLife())), 3);
        super.add("2", " ", 2);
        super.add("ip", Settings.getIp(), 1);
    }
}
