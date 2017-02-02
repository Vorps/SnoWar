package net.vorps.snowar.commands;

import net.vorps.api.lang.Lang;
import net.vorps.api.type.GameState;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.Settings;
import net.vorps.snowar.game.Victory;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
/**
 * Project SnoWar Created by Vorps on 07/08/2016 at 15:24.
 */
public class CommandStop extends net.vorps.api.commands.Commands{


    public CommandStop(final CommandSender sender, final String args[]){
        super(sender, Commands.STOP.getPermissions());
        if(args.length == 0){
            if(GameState.isState(GameState.INGAME)){
                Bukkit.getServer().getScheduler().cancelAllTasks();
                Victory.onVictory(1);
                if(!PlayerData.isPlayerData(sender.getName())) sender.sendMessage(Lang.getMessage("SNO_WAR.COMMAND.STOP", Settings.getConsoleLang(), new Lang.Args(Lang.Parameter.PLAYER, sender.getName())));
                PlayerData.broadCast("SNO_WAR.COMMAND.STOP", new Lang.Args(Lang.Parameter.PLAYER, sender.getName()));
            } else sender.sendMessage(Lang.getMessage("SNO_WAR.COMMAND.STOP.ERROR", sender instanceof Player ? PlayerData.getPlayerData(sender.getName()).getLang() : Settings.getConsoleLang()));
            super.setStateExec(true);
        }
        super.onDisable();
    }

    @Override
    public void help(){
        super.getSender().sendMessage("§e✴------------------- §aHelp stop§e -------------------✴");
        super.getSender().sendMessage(Lang.getMessage("SNO_WAR.COMMAND.STOP.HELP", PlayerData.getPlayerData(getSender().getName()).getLang()));
        super.getSender().sendMessage("§e✴--------------------------------------------------✴");
    }
}
