package me.vorps.snowar.commands;

import me.vorps.snowar.Data;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.syluriapi.lang.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class CommandReload extends me.vorps.syluriapi.commands.Commands{

    public CommandReload(final CommandSender sender, final String args[]){
        super(sender, Commands.RELOAD_SNOW_WAR.getPermissions());
        if(args.length == 0 && sender.hasPermission(getPermission())){
            Data.loadVariable();
            sender.sendMessage(Lang.getMessage("SNO_WAR.COMMAND.RELOAD", sender instanceof Player ? PlayerData.getPlayerData(sender.getName()).getLang() : Settings.getConsoleLang()));
            super.setStateExec(true);
        }
        super.finalize();
    }
    @Override
    protected void help(){
        if(super.getSender().hasPermission(getPermission())){
            super.getSender().sendMessage("§e✴------------------- §aHelp reload§e --------------------✴");
            super.getSender().sendMessage(Lang.getMessage("SNO_WAR.COMMAND.RELOAD.HELP", super.getSender() instanceof Player ? PlayerData.getPlayerData(super.getSender().getName()).getLang() : Settings.getConsoleLang()));
            super.getSender().sendMessage("§e✴--------------------------------------------------✴");
        }
    }
}
