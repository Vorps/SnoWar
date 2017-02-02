package net.vorps.snowar.scoreboard;

import net.vorps.api.lang.Lang;
import net.vorps.api.scoreboard.ScoreBoard;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.Settings;
import org.bukkit.scoreboard.DisplaySlot;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project RushVolcano Created by Vorps on 25/04/2016 at 17:08.
 */
public class SbFinish extends ScoreBoard{

    public SbFinish(final String lang){
        super(DisplaySlot.SIDEBAR, Lang.getMessage("SNO_WAR.SB.NAME", lang));
        super.add("15", Lang.getMessage("SNO_WAR.SB.STATS", lang), 14);
        super.add("14", " ", 13);
        super.add("time", Lang.getMessage("SNO_WAR.SB.TIME", lang, new Lang.Args(Lang.Parameter.TIME, new SimpleDateFormat("mm:ss").format(new Date(Settings.getTimeFinish()*1000)))), 12);
        super.add("16", "  ", 11);
        String[] winner = new String[3];
        int i = 0;
        for(PlayerData playerData : PlayerData.triePlayerDataLife().values()){
            winner[i++] = Lang.getMessage("SNO_WAR.SB.PLAYERS", lang, new Lang.Args(Lang.Parameter.PLAYER, playerData.getPlayer().getName()), new Lang.Args(Lang.Parameter.LIFE, ""+playerData.getLife()));
            if(i == 3) break;
        }
        if(PlayerData.getPlayerInGame() >= 1) super.add("first", Lang.getMessage("SNO_WAR.SB.FINISH.TEAM", lang, new Lang.Args(Lang.Parameter.VAR, ""+1), new Lang.Args(Lang.Parameter.WINNER, winner[0])), 10);
        if(PlayerData.getPlayerInGame() >= 2) super.add("second", Lang.getMessage("SNO_WAR.SB.FINISH.TEAM", lang, new Lang.Args(Lang.Parameter.VAR, ""+2), new Lang.Args(Lang.Parameter.WINNER, winner[1])), 9);
        if(PlayerData.getPlayerInGame() >= 3) super.add("third", Lang.getMessage("SNO_WAR.SB.FINISH.TEAM", lang, new Lang.Args(Lang.Parameter.VAR, ""+3), new Lang.Args(Lang.Parameter.WINNER, winner[2])), 8);
        super.add("14", "  ", 7);
        super.add("kill", Lang.getMessage("SNO_WAR.SB.FINISH.KILL", lang, new Lang.Args(Lang.Parameter.PLAYER, ""+ PlayerData.getPlayerDataList().get(PlayerData.triePlayerDataKills().firstKey())), new Lang.Args(Lang.Parameter.VAR, ""+PlayerData.getPlayerDataList().get(PlayerData.triePlayerDataKills().firstKey()).getKill())), 6);
        super.add("dead", Lang.getMessage("SNO_WAR.SB.FINISH.BONUS", lang, new Lang.Args(Lang.Parameter.PLAYER, ""+PlayerData.getPlayerDataList().get(PlayerData.triePlayerDataBonus().firstKey())), new Lang.Args(Lang.Parameter.VAR, ""+PlayerData.getPlayerDataList().get(PlayerData.triePlayerDataBonus().firstKey()).getBonus())), 5);
        super.add("point", Lang.getMessage("SNO_WAR.SB.FINISH.BALL", lang, new Lang.Args(Lang.Parameter.PLAYER, ""+PlayerData.getPlayerDataList().get(PlayerData.triePlayerDataBall().firstKey())), new Lang.Args(Lang.Parameter.VAR, ""+PlayerData.getPlayerDataList().get(PlayerData.triePlayerDataBall().firstKey()).getBallTouch())), 4);
        super.add("3", "", 3);
        super.add("2", Lang.getMessage("SNO_WAR.SB.SPACE", lang), 2);
        super.add("ip", Settings.getIp(), 1);
    }
}
