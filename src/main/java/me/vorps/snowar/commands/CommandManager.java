package me.vorps.snowar.commands;

import me.vorps.snowar.SnowWar;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class CommandManager {

    public CommandManager(){
        SnowWar plugin = SnowWar.getInstance();
        plugin.getCommand(Commands.Command.RELOAD_SNOW_WAR.getCommand()).setTabCompleter(new CommandsAutoCompletion(Commands.Command.RELOAD_SNOW_WAR));
        plugin.getCommand(Commands.Command.ENTITY.getCommand()).setTabCompleter(new CommandsAutoCompletion(Commands.Command.ENTITY));
    }

    @EventHandler
    public static boolean onCommand(final CommandSender sender, final String label, final String args[]){
        boolean state = false;
        if(label.equalsIgnoreCase(Commands.Command.RELOAD_SNOW_WAR.getCommand())){
            state = new CommandReload(sender, args).isStateExec();
        }
        if(label.equalsIgnoreCase(Commands.Command.ENTITY.getCommand())){
            state = new CommandGenEntity(sender, args).isStateExec();
        }
        return state;
    }
}
