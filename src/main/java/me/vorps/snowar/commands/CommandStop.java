package me.vorps.snowar.commands;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.snowar.game.Victory;
import me.vorps.syluriapi.lang.Lang;
import me.vorps.syluriapi.type.GameState;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
/**
 * Project SnoWar Created by Vorps on 07/08/2016 at 15:24.
 */
public class CommandStop extends me.vorps.syluriapi.commands.Commands{


    public CommandStop(final CommandSender sender, final String args[]){
        super(sender, Commands.STOP.getPermissions());
        if(args.length == 0){
            if(GameState.isState(GameState.INGAME)){
                Bukkit.getServer().getScheduler().cancelAllTasks();
                Victory.onVictory(1);
                if(!PlayerData.isPlayerData(sender.getName())) sender.sendMessage(Lang.getMessage("SNO_WAR.COMMAND.STOP", Settings.getConsoleLang(), new Lang.Args(Lang.Parameter.PLAYER, sender.getName())));
                PlayerData.broadCast("SNO_WAR.COMMAND.STOP", new Lang.Args(Lang.Parameter.PLAYER, sender.getName()));
            } else {
                sender.sendMessage(Lang.getMessage("SNO_WAR.COMMAND.STOP.ERROR", sender instanceof Player ? PlayerData.getPlayerData(sender.getName()).getLang() : Settings.getConsoleLang()));
            }
            super.setStateExec(true);
        }
        super.finalize();
    }

    @Override
    public void help(){
        if(super.getSender().hasPermission(getPermission())){
            super.getSender().sendMessage("§e✴------------------- §aHelp stop§e -------------------✴");
            super.getSender().sendMessage(Lang.getMessage("SNO_WAR.COMMAND.STOP.HELP", PlayerData.getPlayerData(getSender().getName()).getLang()));
            super.getSender().sendMessage("§e✴--------------------------------------------------✴");
        }
    }
}
