package me.vorps.snowar.listeners;

import me.vorps.snowar.SnowWar;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventsManager {
	
	public static void registerEvents(SnowWar plugin){
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new PlayerJoin(), plugin);
		pluginManager.registerEvents(new PlayerQuit(), plugin);
        pluginManager.registerEvents(new FoodLevel(), plugin);
	}
}
