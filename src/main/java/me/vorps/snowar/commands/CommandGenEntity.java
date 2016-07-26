package me.vorps.snowar.commands;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.objects.MapParameter;
import me.vorps.snowar.utils.EntityManager;
import me.vorps.snowar.lang.Lang;
import me.vorps.snowar.utils.WorldUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class CommandGenEntity extends Commands{

    public CommandGenEntity(final CommandSender sender, final String args[]){
        super(sender, Command.ENTITY.getPermissions());
        if(args.length == 0 && sender.hasPermission(getPermission()) && sender instanceof Player){
            EntityManager.removeEntity();
            MapParameter.spawnStats();
            WorldUtils.initMap("world", "saveWorld");
            sender.sendMessage(Lang.getMessage("SNO_WAR.COMMAND.GEN_ENTITY", PlayerData.getPlayerData(sender.getName()).getLang()));
            super.setStateExec(true);
        }
        if(!isStateExec()){
            this.help();
        }
    }

    @Override
    protected void help(){
        if(super.getSender().hasPermission(getPermission()) && super.getSender() instanceof Player){
            super.getSender().sendMessage("§e✴------------------- §aHelp genShop§e -------------------✴");
            super.getSender().sendMessage(Lang.getMessage("SNO_WAR.COMMAND.GEN_ENTITY.HELP", PlayerData.getPlayerData(getSender().getName()).getLang()));
            super.getSender().sendMessage("§e✴--------------------------------------------------✴");
        }
    }
}
