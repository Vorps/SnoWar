package me.vorps.snowar.cooldowns;

import lombok.Getter;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.SnowWar;
import me.vorps.snowar.objects.Bonus;
import org.bukkit.Bukkit;

/**
 * Project SnoWar Created by Vorps on 24/07/2016 at 21:48.
 */
public class CoolDownBonus {

    private @Getter Bonus.BonusData bonusData;
    private PlayerData playerData;
    private @Getter int task;

    public CoolDownBonus(Bonus.BonusData bonusData, PlayerData playerData){
        this.bonusData = bonusData;
        this.playerData = playerData;
        run();
    }

    public void run(){
        task = Bukkit.getScheduler().scheduleSyncDelayedTask(SnowWar.getInstance(), new Runnable() {
            @Override
            public void run() {
                bonusData.getBonus().onDisable(playerData);
                playerData.setBonusData(null);
            }
        }, bonusData.getBonus().getTime()*20);
    }

}
