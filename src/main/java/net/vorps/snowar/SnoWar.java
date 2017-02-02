package net.vorps.snowar;

import lombok.Getter;
import net.vorps.api.databases.Database;
import net.vorps.api.listeners.ListenerManager;
import net.vorps.api.type.GameState;
import net.vorps.api.utils.Info;
import net.vorps.api.utils.WorldUtils;
import net.vorps.snowar.commands.CommandManager;
import net.vorps.snowar.listeners.*;
import net.vorps.snowar.objects.MapParameter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Project SnoWar Created by Vorps on 19/07/2016 at 22:52.
 */
public class SnoWar extends JavaPlugin {

    private static @Getter
    SnoWar instance;
    //private @Getter Updater updater;

    @Override
    public void onEnable() {
        SnoWar.instance = this;
        new ListenerManager(this, new PlayerJoin(), new PlayerQuit(), new FoodLevel(), new PlayerMove()
                , new DamageByEntityListener(), new DamageListener(), new DropListener(), new PlayerInteract()
                , new PlayerSneak(), new PlayerInteractEntities(), new ChatListener(), new PlayerInventoryClick()
                , new PlayerCommandPreprocess());
        new CommandManager();
        Data.loadVariable();
        GameState.setState(GameState.WAITING);
        Info.setInfo(true, false, MapParameter.getName(), true);
        //this.updater = new Updater(this);
    }

    @Override
    public void onDisable() {
        WorldUtils.initMap("save_world", "world");
        Bukkit.getScheduler().cancelAllTasks();
        GameState.setState(GameState.STOP);
        PlayerData.clear();
        Database.closeAllDataBases();
        Info.setInfo(false, false, MapParameter.getName(), false);
        //this.updater.disable();
    }

    @EventHandler
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        return CommandManager.onCommand(sender, label, args);
    }
}
