package net.vorps.snowar.commands;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public enum Commands{
    RELOAD_SNOW_WAR("reloadsw", "snowwar.reloadsw"),
    ENTITY("genentity", "snowar.genentity"),
    START("startgame", "snowar.start"),
    STOP("stopgame", "snowar.stop");

    private @Getter String command;
    private @Getter String permissions;

    Commands(final String command, final String permissions){
        this.command = command;
        this.permissions = permissions;
    }
}

