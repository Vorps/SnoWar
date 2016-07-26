package me.vorps.snowar.utils;

import lombok.Getter;
import me.vorps.snowar.Exceptions.SqlException;
import me.vorps.snowar.databases.Database;
import me.vorps.snowar.lang.Lang;
import me.vorps.snowar.lang.LangSetting;

import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Project Hub Created by Vorps on 21/05/2016 at 14:42.
 */
public class MessageTitle {

    private static HashMap<String, MessageTitle> messageTitleList = new HashMap<>();

    private @Getter HashMap<String, String> title;
    private @Getter HashMap<String, String> subTitle;

    public MessageTitle(ResultSet result) throws SqlException {
        this.title = new HashMap<>();
        this.subTitle = new HashMap<>();
        for(LangSetting langSetting : LangSetting.getListLangSetting().values()){
            this.title.put(langSetting.getName(), Lang.getMessage(Database.SNOWAR.getDatabase().getString(result, 2), langSetting.getName()));
            this.subTitle.put(langSetting.getName(), Lang.getMessage(Database.SNOWAR.getDatabase().getString(result, 3), langSetting.getName()));
        }
        MessageTitle.messageTitleList.put(Database.SNOWAR.getDatabase().getString(result, 1), this);
    }

    public static void clear(){
        MessageTitle.messageTitleList.clear();
    }

    public static MessageTitle getMessageTitle(String name){
        return MessageTitle.messageTitleList.get(name);
    }
}
