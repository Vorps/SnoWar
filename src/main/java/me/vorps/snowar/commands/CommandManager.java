package me.vorps.snowar.commands;

import me.vorps.snowar.SnowWar;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;

/**
 * Project Hub Created by Vorps on 06/03/2016 at 07:57.
 */
public class CommandManager {

    public CommandManager(){
        SnowWar plugin = SnowWar.getInstance();
        plugin.getCommand(Commands.Command.RELOAD_SNOW_WAR.getCommand()).setTabCompleter(new CommandsAutoCompletion(Commands.Command.RELOAD_SNOW_WAR));
    }

    @EventHandler
    public static boolean onCommand(CommandSender sender, String label, String args[]){
        boolean state = false;
        if(label.equalsIgnoreCase(Commands.Command.RELOAD_SNOW_WAR.getCommand())){
            state = new CommandReload(sender, args).isStateExec();
        }
        return state;
    }
}
