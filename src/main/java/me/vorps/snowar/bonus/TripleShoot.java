package me.vorps.snowar.bonus;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.utils.Item;
import org.bukkit.entity.Snowball;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

/**
 * Project SnoWar Created by Vorps on 25/07/2016 at 15:46.
 */
public class TripleShoot extends Bonus {

    public TripleShoot(final int time, final double percent, final String enable, final String disable, final boolean persistence, final String label, final String icon){
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
        Snowball snowball =  playerData.getPlayer().launchProjectile(Snowball.class);
        snowball.setVelocity(snowball.getVelocity().add(new Vector(0.30, 0, 0.30)));
        Snowball snowball2 =  playerData.getPlayer().launchProjectile(Snowball.class);
        snowball2.setVelocity(snowball2.getVelocity().add(new Vector(-0.30, 0, -0.30)));
        playerData.removeBall();
    }
}
