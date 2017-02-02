package net.vorps.snowar.commands;

import net.vorps.snowar.SnoWar;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class CommandManager {

    public CommandManager(){
        for(Commands commands : Commands.values()) SnoWar.getInstance().getCommand(commands.getCommand()).setTabCompleter(new CommandsAutoCompletion(commands));
    }

    @EventHandler
    public static boolean onCommand(final CommandSender sender, final String label, final String args[]){
        boolean state = false;
        if(label.equalsIgnoreCase(Commands.RELOAD_SNOW_WAR.getCommand())) state = new CommandReload(sender, args).isStateExec();
        if(label.equalsIgnoreCase(Commands.ENTITY.getCommand())) state = new CommandGenEntity(sender, args).isStateExec();
        if(label.equalsIgnoreCase(Commands.START.getCommand())) state = new CommandStart(sender, args).isStateExec();
        if(label.equalsIgnoreCase(Commands.STOP.getCommand())) state = new CommandStop(sender, args).isStateExec();
        return state;
    }
}
