package net.vorps.snowar.cooldowns;

import lombok.Getter;
import net.vorps.api.lang.Lang;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.SnoWar;
import net.vorps.snowar.bonus.Bonus;
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
        this.task = Bukkit.getScheduler().scheduleSyncDelayedTask(SnoWar.getInstance(), new Runnable() {
            @Override
            public void run() {
                bonus.onDisable(playerData);
                playerData.getBonusData().remove(bonus);
                playerData.getPlayer().sendMessage(Lang.getMessage(bonus.getDisable(), playerData.getLang()));
            }
        }, this.bonus.getTime()*20);
    }

}
