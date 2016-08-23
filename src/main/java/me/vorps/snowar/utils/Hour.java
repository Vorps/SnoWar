package me.vorps.snowar.utils;

import lombok.Getter;
import me.vorps.snowar.scenario.Scenario;
import me.vorps.syluriapi.lang.Lang;
import me.vorps.syluriapi.lang.LangSetting;

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
        for(LangSetting langSetting : LangSetting.getListLangSetting().values()){
            this.label.put(langSetting.getName(), "§a"+ Lang.getMessage("SNO_WAR.SCENARIO."+label.toUpperCase(), langSetting.getName()));
        }
    }


    public static Hour getHour(long time){
        Hour hour = DAY;
        for(Hour hourTmp : Hour.values()){
            Scenario.addHour();
            if(hourTmp.time == time){
                hour = hourTmp;
                break;
            }
        }
        return hour;
    }
}
