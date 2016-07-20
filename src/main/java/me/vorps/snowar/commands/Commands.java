package me.vorps.snowar.commands;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

/**
 * Project Hub Created by Vorps on 04/03/2016 at 16:25.
 */
public abstract class Commands {
    public enum Command{
        RELOAD_SNOW_WAR("reloadsw", "snowwar.reloadsw");

        private @Getter String command;
        private @Getter String permissions;

        Command(String command, String permissions){
            this.command = command;
            this.permissions = permissions;
        }
    }

    private @Getter @Setter boolean stateExec;
    private @Getter CommandSender sender;
    private @Getter String permission;

    protected abstract void help();

    public Commands(CommandSender sender, String permission){
        this.sender = sender;
        this.permission = permission;
    }
}
