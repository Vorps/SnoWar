package me.vorps.snowar.cooldowns;

import me.vorps.snowar.objects.Parameter;
import org.bukkit.entity.Player;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 21:41.
 */
public class CooldownBall extends Thread {

    private Player player;
    private int time;

    public CooldownBall(Player player){
        this.player = player;
        this.time = Parameter.getCooldownBall();
    }

    public void run(){
        while(time> 0){
            player.setLevel(time);
            time--;
            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        player.setLevel(0);
    }
}
