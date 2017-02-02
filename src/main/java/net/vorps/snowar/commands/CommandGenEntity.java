package net.vorps.snowar.commands;

import net.vorps.api.lang.Lang;
import net.vorps.api.utils.EntityManager;
import net.vorps.api.utils.WorldUtils;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.objects.MapParameter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class CommandGenEntity extends net.vorps.api.commands.Commands{

    public CommandGenEntity(final CommandSender sender, final String args[]){
        super(sender, Commands.ENTITY.getPermissions());
        if(args.length == 0 && sender.hasPermission(getPermission()) && sender instanceof Player){
            EntityManager.removeEntity();
            MapParameter.spawnStats();
            WorldUtils.initMap("world", "save_world");
            sender.sendMessage(Lang.getMessage("SNO_WAR.COMMAND.GEN_ENTITY", PlayerData.getPlayerData(sender.getName()).getLang()));
            super.setStateExec(true);
        }
        super.onDisable();
    }

    @Override
    protected void help(){
        super.getSender().sendMessage("§e✴------------------- §aHelp genShop§e -------------------✴");
        super.getSender().sendMessage(Lang.getMessage("SNO_WAR.COMMAND.GEN_ENTITY.HELP", PlayerData.getPlayerData(getSender().getName()).getLang()));
        super.getSender().sendMessage("§e✴--------------------------------------------------✴");
    }
}
