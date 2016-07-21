package me.vorps.snowar.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class FoodLevel implements Listener {

	@EventHandler
	public void onFoodLevel(FoodLevelChangeEvent e){
        e.setCancelled(true);
	}
}
