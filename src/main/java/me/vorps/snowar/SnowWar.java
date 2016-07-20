package me.vorps.snowar;

import lombok.Getter;
import me.vorps.snowar.listeners.EventsManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Project SnoWar Created by Vorps on 19/07/2016 at 22:52.
 */
public class SnowWar extends JavaPlugin{

    private static @Getter SnowWar instance;

    @Override
    public void onEnable(){
        instance = this;
        EventsManager.registerEvents(this);
        Data.loadVariable();
        GameState.setState(GameState.WAITING);
    }

    @Override
    public void onDisable(){
        GameState.setState(GameState.STOP);
        PlayerData.clear();
    }
}
