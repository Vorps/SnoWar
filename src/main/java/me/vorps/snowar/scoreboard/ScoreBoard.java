package me.vorps.snowar.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public abstract class ScoreBoard {
    private ScoreboardManager manager = Bukkit.getScoreboardManager();
    private Scoreboard board = manager.getNewScoreboard();
    private Objective o = board.registerNewObjective("Nom", "mort");
    private HashMap<String, Score> value = new HashMap<>();

    public ScoreBoard(DisplaySlot slot, String name){
        o.setDisplaySlot(slot);
        o.setDisplayName(name);
    }

    /**
     * Change name ScoreBoard
     * @param name String
     */
    public void changeName(String name){
        o.setDisplayName(name);
    }

    /**
     * Add objective
     * @param id String
     * @param value String
     * @param place int
     */
    public void add(String id, String value, int place){
        this.value.put(id, o.getScore(value));
        this.value.get(id).setScore(place);
    }

    /**
     * Remove objective
     * @param id String
     */
    public void remove(String id){
        o.getScoreboard().resetScores(this.value.get(id).getEntry());
    }

    /**
     * Create Team
     * @param name String
     * @param displayName String
     */
    public void createTeam(String name, String displayName){
        Team team;
        team = board.registerNewTeam(name);
        team.setPrefix(displayName);
        team.setNameTagVisibility(NameTagVisibility.ALWAYS);
        teamDisplayName.put(name, team);
    }

    /**
     * Update objective
     * @param id String
     * @param value String
     */
    public void updateValue(String id, String value){
        int place = this.value.get(id).getScore();
        o.getScoreboard().resetScores(this.value.get(id).getEntry());
        this.value.replace(id, o.getScore(value));
        this.value.get(id).setScore(place);
    }

    /**
     * Get Scoreboard
     * @return Scoreboard
     */
    public Scoreboard getScoreBoard(){
        return board;
    }

    /**
     * Add Player team
     * @param nameTeam String
     * @param player Player
     */
    public void addPlayerTeam(String nameTeam, Player player){
        teamDisplayName.get(nameTeam).addPlayer(player);
    }

    /**
     * Remove Player team
     * @param nameTeam String
     * @param player Player
     */
    public void removePlayerTeam(String nameTeam, Player player){
        teamDisplayName.get(nameTeam).removePlayer(player);
    }

    private static HashMap<String, Team> teamDisplayName = new HashMap<>();
}
