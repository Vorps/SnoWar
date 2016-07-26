package me.vorps.snowar.utils;

import me.vorps.snowar.Exceptions.SqlException;
import me.vorps.snowar.databases.Database;
import me.vorps.snowar.lang.Lang;
import me.vorps.snowar.lang.LangSetting;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionType;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project Hub Created by Vorps on 17/05/2016 at 20:51.
 */
public class Item {

    private HashMap<String, String> label;
    private HashMap<String, String[]> lore;
    private me.vorps.snowar.menu.Item item;

    /**
     * Load item bdd
     * @param result ResultSet
     * @throws SqlException
     */
    public Item(ResultSet result) throws SqlException {
        label = new HashMap<>();
        me.vorps.snowar.menu.Item item;
        if(Database.SNOWAR.getDatabase().getString(result, 5) != null){
            item = new  me.vorps.snowar.menu.Item(Database.SNOWAR.getDatabase().getString(result, 5));
        } else if(Database.SNOWAR.getDatabase().getString(result, 6) != null){
            item = new  me.vorps.snowar.menu.Item(PotionType.valueOf(Database.SNOWAR.getDatabase().getString(result, 6)));
        } else {
            item = new  me.vorps.snowar.menu.Item(Database.SNOWAR.getDatabase().getInt(result, 3));
            item.withData((byte) Database.SNOWAR.getDatabase().getInt(result, 4));
        }
        String labelTmp = Database.SNOWAR.getDatabase().getString(result, 2);
        for(LangSetting langSetting : LangSetting.getListLangSetting().values()){
            label.put(langSetting.getName(), Lang.getMessage(labelTmp, langSetting.getName()));
        }
        String loreTmp = Database.SNOWAR.getDatabase().getString(result, 8);
        if(loreTmp != null){
            lore = new HashMap<>();
            for(LangSetting langSetting : LangSetting.getListLangSetting().values()){
                lore.put(langSetting.getName(), lore(Lang.getMessage(loreTmp,  langSetting.getName())));
            }
        }
        item.withAmount(Database.SNOWAR.getDatabase().getInt(result, 7));
        enchant(Database.SNOWAR.getDatabase().getString(result, 9), item);
        int durability = Database.SNOWAR.getDatabase().getInt(result, 10);
        if(durability != 0){
            item.withDurability(durability);
        }
        String color = Database.SNOWAR.getDatabase().getString(result, 11);
        if(color != null){
            item.withColor(Color.valueOf(color).getColor());
        }
        item.hideEnchant(Database.SNOWAR.getDatabase().getBoolean(result, 12));
        this.item = item;
        listItem.put(Database.SNOWAR.getDatabase().getString(result, 1), this);
    }

    /**
     * Return item
     * @param lang String
     * @return me.vorps.snowar.menu.Item
     */
    private  me.vorps.snowar.menu.Item getItem(String lang){
        me.vorps.snowar.menu.Item item = new  me.vorps.snowar.menu.Item(this.item);
        item.withName(label.get(lang));
        if(lore != null){
            item.withLore(lore.get(lang));
        }
        return item;
    }

    private static HashMap<String, Item> listItem;

    static {
        listItem = new HashMap<>();
    }

    /**
     * return Lore
     * @param lore String
     * @return String[]
     */
    private static String[] lore(String lore){
        ArrayList<String> loreTab = new ArrayList<>();
        int y = 0;
        if(lore != null){
            for(int i = 0; i < lore.length(); i++){
                if(lore.charAt(i) == ';'){
                    loreTab.add(lore.substring(y, i));
                    y = i;
                }
            }
            return loreTab.toArray(new String[loreTab.size()]);
        }
        return new String[0];
    }

    /**
     * Load enchant item
     * @param enchentment String
     * @param item me.vorps.snowar.menu.Item
     */
    private static void enchant(String enchentment,  me.vorps.snowar.menu.Item item){
        int y = 0;
        int[] var = new int[2];
        var[0] = -1;
        var[1] = -1;
        if(enchentment != null){
            for(int i = 0; i < enchentment.length(); i++){
                if(enchentment.charAt(i) == ':'){
                    if(y != 0){
                        y++;
                    }
                    var[0] = Integer.parseInt(enchentment.substring(y, i));
                    y = i;
                }
                if(enchentment.charAt(i) == ';'){
                    var[1] = Integer.parseInt(enchentment.substring(y+1, i));
                    y = i;
                }
                if(var[0] != -1 && var[1] != -1){
                    item.withEnchant(Enchantment.getById(var[0]), var[1]);
                    var[0] = -1;
                    var[1] = -1;
                }
            }
        }
    }


    public static  me.vorps.snowar.menu.Item getItem(String name, String lang){
        return listItem.get(name).getItem(lang);
    }


    /**
     * Clear all Item
     */
    public static void clear(){
        listItem.clear();
    }
}
