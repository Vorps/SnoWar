package net.vorps.snowar.bonus;

import net.vorps.api.utils.Item;
import net.vorps.snowar.PlayerData;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Project SnoWar Created by Vorps on 25/07/2016 at 15:40.
 */
public class StackSnowBall extends Bonus {


    public StackSnowBall(final int time, final double percent, final String enable, final String disable, final boolean persistence, final String label, final String icon, final String lore){
        super(time, percent , enable, disable, persistence, label, icon, lore);
    }

    @Override
    public void onEnable(PlayerData playerData) {
        playerData.getPlayer().getInventory().addItem(Item.getItem("ball", playerData.getLang()).withAmount(16).get());
    }

    @Override
    public void onDisable(PlayerData playerData) {
    }

    @Override
    public void onUse(PlayerData playerData, PlayerInteractEvent e) {
    }
}
