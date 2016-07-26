package me.vorps.snowar;

import lombok.Getter;
import me.vorps.snowar.commands.CommandManager;
import me.vorps.snowar.game.GameState;
import me.vorps.snowar.listeners.EventsManager;
import me.vorps.snowar.utils.WorldUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Project SnoWar Created by Vorps on 19/07/2016 at 22:52.
 */
public class SnowWar extends JavaPlugin{

    private static @Getter SnowWar instance;

    @Override
    public void onEnable(){
        SnowWar.instance = this;
        new EventsManager();
        new CommandManager();
        Data.loadVariable();
        GameState.setState(GameState.WAITING);
    }

    @Override
    public void onDisable(){
        WorldUtils.initMap("saveWorld", "world");
        Bukkit.getScheduler().cancelAllTasks();
        GameState.setState(GameState.STOP);
        PlayerData.clear();
    }

    @EventHandler
    public boolean onCommand(CommandSender sender , Command command, String label, String args[]){
        return CommandManager.onCommand(sender, label, args);
    }
}
