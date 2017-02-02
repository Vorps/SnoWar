package net.vorps.snowar.cooldowns;

import net.vorps.snowar.objects.Parameter;
import org.bukkit.entity.Player;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 21:41.
 */
public class CooldownBall extends Thread {

    private Player player;
    private int time;

    public CooldownBall(final Player player){
        this.player = player;
        this.time = Parameter.getCooldownBall();
    }

    public void run(){
        while(time > 0){
            this.player.setLevel(this.time);
            this.time--;
            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        this.player.setLevel(0);
    }
}
