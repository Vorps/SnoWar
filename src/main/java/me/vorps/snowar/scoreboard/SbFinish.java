package me.vorps.snowar.scoreboard;

import me.vorps.snowar.utils.Lang;
import org.bukkit.scoreboard.DisplaySlot;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project RushVolcano Created by Vorps on 25/04/2016 at 17:08.
 */
public class SbFinish extends ScoreBoard{

    public SbFinish(String lang){
        super(DisplaySlot.SIDEBAR, Lang.getMessage("RUSH_VOLCANO.SB.NAME", lang));
        super.add("15", Lang.getMessage("RUSH_VOLCANO.SB.STATS", lang), 14);
        super.add("14", " ", 13);
        super.add("time", Lang.getMessage("RUSH_VOLCANO.SB.TIME", lang, new Lang.Args(Lang.Parameter.TIME, new SimpleDateFormat("mm:ss").format(new Date(Settings.getTimeFinish()*1000)))), 12);
        super.add("16", "  ", 11);
        String[] winner = new String[3];
        int i = 0;
        for(Team team : Team.trieTeamPoint().values()){
            winner[i++] = Lang.getMessage("RUSH_VOLCANO.SB.TEAM", lang, new Lang.Args[] {new Lang.Args(Lang.Parameter.TEAM, team.showName(lang)), new Lang.Args(Lang.Parameter.POINT, ""+(team.getNoyau()*100/MapParameter.getNumberNoyau())), new Lang.Args(Lang.Parameter.NBR_PLAYER, ""+team.nbrPlayer())});
            if(i == 3){
                break;
            }
        }
        if(Team.nbrTeam() >= 2) {
            super.add("first", Lang.getMessage("RUSH_VOLCANO.SB.FINISH.TEAM", lang, new Lang.Args[] {new Lang.Args(Lang.Parameter.VAR, ""+1), new Lang.Args(Lang.Parameter.TEAM, winner[0])}), 10);
        }
        if(Team.nbrTeam() >= 3) {
            super.add("first", Lang.getMessage("RUSH_VOLCANO.SB.FINISH.TEAM", lang, new Lang.Args[] {new Lang.Args(Lang.Parameter.VAR, ""+2), new Lang.Args(Lang.Parameter.TEAM, winner[1])}), 9);
        }
        if(Team.nbrTeam() > 3){
            super.add("first", Lang.getMessage("RUSH_VOLCANO.SB.FINISH.TEAM", lang, new Lang.Args[] {new Lang.Args(Lang.Parameter.VAR, ""+3), new Lang.Args(Lang.Parameter.TEAM, winner[2])}), 8);
        }
        super.add("14", "  ", 7);
        super.add("kill", Lang.getMessage("RUSH_VOLCANO.SB.FINISH.KILL", lang, new Lang.Args[] {new Lang.Args(Lang.Parameter.PLAYER, ""+ PlayerData.getPlayersList().get(PlayerData.triePlayerDataKill().firstKey())), new Lang.Args(Lang.Parameter.KILL, ""+PlayerData.getPlayersList().get(PlayerData.triePlayerDataKill().firstKey()).getKills())}), 6);
        super.add("dead", Lang.getMessage("RUSH_VOLCANO.SB.FINISH.DEAD", lang, new Lang.Args[] {new Lang.Args(Lang.Parameter.PLAYER, ""+PlayerData.getPlayersList().get(PlayerData.triePlayerDataDead().firstKey())), new Lang.Args(Lang.Parameter.DEAD, ""+PlayerData.getPlayersList().get(PlayerData.triePlayerDataDead().firstKey()).getDead())}), 5);
        super.add("point", Lang.getMessage("RUSH_VOLCANO.SB.FINISH.POINT", lang, new Lang.Args[] {new Lang.Args(Lang.Parameter.PLAYER, ""+PlayerData.getPlayersList().get(PlayerData.triePlayerDataPoint().firstKey())), new Lang.Args(Lang.Parameter.POINT, ""+PlayerData.getPlayersList().get(PlayerData.triePlayerDataPoint().firstKey()).getWool())}), 4);
        super.add("3", "", 3);
        super.add("2", Lang.getMessage("RUSH_VOLCANO.SB.SPACE", lang), 2);
        super.add("ip", Settings.getIpServer(), 1);
        //AnimationLabel.addAnimation(new AnimationLabel.Label("ip", "play.fortycube.fr", "§e", AnimationLabel.Type.OBJECTIVE, super, new AnimationLabel.Parameter[] {new AnimationLabel.Parameter("§6", 0), new AnimationLabel.Parameter("§6", 0), new AnimationLabel.Parameter("§6", 0)}, 200));
    }
}
