package net.vorps.snowar.utils;

import net.vorps.snowar.objects.Parameter;
import net.vorps.api.lang.Lang;
import net.vorps.api.lang.LangSetting;
import net.vorps.snowar.scenario.Scenario;
import org.bukkit.Bukkit;

import java.util.HashMap;

/**
 * Project SnoWar Created by Vorps on 31/07/2016 at 13:26.
 */
public enum Weather{
    SUN("SNO_WAR.WEATHER.CLEAR", 1000000),
    RAIN("SNO_WAR.WEATHER.RAIN", 1000000),
    THUNDER("SNO_WAR.WEATHER.THUNDER", 1000000);

    private HashMap<String, String> label;
    private int duration;

    Weather(final String label, final int duration){
        this.label = new HashMap<>();
        for(LangSetting langSetting : LangSetting.getListLangSetting().values()) this.label.put(langSetting.getName(), Lang.getMessage(label, langSetting.getName()));
        this.duration = duration;
    }

    public static void setWeather(){
        switch (Parameter.getWeather()){
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
        Bukkit.getWorlds().get(0).setThunderDuration(Parameter.getWeather().duration);
    }

    public String getLabel(final String lang){
        return this.label.get(lang);
    }

    public static void addWeather() {
        Scenario.setWeather(Scenario.getWeather() + 1);
        if (Scenario.getWeather() >= Weather.values().length) Scenario.setWeather(0);
        Weather weather = Weather.values()[Scenario.getWeather()];
        Parameter.setWeather(weather);
        Weather.setWeather();
    }
}