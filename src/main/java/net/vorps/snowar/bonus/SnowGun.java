package net.vorps.snowar.bonus;

import net.vorps.api.utils.Item;
import net.vorps.snowar.PlayerData;
import org.bukkit.entity.Snowball;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Random;

/**
 * Project SnoWar Created by Vorps on 25/07/2016 at 15:45.
 */
public class SnowGun extends Bonus {

    public SnowGun(final int time, final double percent, final String enable, final String disable, final boolean persistence, final String label, final String icon, final String lore){
        super(time, percent , enable, disable, persistence, label, icon, lore);
    }

    @Override
    public void onEnable(PlayerData playerData) {
        playerData.getPlayer().getInventory().addItem(Item.getItem("gun", playerData.getLang()).get());
        super.setItemStack(Item.getItem("gun", playerData.getLang()).get());
    }

    @Override
    public void onDisable(PlayerData playerData) {
        playerData.getPlayer().getInventory().remove(Item.getItem("gun", playerData.getLang()).get());
    }

    @Override
    public void onUse(PlayerData playerData, PlayerInteractEvent e) {
        playerData.getPlayer().launchProjectile(Snowball.class);
        if(new Random().nextBoolean()){
            playerData.getPlayer().launchProjectile(Snowball.class).getVelocity().multiply(1.5);
        }
    }
}
