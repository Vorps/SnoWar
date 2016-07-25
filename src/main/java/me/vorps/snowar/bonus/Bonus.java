package me.vorps.snowar.bonus;

import lombok.Getter;
import lombok.Setter;
import me.vorps.snowar.PlayerData;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Project SnoWar Created by Vorps on 24/07/2016 at 22:14.
 */
public abstract class Bonus {

    private @Getter int time;
    private @Getter double percent;
    private @Getter String enable;
    private @Getter String disable;
    private @Getter boolean persistence;
    private @Getter @Setter ItemStack itemStack;

    public Bonus(int time, double percent, String enable, String disable, boolean persistence){
        this.time = time;
        this.percent = percent;
        this.enable = enable;
        this.disable = disable;
        this.persistence = persistence;
    }

    public abstract void onEnable(PlayerData playerData);

    public abstract void onDisable(PlayerData playerData);

    public abstract void onUse(PlayerData playerData, PlayerInteractEvent e);

}
