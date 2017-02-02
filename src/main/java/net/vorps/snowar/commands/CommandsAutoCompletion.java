package net.vorps.snowar.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class CommandsAutoCompletion implements TabCompleter {
    private Commands command;

    public CommandsAutoCompletion(Commands command){
        this.command = command;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args){
        List<String> matches = new ArrayList<>();
        switch (command){
            case RELOAD_SNOW_WAR:
                break;
            case ENTITY:
                break;
            case START:
                break;
            case STOP:
                break;
            default:
                break;
        }
        return matches;
    }
}
