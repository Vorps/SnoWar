package me.vorps.snowar;

import lombok.Getter;
import me.vorps.snowar.commands.CommandManager;
import me.vorps.snowar.listeners.EventsManager;
import me.vorps.snowar.objects.MapParameter;
import me.vorps.syluriapi.databases.Database;
import me.vorps.syluriapi.type.GameState;
import me.vorps.syluriapi.utils.Info;
import me.vorps.syluriapi.utils.WorldUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Project SnoWar Created by Vorps on 19/07/2016 at 22:52.
 */
public class SnowWar extends JavaPlugin {

    private static @Getter SnowWar instance;

    @Override
    public void onEnable() {
        SnowWar.instance = this;
        new EventsManager();
        new CommandManager();
        Data.loadVariable();
        GameState.setState(GameState.WAITING);
        Info.setInfo(true, false, MapParameter.getName(), true);
    }

    @Override
    public void onDisable() {
        WorldUtils.initMap("save_world", "world");
        Bukkit.getScheduler().cancelAllTasks();
        GameState.setState(GameState.STOP);
        PlayerData.clear();
        Database.closeAllDataBases();
        Info.setInfo(false, false, MapParameter.getName(), false);
    }

    @EventHandler
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        return CommandManager.onCommand(sender, label, args);
    }
}
