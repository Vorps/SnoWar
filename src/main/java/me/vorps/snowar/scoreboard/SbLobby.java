package me.vorps.snowar.scoreboard;

import me.vorps.snowar.game.GameState;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.snowar.threads.Timers;
import me.vorps.syluriapi.lang.Lang;
import me.vorps.syluriapi.scoreboard.ScoreBoard;
import org.bukkit.scoreboard.DisplaySlot;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class SbLobby extends ScoreBoard{

    public SbLobby(final String lang){
		super(DisplaySlot.SIDEBAR, Lang.getMessage("SNO_WAR.SB.NAME", lang));
        super.add("6", "§7"+Lang.getMessage("SNO_WAR.SB.SPACE", lang), 7);
        super.add("player", Lang.getMessage("SNO_WAR.SB.PLAYER", lang, new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+PlayerData.getPlayerInGame())), 6);
        super.add("4", "§f§7"+Lang.getMessage("SNO_WAR.SB.SPACE", lang), 4);
        super.add("2", " ", 2);
		super.add("ip", Settings.getIp(), 1);
        if(GameState.isState(GameState.INSTART)){
            super.add("time", Lang.getMessage(SbLobby.getKey(Timers.getTime()), lang, new Lang.Args(Lang.Parameter.TIME, ""+Timers.getTime())), 5);
        } else {
            super.add("waiting", Lang.getMessage("SNO_WAR.SB.WAITING", lang), 3);
        }
	}

    /**
     * return key lang
     * @param time int
     * @return String
     */
    public static String getKey(final int time){
        String key;
        if(time >= 120){
            key = "SNO_WAR.SB.LOBBY.TIME.MINUTES";
        } else if(time < 120 && time >= 60){
            key = "SNO_WAR.SB.LOBBY.TIME.MINUTE";
        } else if(time < 60 && time > 1){
            key = "SNO_WAR.SB.LOBBY.TIME.SECONDES";
        } else {
            key = "SNO_WAR.SB.LOBBY.TIME.SECONDE";
        }
        return key;
    }
}
