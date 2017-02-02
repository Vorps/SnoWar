package net.vorps.snowar.cooldowns;

import net.vorps.snowar.PlayerData;
import net.vorps.snowar.Settings;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class CoolDownsLastDamage extends Thread{
	
	private PlayerData playerData;

    /**
     * Define the last player damager
     * @param playerData PlayerData
     */
	public CoolDownsLastDamage(PlayerData playerData) {
		this.playerData = playerData;
	}

    @Override
	public void run(){
		try{
			Thread.sleep(Settings.getCoolDownLastDamage());
		} catch(InterruptedException e){
			e.printStackTrace();
		}
		this.playerData.setPlayerLastDamage(null);
	}
}
