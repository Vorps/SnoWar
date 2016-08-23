package me.vorps.snowar.commands;

import me.vorps.snowar.Data;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.snowar.game.GameState;
import me.vorps.snowar.objects.Parameter;
import me.vorps.snowar.threads.ThreadInStart;
import me.vorps.snowar.threads.Timers;
import me.vorps.syluriapi.commands.*;
import me.vorps.syluriapi.lang.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Project SnoWar Created by Vorps on 07/08/2016 at 15:24.
 */
public class CommandStart extends me.vorps.syluriapi.commands.Commands{

    public CommandStart(final CommandSender sender, final String args[]) {
        super(sender, Commands.ENTITY.getPermissions());
        if(args.length == 0){
            if(GameState.isState(GameState.INSTART) || GameState.isState(GameState.WAITING)){
                if(PlayerData.getPlayerInGame() >= Data.getNbPlayerMin()){
                    GameState.setState(GameState.INGAME);
                    if(GameState.isState(GameState.INSTART)) Bukkit.getServer().getScheduler().cancelTask(ThreadInStart.getTask());
                    Timers.run(Parameter.getTimeGame());
                    if(!PlayerData.isPlayerData(sender.getName())) sender.sendMessage(Lang.getMessage("SNO_WAR.COMMAND.START", Settings.getConsoleLang(), new Lang.Args(Lang.Parameter.PLAYER, sender.getName())));
                    PlayerData.broadCast("SNO_WAR.COMMAND.START", new Lang.Args(Lang.Parameter.PLAYER, sender.getName()));
                } else {
                    sender.sendMessage(Lang.getMessage("SNO_WAR.COMMAND.START.ERROR.2", sender instanceof Player ? PlayerData.getPlayerData(sender.getName()).getLang() : Settings.getConsoleLang()));
                }
            } else {
                sender.sendMessage(Lang.getMessage("SNO_WAR.COMMAND.START.ERROR.1", sender instanceof Player ? PlayerData.getPlayerData(sender.getName()).getLang() : Settings.getConsoleLang()));
            }
            super.setStateExec(true);
        }
        super.finalize();
    }

    @Override
    protected void help(){
        if(super.getSender().hasPermission(getPermission())){
            super.getSender().sendMessage("§e✴------------------- §aHelp start§e -------------------✴");
            super.getSender().sendMessage(Lang.getMessage("SNO_WAR.COMMAND.START.HELP", PlayerData.getPlayerData(getSender().getName()).getLang()));
            super.getSender().sendMessage("§e✴--------------------------------------------------✴");
        }
    }
}
