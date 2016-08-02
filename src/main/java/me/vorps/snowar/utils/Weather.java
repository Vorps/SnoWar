package me.vorps.snowar.utils;

import me.vorps.snowar.scenario.Scenario;
import org.bukkit.Bukkit;

/**
 * Project SnoWar Created by Vorps on 31/07/2016 at 13:26.
 */
public enum Weather{
    SUN("clear", 1000000),
    RAIN("rain", 1000000),
    THUNDER("thunder", 1000000);

    private String label;
    private int duration;

    Weather(String label, int duration){
        this.label = label;
        this.duration = duration;
    }

    public void setWeather(){
        switch (this){
            case SUN:
                Bukkit.getWorlds().get(0).setStorm(false);
                Bukkit.getWorlds().get(0).setThundering(false);
                break;
            case RAIN:
                Bukkit.getWorlds().get(0).setStorm(true);
                Bukkit.getWorlds().get(0).setThundering(false);
                break;
            case THUNDER:
                Bukkit.getWorlds().get(0).setStorm(true);
                Bukkit.getWorlds().get(0).setThundering(true);
                break;
        }
        Bukkit.getWorlds().get(0).setThunderDuration(this.duration);
    }

    public String getLabel(){
        return this.label;
    }

    public static Weather getWeather(String label){
        Weather weather = Weather.SUN;
        for(Weather weatherList : Weather.values()){
            Scenario.addWeather();
            if(weatherList.label.equals(label)){
                weather = weatherList;
                break;
            }
        }
        return weather;
    }
}