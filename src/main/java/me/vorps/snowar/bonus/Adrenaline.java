package me.vorps.snowar.bonus;

import me.vorps.snowar.PlayerData;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Project SnoWar Created by Vorps on 25/07/2016 at 15:47.
 */
public class Adrenaline extends Bonus {

    public Adrenaline(final int time, final double percent, final String enable, final String disable, final boolean persistence, final String label){
        super(time, percent , enable, disable, persistence, label);
    }

    @Override
    public void onEnable(PlayerData playerData) {
        playerData.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, super.getTime()*20, 1));
        playerData.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, super.getTime()*20, 2));
    }

    @Override
    public void onDisable(PlayerData playerData) {

    }

    @Override
    public void onUse(PlayerData playerData, PlayerInteractEvent e) {
    }
}
