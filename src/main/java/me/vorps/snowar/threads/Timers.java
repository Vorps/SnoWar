package me.vorps.snowar.threads;


import lombok.Getter;
import lombok.Setter;
import me.vorps.snowar.GameState;

public class Timers{
	
	private @Getter @Setter static int time;

	public static void run(int time){
        Timers.time = time;
		switch (GameState.getState()) {
		case INSTART:
			new ThreadInStart(time);
			break;
		case INGAME:
			//threadGame = new ThreadGame(time);
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
