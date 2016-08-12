package me.vorps.snowar.commands;

import me.vorps.snowar.Data;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.Settings;
import me.vorps.snowar.game.GameState;
import me.vorps.snowar.lang.Lang;
import me.vorps.snowar.objects.Parameter;
import me.vorps.snowar.threads.Timers;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Project SnoWar Created by Vorps on 07/08/2016 at 15:24.
 */
public class CommandStart extends Commands{

    public CommandStart(final CommandSender sender, final String args[]) {
        super(sender, Command.ENTITY.getPermissions());
        if(args.length == 0){
            if(PlayerData.getPlayerInGame() >= Data.getNbPlayerMin()){
                GameState.setState(GameState.INGAME);
                Timers.run(Parameter.getTimeGame());
                if(!PlayerData.isPlayerData(sender.getName())) sender.sendMessage(Lang.getMessage("SNO_WAR.COMMAND.START", Settings.getConsoleLang(), new Lang.Args(Lang.Parameter.PLAYER, sender.getName())));
                PlayerData.broadCast("SNO_WAR.COMMAND.START", new Lang.Args(Lang.Parameter.PLAYER, sender.getName()));
                super.setStateExec(true);
            } else {

            }
        }
        if(!super.isStateExec()){
            this.help();
        }
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
