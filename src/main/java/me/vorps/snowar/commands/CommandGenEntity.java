package me.vorps.snowar.commands;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.objects.MapParameter;
import me.vorps.snowar.utils.EntityManager;
import me.vorps.snowar.utils.Lang;
import me.vorps.snowar.utils.WorldUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class CommandGenEntity extends Commands{

    public CommandGenEntity(CommandSender sender, String args[]){
        super(sender, Command.ENTITY.getPermissions());
        if(args.length == 0 && sender.hasPermission(getPermission()) && sender instanceof Player){
            EntityManager.removeEntity();
            MapParameter.spawnStats();
            WorldUtils.initMap("world", "saveWorld");
            sender.sendMessage(Lang.getMessage("SNO_WAR.COMMAND.GEN_ENTITY", PlayerData.getPlayerData(sender.getName()).getLang()));
            setStateExec(true);
        }
        if(!isStateExec()){
            help();
        }
    }

    @Override
    protected void help(){
        if(getSender().hasPermission(getPermission()) && getSender() instanceof Player){
            getSender().sendMessage("§e✴------------------- §aHelp genShop§e -------------------✴");
            getSender().sendMessage(Lang.getMessage("SNO_WAR.COMMAND.GEN_ENTITY.HELP", PlayerData.getPlayerData(getSender().getName()).getLang()));
            getSender().sendMessage("§e✴--------------------------------------------------✴");
        }
    }
}
