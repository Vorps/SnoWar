package me.vorps.snowar.scoreboard;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.snowar.utils.Lang;
import org.bukkit.scoreboard.DisplaySlot;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class SbLobby extends ScoreBoard{

    public SbLobby(String lang){
		super(DisplaySlot.SIDEBAR, Lang.getMessage("SNO_WAR.SB.NAME", lang));
        super.add("6", "§7"+Lang.getMessage("SNO_WAR.SB.SPACE", lang), 7);
        super.add("player", Lang.getMessage("SNO_WAR.SB.PLAYER", lang, new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+PlayerData.size())), 6);
        super.add("4", "§f§7"+Lang.getMessage("SNO_WAR.SB.SPACE", lang), 4);
		super.add("waiting", Lang.getMessage("SNO_WAR.SB.WAITING", lang), 3);
        super.add("2", " ", 2);
		super.add("ip", Settings.getIp(), 1);
	}

    public static String getKey(int time){
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
