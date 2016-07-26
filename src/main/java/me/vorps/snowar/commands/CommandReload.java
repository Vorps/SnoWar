package me.vorps.snowar.commands;

import me.vorps.snowar.Data;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.snowar.lang.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class CommandReload extends Commands{

    private String lang;

    public CommandReload(final CommandSender sender, final String args[]){
        super(sender, Command.RELOAD_SNOW_WAR.getPermissions());
        this.lang = sender instanceof Player ? PlayerData.getPlayerData(sender.getName()).getLang() : Settings.getConsoleLang();
        if(args.length == 0 && sender.hasPermission(getPermission())){
            Data.loadVariable();
            sender.sendMessage(Lang.getMessage("SNO_WAR.COMMAND.RELOAD", this.lang));
            super.setStateExec(true);
        }
        if(!isStateExec()){
            this.help();
        }
    }
    @Override
    protected void help(){
        if(super.getSender().hasPermission(getPermission())){
            super.getSender().sendMessage("§e✴------------------- §aHelp reload§e --------------------✴");
            super.getSender().sendMessage(Lang.getMessage("SNO_WAR.COMMAND.RELOAD.HELP", this.lang));
            super.getSender().sendMessage("§e✴--------------------------------------------------✴");
        }
    }
}
