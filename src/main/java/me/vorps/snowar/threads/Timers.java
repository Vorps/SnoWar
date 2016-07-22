package me.vorps.snowar.threads;


import lombok.Getter;
import lombok.Setter;
import me.vorps.snowar.GameManager;
import me.vorps.snowar.GameState;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.snowar.utils.Lang;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class Timers{
	
	private @Getter @Setter static int time = Settings.getTimeStart();

	public static void run(int time){
        Timers.time = time;
		switch (GameState.getState()) {
		case INSTART:
			new ThreadInStart();
			break;
		case INGAME:
            GameManager.startGame();
			new ThreadGame();
			break;
		case FINISH:
			GameManager.stopGame();
			new ThreadFinish(time);
			break;
		default:
			break;
		}
	}

    public static void updateTime(){
        PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> {
            Date date = new Date(Timers.getTime()*1000);
            date.setHours(date.getHours()-1);
            SimpleDateFormat simpleDateFormat;
            if(Timers.getTime() > 3600){
                simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            } else {
                simpleDateFormat = new SimpleDateFormat("mm:ss");
            }
            playerData.getScoreboard().updateValue("time", Lang.getMessage("SNO_WAR.SB.TIME",  playerData.getLang(), new Lang.Args(Lang.Parameter.TIME, simpleDateFormat.format(date))));
        });
    }
}
