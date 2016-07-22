package me.vorps.snowar.objects;

import lombok.Getter;
import me.vorps.snowar.Exceptions.SqlException;
import me.vorps.snowar.databases.Database;
import me.vorps.snowar.databases.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Stats {

    public static void updateStats(UUID uuid, int... values){
        try{
            ResultSet result = Database.SNOWAR.getDatabase().getData("stats", "s_player = '"+uuid.toString()+"'");
            if(!result.next()){
                Database.SNOWAR.getDatabase().insertTable("stats", uuid.toString(), 0, 0, 0, 0, 0, 0, 0, 0);
            }
            Stats stats = new Stats(uuid);
            Database.SNOWAR.getDatabase().updateTable("stats", "stats_player = '"+uuid.toString()+"'",
                    new DatabaseManager.Values("stats_kill", stats.kill+values[0]),
                    new DatabaseManager.Values("stats_death", stats.dead+values[1]),
                    new DatabaseManager.Values("stats_bonus", stats.bonus+values[2]),
                    new DatabaseManager.Values("stats_ball_shot", stats.ballShot+values[3]),
                    new DatabaseManager.Values("stats_ball_touch", stats.ballTouch+values[4]),
                    new DatabaseManager.Values("stats_time", stats.time+values[5]),
                    new DatabaseManager.Values("stats_victory", stats.victory+values[6]),
                    new DatabaseManager.Values("stats_defeat", stats.defeat+values[7]));
        }catch(SQLException e){
            //
        }catch (SqlException e){
            e.printStackTrace();
        }
    }

	private @Getter int kill;
	private @Getter int dead;
	private @Getter int bonus;
    private @Getter int ballShot;
    private @Getter int ballTouch;
    private @Getter int time;
	private @Getter int victory;
	private @Getter int defeat;

	public Stats(UUID uuid){
		try{
			ResultSet result = Database.SNOWAR.getDatabase().getData("stats", "stats_player = '"+uuid.toString()+"'");
			if(result.next()){
				kill = Database.SNOWAR.getDatabase().getInt(result, 2);
				dead = Database.SNOWAR.getDatabase().getInt(result, 3);
                bonus = Database.SNOWAR.getDatabase().getInt(result, 4);
                ballShot = Database.SNOWAR.getDatabase().getInt(result, 5);
                ballTouch = Database.SNOWAR.getDatabase().getInt(result, 6);
				time = Database.SNOWAR.getDatabase().getInt(result, 7);
                victory = Database.SNOWAR.getDatabase().getInt(result, 8);
				defeat = Database.SNOWAR.getDatabase().getInt(result, 9);
			}
		}catch(SQLException e){
			//
		} catch (SqlException e){
            e.printStackTrace();
        }
	}
}
