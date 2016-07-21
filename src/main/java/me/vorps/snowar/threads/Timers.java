package me.vorps.snowar.threads;


import lombok.Getter;
import lombok.Setter;
import me.vorps.snowar.GameManager;
import me.vorps.snowar.GameState;
import me.vorps.snowar.Settings;

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
			//GameManager.stopGame();
			//new ThreadFinish(time);
			break;
		default:
			break;
		}
	}
}
