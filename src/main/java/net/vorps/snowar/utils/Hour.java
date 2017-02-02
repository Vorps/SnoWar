package net.vorps.snowar.utils;

import lombok.Getter;
import net.vorps.snowar.objects.Parameter;
import net.vorps.snowar.scenario.Scenario;
import net.vorps.api.lang.Lang;
import net.vorps.api.lang.LangSetting;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionType;

import java.util.HashMap;

/**
 * Project SnoWar Created by Vorps on 31/07/2016 at 13:23.
 */
public enum Hour {
    MORNING(23000, "morning"),
    DAY(0, "day"),
    EVENING(12000, "evening"),
    NIGHT(13500, "night");

    private @Getter long time;
    private @Getter HashMap<String, String> label;

    Hour(long time, String label){
        this.label = new HashMap<>();
        this.time = time;
        for(LangSetting langSetting : LangSetting.getListLangSetting().values()) this.label.put(langSetting.getName(), "§a"+ Lang.getMessage("SNO_WAR.SCENARIO."+label.toUpperCase(), langSetting.getName()));
    }

    public static void addHour(){
        Scenario.setHour(Scenario.getHour()+1);
        if(Scenario.getHour() >= 4) Scenario.setHour(0);
        Hour hourVar = Hour.values()[Scenario.getHour()];
        Parameter.setHour(hourVar);
        Hour.setHour();
    }

    public static void setHour(){
        Bukkit.getWorlds().get(0).setTime(Parameter.getHour().getTime());
    }
}
