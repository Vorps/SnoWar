package me.vorps.snowar.listeners;

import me.vorps.snowar.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevel implements Listener {

	@EventHandler
	public void onFoodLevel(FoodLevelChangeEvent e){
		if(!GameState.isState(GameState.INGAME)){
			e.setCancelled(true);
		}
	}
}
