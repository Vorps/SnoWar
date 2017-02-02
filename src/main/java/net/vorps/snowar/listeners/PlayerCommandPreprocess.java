package net.vorps.snowar.listeners;

import net.vorps.snowar.commands.Commands;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Project SnoWar Created by Vorps on 29/08/2016 at 17:46.
 */
public class PlayerCommandPreprocess implements Listener {

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e){
        if(!e.getPlayer().isOp()){
            e.setCancelled(true);
            for(Commands commands : Commands.values()){
                if(commands.getCommand().equalsIgnoreCase(e.getMessage().substring(1))){
                    e.setCancelled(false);
                    break;
                }
            }
        }
    }
}
