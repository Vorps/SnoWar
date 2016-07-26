package me.vorps.snowar.cooldowns;

import lombok.Getter;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.SnowWar;
import me.vorps.snowar.bonus.Bonus;
import me.vorps.snowar.lang.Lang;
import org.bukkit.Bukkit;

/**
 * Project SnoWar Created by Vorps on 24/07/2016 at 21:48.
 */
public class CoolDownBonus {

    private Bonus bonus;
    private PlayerData playerData;
    private @Getter int task;

    /**
     * Remove bonus time
     * @param bonus Bonus
     * @param playerData PlayerData
     */
    public CoolDownBonus(final Bonus bonus, final PlayerData playerData){
        this.bonus = bonus;
        this.playerData = playerData;
        run();
    }

    public void run(){
        this.task = Bukkit.getScheduler().scheduleSyncDelayedTask(SnowWar.getInstance(), new Runnable() {
            @Override
            public void run() {
                bonus.onDisable(playerData);
                playerData.getBonusData().remove(bonus);
                playerData.getPlayer().sendMessage(Lang.getMessage(bonus.getDisable(), playerData.getLang()));
            }
        }, this.bonus.getTime()*20);
    }

}
