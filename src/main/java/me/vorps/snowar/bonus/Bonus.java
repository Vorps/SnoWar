package me.vorps.snowar.bonus;

import lombok.Getter;
import lombok.Setter;
import me.vorps.snowar.PlayerData;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

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
    private String label;

    /**
     * Constructor abstract instance new Bonus
     * @param time int
     * @param percent double
     * @param enable String
     * @param disable String
     * @param persistence boolean
     * @param label String
     */
    public Bonus(final int time, final double percent, final String enable, final String disable, final boolean persistence, final String label){
        this.time = time;
        this.percent = percent;
        this.enable = enable;
        this.disable = disable;
        this.persistence = persistence;
        this.label = label;
        Bonus.bonusList.add(this);
    }

    /**
     * Method start bonus
     * @param playerData PlayerData
     */
    public abstract void onEnable(PlayerData playerData);

    /**
     * Method end bonus
     * @param playerData PlayerData
     */
    public abstract void onDisable(PlayerData playerData);

    /**
     * Method use bonus
     * @param playerData PlayerData
     */
    public abstract void onUse(PlayerData playerData, PlayerInteractEvent e);

    @Override
    public String toString(){
        return this.label;
    }

    /**
     * Clear all bonus
     */
    public static void clear(){
        Bonus.bonusList.clear();
    }

    private static @Getter ArrayList<Bonus> bonusList;

    static {
        Bonus.bonusList = new ArrayList<>();
    }
}
