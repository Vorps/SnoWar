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

    private Scoreboard scoreboard;
    private Objective objective;
    private HashMap<String, Score> value;

    /**
     * Constructor abstract
     * @param slot DisplaySlot
     * @param name String
     */
    public ScoreBoard(DisplaySlot slot, String name){
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective("Nom", "mort");
        this.value = new HashMap<>();
        this.objective.setDisplaySlot(slot);
        this.objective.setDisplayName(name);
    }

    /**
     * Change name ScoreBoard
     * @param name String
     */
    public void changeName(String name){
        this.objective.setDisplayName(name);
    }

    /**
     * Add objective
     * @param id String
     * @param value String
     * @param place int
     */
    public void add(String id, String value, int place){
        this.value.put(id, this.objective.getScore(value));
        this.value.get(id).setScore(place);
    }

    /**
     * Remove objective
     * @param id String
     */
    public void remove(String id){
        this.objective.getScoreboard().resetScores(this.value.get(id).getEntry());
    }

    /**
     * Create Team
     * @param name String
     * @param displayName String
     */
    public void createTeam(String name, String displayName){
        Team team;
        team = this.scoreboard.registerNewTeam(name);
        team.setPrefix(displayName);
        team.setNameTagVisibility(NameTagVisibility.ALWAYS);
        ScoreBoard.teamDisplayName.put(name, team);
    }

    /**
     * Update objective
     * @param id String
     * @param value String
     */
    public void updateValue(String id, String value){
        int place = this.value.get(id).getScore();
        this.remove(id);
        this.value.replace(id, this.objective.getScore(value));
        this.value.get(id).setScore(place);
    }

    /**
     * Get Scoreboard
     * @return Scoreboard
     */
    public Scoreboard getScoreBoard(){
        return this.scoreboard;
    }

    /**
     * Add Player team
     * @param nameTeam String
     * @param player Player
     */
    public void addPlayerTeam(String nameTeam, Player player){
        ScoreBoard.teamDisplayName.get(nameTeam).addPlayer(player);
    }

    /**
     * Remove Player team
     * @param nameTeam String
     * @param player Player
     */
    public void removePlayerTeam(String nameTeam, Player player){
        ScoreBoard.teamDisplayName.get(nameTeam).removePlayer(player);
    }

    private static HashMap<String, Team> teamDisplayName;

    static {
        ScoreBoard.teamDisplayName = new HashMap<>();
    }
}
