package net.vorps.snowar.bonus;

import lombok.Getter;
import lombok.Setter;
import net.vorps.api.lang.Lang;
import net.vorps.api.utils.Item;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.Settings;
import net.vorps.snowar.scenario.Scenario;
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
    private @Getter String icon;
    private String lore;

    /**
     * Constructor abstract instance new Bonus
     * @param time int
     * @param percent double
     * @param enable String
     * @param disable String
     * @param persistence boolean
     * @param label String
     */
    public Bonus(final int time, final double percent, final String enable, final String disable, final boolean persistence, final String label, final String icon, final String lore){
        this.time = time;
        this.percent = percent;
        this.enable = enable;
        this.disable = disable;
        this.persistence = persistence;
        this.label = label;
        this.icon = icon;
        this.lore = lore;
        Bonus.bonusList.add(this);
    }

    public String getLore(String lang){
        return Lang.getMessage(this.lore, lang);
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

    private static int percentTotal(){
        int percent = 0;
        for(Bonus bonus : Bonus.getBonusList()){
            percent+= bonus.percent;
        }
        return percent;
    }

    public void addPercent(){
        if(Bonus.percentTotal() < 100) this.percent++;
    }

    public void removePercent(){
        if(Bonus.percentTotal() > 0 && this.percent > 0) this.percent--;
    }

    public static Bonus getBonus(ItemStack itemStack){
        Bonus bonus = null;
        for(Bonus bonusList : Bonus.getBonusList()){
            if(Item.getItem(bonusList.icon, Settings.getConsoleLang()).get().getItemMeta().getDisplayName().equals(itemStack.getItemMeta().getDisplayName())){
                bonus = bonusList;
                break;
            }
        }
        return bonus;
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
