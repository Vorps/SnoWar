package me.vorps.snowar.cooldowns;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;

public class CoolDownsLastDamage extends Thread{
	
	private PlayerData playerData;
	
	public CoolDownsLastDamage(PlayerData playerData) {
		this.playerData = playerData;
	}
	
	public void run(){
		try{
			Thread.sleep(Settings.getCoolDownLastDamage());
		} catch(InterruptedException e){
			e.printStackTrace();
		}
		playerData.setPlayerLastDamage(null);
	}
}
