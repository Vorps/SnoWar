package me.vorps.snowar.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

/**
 * Project Hub Created by Vorps on 14/03/2016 at 05:19.
 */
public class CommandsAutoCompletion implements TabCompleter {
    private Commands.Command command;

    public CommandsAutoCompletion(Commands.Command command){
        this.command = command;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args){
        List<String> matches = new ArrayList<>();
        switch (command){
            case RELOAD_SNOW_WAR:
                break;
            default:
                break;
        }
        return matches;
    }
}
