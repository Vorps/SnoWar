package me.vorps.snowar.cooldowns;

import java.util.HashMap;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class CoolDowns {
    private int timeCooldown;
    private long time;
    private String id;
    private String namePlayer;

    private static HashMap<String, CoolDowns> coolDowns;

    static {
        coolDowns = new HashMap<>();
    }

    /**
     * Add cooldown
     * @param namePlayer String
     * @param timeCooldown int
     * @param id String
     */
    public CoolDowns(String namePlayer, int timeCooldown, String id){
        this.namePlayer = namePlayer;
        this.timeCooldown = timeCooldown;
        this.id = id;
        this.time = System.currentTimeMillis();
        CoolDowns.coolDowns.put(namePlayer+id, this);
    }

    /**
     * Test player cooldown
     * @param namePlayer String
     * @param id String
     * @return boolean
     */
    public static boolean hasCoolDown(String namePlayer, String id){
        return CoolDowns.coolDowns.containsKey(namePlayer+id);
    }

    /**
     * Return rest Seconde
     * @return Long
     */
	public Long getSecondsLeft(){
        return (( CoolDowns.coolDowns.get(this.namePlayer+this.id).time / 1000) + this.timeCooldown) - System.currentTimeMillis() / 1000;
	}

    /**
     * Remove cooldown player
     */
    public void removeCoolDown(){
        if(CoolDowns.hasCoolDown(this.namePlayer, this.id)){
            CoolDowns.coolDowns.remove(this.namePlayer+this.id);
        }
    }

    /**
     * Return Cooldown
     * @param namePlayer String
     * @param id String
     * @return CoolDowns
     */
    public static CoolDowns getCoolDown(String namePlayer, String id){
        return  CoolDowns.coolDowns.get(namePlayer+id);
    }
}
