package me.vorps.snowar.threads;


import lombok.Getter;
import lombok.Setter;
import me.vorps.snowar.game.GameManager;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.snowar.scoreboard.SbLobby;
import me.vorps.syluriapi.lang.Lang;
import me.vorps.syluriapi.type.GameState;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class Timers{

    private static final String[] COLOR;
    private @Getter @Setter static int time;


    static {
        COLOR = new String[] {"§6", "§c", "§4"};
        Timers.time = Settings.getTimeStart();
    }

	public static void run(final int time){
        Timers.time = time;
		switch (GameState.getState()) {
		case INSTART:
            PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> {
                playerData.getScoreboard().remove("waiting");
                playerData.getScoreboard().add("time", Lang.getMessage(SbLobby.getKey(time), playerData.getLang(), new Lang.Args(Lang.Parameter.TIME, ""+Timers.time)), 5);
            });
			new ThreadInStart();
			break;
		case INGAME:
            GameManager.startGame();
			new ThreadGame();
			break;
		case FINISH:
			GameManager.stopGame();
			new ThreadFinish();
			break;
		default:
			break;
		}
	}

    public static void removeTime(){
        Timers.time--;
    }

    public static void updateTime(){
        PlayerData.getPlayerDataList().values().forEach((PlayerData playerData) -> {
            Date date = new Date(Timers.getTime()*1000);
            date.setHours(date.getHours()-1);
            playerData.getScoreboard().updateValue("time", Lang.getMessage("SNO_WAR.SB.TIME",  playerData.getLang(), new Lang.Args(Lang.Parameter.TIME, (Timers.getTime() > 3600 ? new SimpleDateFormat("HH:mm:ss") : new SimpleDateFormat("mm:ss")).format(date))));
        });
    }

    public static String color(){
        return Timers.time > 3 ? "§a" : Timers.COLOR[Timers.time-1];
    }
}
