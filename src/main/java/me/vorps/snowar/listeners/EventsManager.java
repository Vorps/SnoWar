package me.vorps.snowar.listeners;

import me.vorps.snowar.SnowWar;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class EventsManager {
	
	public EventsManager(){
        SnowWar plugin = SnowWar.getInstance();
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new PlayerJoin(), plugin);
		pluginManager.registerEvents(new PlayerQuit(), plugin);
        pluginManager.registerEvents(new FoodLevel(), plugin);
        pluginManager.registerEvents(new WeatherChange(), plugin);
        pluginManager.registerEvents(new PlayerMove(), plugin);
        pluginManager.registerEvents(new DamageByEntityListener(), plugin);
        pluginManager.registerEvents(new DamageListener(), plugin);
        pluginManager.registerEvents(new DropListener(), plugin);
        pluginManager.registerEvents(new PlayerInteract(), plugin);
        pluginManager.registerEvents(new PlayerSneak(), plugin);
        pluginManager.registerEvents(new PlayerInteractEntities(), plugin);
        pluginManager.registerEvents(new ChatListener(), plugin);
	}
}
