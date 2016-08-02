package me.vorps.snowar.utils;

import me.vorps.snowar.Exceptions.SqlException;
import me.vorps.snowar.databases.Database;
import me.vorps.snowar.lang.Lang;
import me.vorps.snowar.lang.LangSetting;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Project Hub Created by Vorps on 01/02/2016 at 01:41.
 */
public class BookHelp {
    private static HashMap<String, BookHelp> bookHelpList = new HashMap<>();
    private static TreeMap<String ,BookHelp> trieBookHelp = new TreeMap<>(new ComparatorBookHelp(bookHelpList));

    private ItemStack book;
    private HashMap<String, me.vorps.snowar.menu.Item> item;
    private HashMap<String, String> label;
    private int level;
    private HashMap<String, ArrayList<String>> values;

    public static HashMap<String, BookHelp> getBookList(){
        return bookHelpList;
    }
    public static TreeMap<String, BookHelp> getTrieBookHelp(){
        return trieBookHelp;
    }
    public HashMap<String, me.vorps.snowar.menu.Item> getItem(){
        return item;
    }
    public int getLevel(){
        return level;
    }
    private boolean state;

    public static void trieBookHelp(){
        trieBookHelp.clear();
        trieBookHelp.putAll(bookHelpList);
    }

    private static class ComparatorBookHelp implements Comparator<String> {
        Map<String, BookHelp> base;
        private ComparatorBookHelp(Map<String, BookHelp> base) {
            this.base = base;
        }

        public int compare(String a, String b) {
            return base.get(a).level >= base.get(b).level ? 1 : -1;
        }
    }

    public static BookHelp getBookHelp(String nameBook){
        return bookHelpList.get(nameBook);
    }

    public BookHelp(ResultSet result, boolean state) throws SqlException {
        this.state = state;
        if(state){
            item = new HashMap<>();
        } else {
            label = new HashMap<>();
        }
        values = new HashMap<>();
        book = new ItemStack(Material.WRITTEN_BOOK, 1);
        level = Database.SNOWAR.getDatabase().getInt(result, 4);
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setAuthor(Database.SNOWAR.getDatabase().getString(result, 2));
        book.setItemMeta(meta);
        if (state) {
            for(LangSetting langSetting : LangSetting.getListLangSetting().values()) {
                item.put(langSetting.getName(), Item.getItem(Database.SNOWAR.getDatabase().getString(result, 3), langSetting.getName()));
            }
        } else {
            for(LangSetting langSetting : LangSetting.getListLangSetting().values()) {
                label.put(langSetting.getName(), Lang.getMessage(Database.SNOWAR.getDatabase().getString(result, 3), langSetting.getName()));
            }
        }
        ResultSet results = Database.SNOWAR.getDatabase().getData("book_setting", "bs_book = '"+ Database.SNOWAR.getDatabase().getString(result, 1)+"' ");
        try {
            for(LangSetting langSetting : LangSetting.getListLangSetting().values()){
                ArrayList<String> pages = new ArrayList<>();
                while(results.next()){
                    pages.add(Lang.getMessage(Database.SNOWAR.getDatabase().getString(results, 3), langSetting.getName()));
                }
                values.put(langSetting.getName(), pages);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        if(state){
            for(LangSetting langSetting : LangSetting.getListLangSetting().values()){
                bookHelpList.put(item.get(langSetting.getName()).get().getItemMeta().getDisplayName(), this);
            }
        } else {
            for(LangSetting langSetting : LangSetting.getListLangSetting().values()){
                bookHelpList.put(label.get(langSetting.getName()), this);
            }
        }
    }


    public ItemStack getBook(String lang){
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setPages(values.get(lang));
        String title;
        if(state){
            title = item.get(lang).get().getItemMeta().getDisplayName();
        } else {
            title = label.get(lang);
        }
        meta.setTitle(title);
        book.setItemMeta(meta);
        return book;
    }

    public static void clear(){
        bookHelpList.clear();
    }
}
