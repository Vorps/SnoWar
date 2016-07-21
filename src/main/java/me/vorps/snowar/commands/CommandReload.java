package me.vorps.snowar.commands;

import me.vorps.snowar.Data;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.snowar.utils.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class CommandReload extends Commands{

    private String lang;

    public CommandReload(CommandSender sender, String args[]){
        super(sender, Command.RELOAD_SNOW_WAR.getPermissions());
        this.lang = sender instanceof Player ? PlayerData.getPlayerData(sender.getName()).getLang() : Settings.getConsoleLang();
        if(args.length == 0 && sender.hasPermission(getPermission())){
            Data.loadVariable();
            sender.sendMessage(Lang.getMessage("SNO_WAR.COMMAND.RELOAD", this.lang));
            setStateExec(true);
        }
        if(!isStateExec()){
            help();
        }
    }
    @Override
    protected void help(){
        if(getSender().hasPermission(getPermission())){
            getSender().sendMessage("§e✴------------------- §aHelp reload§e --------------------✴");
            getSender().sendMessage(Lang.getMessage("SNO_WAR.COMMAND.RELOAD.HELP", this.lang));
            getSender().sendMessage("§e✴--------------------------------------------------✴");
        }
    }
}
