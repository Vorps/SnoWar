package net.vorps.snowar.objects;

import lombok.Getter;
import net.vorps.api.Exceptions.SqlException;
import net.vorps.api.databases.Database;
import net.vorps.api.databases.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Stats {

	private @Getter int kill;
	private @Getter int dead;
	private @Getter int bonus;
    private @Getter int ballShot;
    private @Getter int ballTouch;
    private @Getter int time;
	private @Getter int victory;
	private @Getter int defeat;

    /**
     * Load statistic player
     * @param uuid UUID
     */
	public Stats(UUID uuid){
		try{
			ResultSet result = Database.SNOWAR.getDatabase().getData("stats", "stats_player = '"+uuid.toString()+"'");
			if(result.next()){
				this.kill = Database.SNOWAR.getDatabase().getInt(result, 2);
                this.dead = Database.SNOWAR.getDatabase().getInt(result, 3);
                this.bonus = Database.SNOWAR.getDatabase().getInt(result, 4);
                this.ballShot = Database.SNOWAR.getDatabase().getInt(result, 5);
                this.ballTouch = Database.SNOWAR.getDatabase().getInt(result, 6);
                this.time = Database.SNOWAR.getDatabase().getInt(result, 7);
                this.victory = Database.SNOWAR.getDatabase().getInt(result, 8);
                this.defeat = Database.SNOWAR.getDatabase().getInt(result, 9);
			}
		}catch(SQLException e){
			//
		} catch (SqlException e){
            e.printStackTrace();
        }
	}

    /**
     * Update statistic player
     * @param uuid UUID
     * @param values int
     */
    public static void updateStats(UUID uuid, int... values){
        try{
            Database.RUSH_VOLCANO.getDatabase().sendRequest("INSERT INTO stats(stats_player,stats_kill,stats_death,stats_bonus,stats_ball_shot,stats_ball_touch,stats_time,stats_victory,stats_defeat) VALUES('"+uuid+"','"+values[0]+"','"+values[1]+"','"+values[2]+"','"+values[3]+"','"+values[4]+"','"+values[5]+"','"+values[6]+"','"+values[7]+"') ON DUPLICATE KEY UPDATE stats_kill = stats_kill + VALUES(stats_kill), stats_death = stats_death + VALUES(stats_death), stats_bonus = stats_bonus + VALUES(stats_bonus), stats_ball_shot = stats_ball_shot + VALUES(stats_ball_shot), stats_ball_touch = stats_ball_touch + VALUES(stats_ball_touch), stats_time = stats_time + VALUES(stats_time), stats_victory = stats_victory + VALUES(stats_victory), stats_defeat = stats_defeat + VALUES(stats_defeat)");
        } catch (SqlException e){
            e.printStackTrace();
        }
    }
}
