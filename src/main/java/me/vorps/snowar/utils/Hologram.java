package me.vorps.snowar.utils;


import lombok.Setter;
import org.bukkit.*;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project SnoWar Created by Vorps on 03/08/2016 at 20:19.
 */

public class Hologram {

    private ArrayList<ArmorStand> armorStand;
    private HashMap<String, String> message;
    private @Setter double space;
    private @Setter Location location;
    private @Setter Location locationTmp;
    private @Setter long time;

    /**
     * Object Hologram
     * @param space double
     * @param message Message[]
     */
    public Hologram(double space, Message... message){
        this.message = new HashMap<>();
        this.armorStand = new ArrayList<>();
        this.addMessage(message);
        this.space = space;
        this.time = 0;
    }

    /**
     * Show Hologram
     * @param location Location
     */
    public void show(Location location){
        this.location = location.clone();
        this.locationTmp = location.clone();
        this.message.values().forEach((String message) -> this.showLine(message));
    }

    /**
     * Show Hologram Timer
     * @param location Location
     * @param time int
     * @param plugin Plugin
     */
    public void show(Location location, long time, Plugin plugin){
        this.show(location);
        this.time = time;
        this.timer(plugin);
    }

    private void timer(Plugin plugin){
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                remove();
            }
        }, (this.time/1000)*20);
    }

    /**
     * Add Message
     * @param message Message[]
     */
    public void addMessage(Message... message){
        for(Message messageList : message){
            this.message.put(messageList.key, messageList.value);
        }
    }

    /**
     * Remove Message
     * @param key String[]
     */
    public void removeMessage(String... key){
        for(String keyList : key){
            this.message.remove(keyList);
        }
    }

    /**
     * Update Hologram
     * @param location Location
     */
    public void update(Location location){
        remove();
        show(location);
    }

    /**
     * Update Hologram
     */
    public void update(){
        remove();
        show(this.location);
    }

    /**
     * Remove all Message
     */
    public void remove(){
        this.armorStand.forEach((ArmorStand armorStand) -> armorStand.remove());
    }


    private void showLine(String message){
        ArmorStand armorStand = Bukkit.getServer().getWorlds().get(0).spawn(this.locationTmp.add(0, -this.space, 0), ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setCustomName(message);
        armorStand.setCustomNameVisible(true);
        armorStand.setGravity(false);
        this.armorStand.add(armorStand);
    }

    /**
     * Factory message
     */
    public static class Message{

        private String key;
        private String value;

        public Message(String key, String value){
            this.key = key;
            this.value = value;
        }
    }

}
