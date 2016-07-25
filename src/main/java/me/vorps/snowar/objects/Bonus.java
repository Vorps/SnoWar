package me.vorps.snowar.objects;

import lombok.Getter;
import me.vorps.snowar.PlayerData;
import me.vorps.snowar.cooldowns.CoolDownBonus;
import me.vorps.snowar.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Snowball;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.sql.ResultSet;
import java.util.Random;

/**
 * Project SnoWar Created by Vorps on 23/07/2016 at 21:02.
 */
public class Bonus {

    public enum BonusData {
        STACK_SNOW_BALL(new me.vorps.snowar.bonus.Bonus(0, 25F, "", "", false) {
            @Override
            public void onEnable(PlayerData playerData) {
                playerData.getPlayer().getInventory().addItem(Item.getItem("ball", playerData.getLang()).withAmount(16).get());
            }

            @Override
            public void onDisable(PlayerData playerData) {
            }

            @Override
            public void onUse(PlayerData playerData, PlayerInteractEvent e) {
            }
        }),
        SNOW_GUN(new me.vorps.snowar.bonus.Bonus(10, 10F, "", "", false) {
            @Override
            public void onEnable(PlayerData playerData) {
                playerData.getPlayer().getInventory().addItem(Item.getItem("gun", playerData.getLang()).get());
            }

            @Override
            public void onDisable(PlayerData playerData) {
                playerData.getPlayer().getInventory().remove(Item.getItem("gun", playerData.getLang()).get());
            }

            @Override
            public void onUse(PlayerData playerData, PlayerInteractEvent e) {
            }
        }),
        TRIPLE_SHOOT(new me.vorps.snowar.bonus.Bonus(15, 15F, "", "", false) {
            @Override
            public void onEnable(PlayerData playerData) {

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
            }
        }),
        SNOW_BALL_FASTE(new me.vorps.snowar.bonus.Bonus(15, 20F, "", "", false) {
            @Override
            public void onEnable(PlayerData playerData) {

            }

            @Override
            public void onDisable(PlayerData playerData) {

            }

            @Override
            public void onUse(PlayerData playerData, PlayerInteractEvent e) {
                Snowball snowball3 = playerData.getPlayer().launchProjectile(Snowball.class);
                snowball3.setVelocity(snowball3.getVelocity().multiply(2));
                e.getItem().setAmount(e.getItem().getAmount()-1);
                e.setCancelled(true);
            }
        }),
        ADRENALINE(new me.vorps.snowar.bonus.Bonus(15, 30F , "", "", false) {
            @Override
            public void onEnable(PlayerData playerData) {
                playerData.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, this.getTime()*20, 1));
                playerData.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, this.getTime()*20, 2));
            }

            @Override
            public void onDisable(PlayerData playerData) {

            }

            @Override
            public void onUse(PlayerData playerData, PlayerInteractEvent e) {
            }
        });

        private @Getter me.vorps.snowar.bonus.Bonus bonus;

        BonusData(me.vorps.snowar.bonus.Bonus bonus){
            this.bonus = bonus;
        }
    }

    public Bonus(ResultSet resultSet){

    }

    public static void give(PlayerData playerData){
        playerData.getPlayer().playSound(playerData.getPlayer().getLocation(), Sound.FIZZ, 10, 10);
        int luck = new Random().nextInt(100);
        int var = 0;
        for(BonusData bonusData : BonusData.values()){
            var+= bonusData.getBonus().getPercent();
            if(var >= luck) {
                Bonus.giveBonus(bonusData, playerData);
                break;
            }
        }
    }

    private static void giveBonus(BonusData bonusData, PlayerData playerData){
        if(playerData.getBonusData() != null){
            playerData.getBonusData().getBonusData().getBonus().onDisable(playerData);
            Bukkit.getScheduler().cancelTask(playerData.getBonusData().getTask());
            playerData.setBonusData(null);
        }
        if(bonusData.getBonus().getTime() > 0){
            playerData.setBonusData(new CoolDownBonus(bonusData, playerData));
        }
        playerData.addBonus();
        bonusData.getBonus().onEnable(playerData);
    }
}
