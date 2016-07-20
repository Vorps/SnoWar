package me.vorps.snowar.scoreboard;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.snowar.utils.Lang;
import org.bukkit.scoreboard.DisplaySlot;


public class SbLobby extends ScoreBoard{

    private String lang;

    public SbLobby(String lang){
		super(DisplaySlot.SIDEBAR, Lang.getMessage("SNO_WAR.SB.NAME", lang));
        this.lang = lang;
        super.add("6", Lang.getMessage("SNO_WAR.SB.SPACE", lang), 7);
        super.add("player", Lang.getMessage("SNO_WAR.SB.PLAYER", lang, new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+PlayerData.size())), 6);
		super.add("4", Lang.getMessage("SNO_WAR.SB.WAITING", lang), 4);
        super.add("3", Lang.getMessage("SNO_WAR.SB.SPACE", lang), 3);
        super.add("2", " ", 2);
		super.add("ip", Settings.getIp(), 1);
	}

    public void inStart(int time){
        super.add("time", Lang.getMessage(getKey(time), lang, new Lang.Args(Lang.Parameter.TIME, ""+time)), 5);
    }

    public void updateTime(int time){
        super.updateValue("time",  Lang.getMessage(getKey(time), lang, new Lang.Args(Lang.Parameter.TIME, ""+time)));
    }

    private String getKey(int time){
        String key;
        if(time >= 120){
            key = "SNO_WAR.SB.LOBBY.TIME.MINUTES";
        } else if(time < 120 && time >= 60){
            key = "SNO_WAR.SB.LOBBY.TIME.MINUTE";
        } else if(time < 60 && time > 1){
            key = "SNO_WAR.SB.LOBBY.TIME.MINUTE";
        } else {
            key = "SNO_WAR.SB.LOBBY.TIME.MINUTE";
        }
        return key;
    }
}
