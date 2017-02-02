package net.vorps.snowar.utils;

import net.vorps.snowar.Data;
import net.vorps.snowar.PlayerData;
import net.vorps.snowar.Settings;

/**
 * Project SnoWar Created by Vorps on 31/08/2016 at 00:32.
 */
public class TimeStart {

    public static int getTime(){
        return  ((float)(PlayerData.getPlayerInGame()-1)/Data.getNbPlayerMax())*100 < 25.0 && ((float)PlayerData.getPlayerInGame()/Data.getNbPlayerMax())*100 >= 25.0 ? Settings.getTimeStart() :
                ((float)(PlayerData.getPlayerInGame()-1)/Data.getNbPlayerMax())*100 < 50.0 && ((float)PlayerData.getPlayerInGame()/Data.getNbPlayerMax())*100 >= 50.0 ? Settings.getTimeStart()/2 :
                        ((float)(PlayerData.getPlayerInGame()-1)/Data.getNbPlayerMax())*100 < 75.0 && ((float)PlayerData.getPlayerInGame()/Data.getNbPlayerMax())*100 >= 75.0 ? Settings.getTimeStart()/4 :
                                PlayerData.getPlayerInGame() == Data.getNbPlayerMax() ? Settings.getTimeStart()/6 : -1;
    }
}
