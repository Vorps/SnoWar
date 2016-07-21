package me.vorps.snowar.utils;

import me.vorps.snowar.Exceptions.SqlException;
import me.vorps.snowar.databases.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class Lang {
    public static class Args{
        private Parameter parameter;
        private String value;

        public Args(Parameter parameter, String value){
            this.parameter = parameter;
            this.value = value;
        }
    }

    public enum Parameter {
        STATE("<state>"),
        TIME("<time>"),
        TEAM("<team>"),
        POINT("<point>"),
        NBR_PLAYER("<nbr_player>"),
        GOLDS("<golds>"),
        KILL("<kill>"),
        DEAD("<dead>"),
        WOOL("<wool>"),
        VAR("<var>"),
        PLAYER("<player>"),
        MODE("<mode>"),
        SERVER("<server>"),
        MESSAGE("<message>"),
        KILLER("<killer>"),
        NBR_MAX_PLAYER("<nbr_max_player>"),
        COLOR("<color>"),
        WINNER("<winner>"),
        PRICE("<price>"),
        DEVICE("<device>"),
        LOOSER("<looser>"),
        KIT("<kit>"),
        PAGE("<page>"),
        LIFE("life"),
        KILLED("killed");

        private String label;

        Parameter(String label){
            this.label = label;
        }

    }

    private static HashMap<String, HashMap<String, String>> lang = new HashMap<>();

    public static String getMessageTmp(String key, String lang, Args[] args){
        String message = getMessage(key, lang);
        for(int i = 0; i < args.length; i++){
            message = message.replaceAll(args[i].parameter.label , args[i].value);
        }
        return message;
    }

    public static String getMessageTmp(String key, String lang, Args args){
        return getMessage(key, lang).replaceAll(args.parameter.label , args.value);
    }

    public static String getMessage(String key, String langPlayer, Args... args){
        String message;
        switch (args.length){
            case 0:
                HashMap<String, String> messageHashMap = lang.get(key);
                message = messageHashMap.get(langPlayer);
                break;
            case 1:
                message = getMessageTmp(key, langPlayer, args[0]);
                break;
            default:
                message = getMessageTmp(key, langPlayer, args);
                break;
        }
        return message;
    }

    public Lang(ResultSet result) throws SqlException, SQLException{
        String key = Database.SNOWAR.getDatabase().getString(result, 1);
        HashMap<String, String> langMessage =  new HashMap<>();
        for(LangSetting langSetting : LangSetting.getListLangSetting().values()){
            langMessage.put(langSetting.getName(), result.getString(langSetting.getColumnId()));
        }
        lang.put(key, langMessage);
    }

    public static void clearLang(){
        lang.clear();
    }
}
