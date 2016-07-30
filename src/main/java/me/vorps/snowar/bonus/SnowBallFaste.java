package me.vorps.snowar.bonus;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.utils.Item;
import org.bukkit.entity.Snowball;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Project SnoWar Created by Vorps on 25/07/2016 at 15:47.
 */
public class SnowBallFaste extends Bonus {

    public SnowBallFaste(final int time, final double percent, final String enable, final String disable, final boolean persistence, final String label, final String icon){
        super(time, percent , enable, disable, persistence, label, icon);
    }


    @Override
    public void onEnable(PlayerData playerData) {
        super.setItemStack(Item.getItem("ball", playerData.getLang()).get());
    }

    @Override
    public void onDisable(PlayerData playerData) {

    }

    @Override
    public void onUse(PlayerData playerData, PlayerInteractEvent e) {
        Snowball snowball3 = playerData.getPlayer().launchProjectile(Snowball.class);
        snowball3.setVelocity(snowball3.getVelocity().multiply(2));
        e.getItem().setAmount(e.getItem().getAmount()-1);
        playerData.removeBall();
        e.setCancelled(true);
    }
}
