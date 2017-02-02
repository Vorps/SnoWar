package net.vorps.snowar.scoreboard;

import net.vorps.api.lang.Lang;
import net.vorps.api.scoreboard.ScoreBoard;
import net.vorps.api.type.GameState;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.Settings;
import net.vorps.snowar.threads.Timers;
import org.bukkit.scoreboard.DisplaySlot;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class SbLobby extends ScoreBoard{

    public SbLobby(final String lang){
		super(DisplaySlot.SIDEBAR, Lang.getMessage("SNO_WAR.SB.NAME", lang));
        super.add("6", "§7"+Lang.getMessage("SNO_WAR.SB.SPACE", lang), 7);
        super.add("player", Lang.getMessage("SNO_WAR.SB.PLAYER", lang, new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+ PlayerData.getPlayerInGame())), 6);
        super.add("4", "§f§7"+Lang.getMessage("SNO_WAR.SB.SPACE", lang), 4);
        super.add("2", " ", 2);
		super.add("ip", Settings.getIp(), 1);
        if(GameState.isState(GameState.INSTART)) super.add("time", Lang.getMessage(SbLobby.getKey(Timers.getTime()), lang, new Lang.Args(Lang.Parameter.TIME, ""+Timers.getTime())), 5);
        else super.add("waiting", Lang.getMessage("SNO_WAR.SB.WAITING", lang), 3);
	}

    /**
     * return key lang
     * @param time int
     * @return String
     */
    public static String getKey(final int time){
        return time >= 120 ? "SNO_WAR.SB.LOBBY.TIME.MINUTES" : time < 120 && time >= 60 ? "SNO_WAR.SB.LOBBY.TIME.MINUTE" : time < 60 && time > 1 ?  "SNO_WAR.SB.LOBBY.TIME.SECONDES" : "SNO_WAR.SB.LOBBY.TIME.SECONDE";
    }
}
