package me.vorps.snowar.utils;

import me.vorps.snowar.scenario.Scenario;
import me.vorps.syluriapi.lang.Lang;
import me.vorps.syluriapi.lang.LangSetting;
import org.bukkit.Bukkit;

import java.util.HashMap;

/**
 * Project SnoWar Created by Vorps on 31/07/2016 at 13:26.
 */
public enum Weather{
    SUN("SNO_WAR.WEATHER.CLEAR", 1000000, "clear"),
    RAIN("SNO_WAR.WEATHER.RAIN", 1000000, "rain"),
    THUNDER("SNO_WAR.WEATHER.THUNDER", 1000000, "thunder");

    private HashMap<String, String> label;
    private String key;
    private int duration;

    Weather(final String label, final int duration, final String key){
        this.label = new HashMap<>();
        this.key = key;
        for(LangSetting langSetting : LangSetting.getListLangSetting().values()){
            this.label.put(langSetting.getName(), Lang.getMessage(label, langSetting.getName()));
        }
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

    public String getLabel(final String lang){
        return this.label.get(lang);
    }

    public static Weather getWeather(final String key){
        Weather weather = Weather.SUN;
        for(Weather weatherList : Weather.values()){
            if(weatherList.key.equals(key)){
                weather = weatherList;
                break;
            } else {
                Scenario.addWeather();
            }
        }
        return weather;
    }
}