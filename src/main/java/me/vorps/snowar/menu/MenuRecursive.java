package me.vorps.snowar.menu;

import me.vorps.snowar.lang.Lang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

/**
 * Project FortyCubeAPIBukkit Created by Vorps on 28/04/2016 at 17:21.
 */
public abstract class MenuRecursive extends Menu {
    protected int page;
    private String lang;
    protected ArrayList<Item> list;
    private int lineSize;
    private int start;
    private int[] exclude;


    public abstract void initMenu(Player player, int page);

    /**
     * Contruct menu Recursive :: lineSize : lineSize == 9 || 7  -- start : start%9 == 0
     * @param ids byte
     * @param menu Inventory
     * @param model int[][]
     * @param list  ArrayList<Item>
     * @param lang String
     * @param lineSize int
     * @param start int
     * @param exclude int[]
     */
    public MenuRecursive(byte[] ids, Inventory menu, int[][] model, ArrayList<Item> list, String lang, int lineSize, int start ,int[] exclude){
        this(ids, menu, model, list, lang, lineSize, start);
        this.exclude = exclude;
    }

    /**
     * Contruct menu Recursive :: lineSize : lineSize == 9 || 7  -- start : start%9 == 0
     * @param ids byte
     * @param menu Inventory
     * @param model int[][]
     * @param list  ArrayList<Item>
     * @param lang String
     * @param lineSize int
     * @param start int
     */
    public MenuRecursive(byte[] ids, Inventory menu, int[][] model, ArrayList<Item> list, String lang, int lineSize, int start){
        super(ids, menu, model);
        this.exclude = new int[0];
        this.list = list;
        this.lang = lang;
        this.lineSize = lineSize;
        this.start = start;
        this.page = 1;
    }

    /**
     * Select page :: (page-1)*((lineSize*(((size-9)/9)-(start/9)))-(exclude.lenght == 0 ? 0 : exclude.length)) <= list.size()
     * @param page int
     */
    protected void getPage(int page){
        getPage(page, Type.DYNAMIQUE);
    }

    /**
     * Select page :: (page-1)*((lineSize*(((size-9)/9)-(start/9)))-(exclude.lenght == 0 ? 0 : exclude.length)) <= list.size()
     * @param page int
     * @param type Type
     */
    protected void getPage(int page, Type type ){
        if(ids != null && model != null){
            constructModel();
        }
        if(type == Type.DYNAMIQUE){
            init(start, menu.getSize(), list.size(), (page-1)*((lineSize*(((menu.getSize()-9)/9)-(start/9)))-(exclude.length == 0 ? 0 : exclude.length)), lineSize, exclude);
        }
        menu.setItem(menu.getSize()-9, new Item(Material.ARROW).withName(Lang.getMessage("SNO_WAR.INVENTORY.RECURSIVE.QUIT", lang)).get());
        this.page = page;
        if(page > 1){
            menu.setItem(menu.getSize()-2, new Item(Material.EMPTY_MAP).withName(Lang.getMessage("SNO_WAR.INVENTORY.RECURSIVE.BACK", lang, new Lang.Args(Lang.Parameter.PAGE, ""+(page-1)))).get());
        }
        if(list(0, (page-1)*((lineSize*(((menu.getSize()-9)/9)-(start/9)))-(exclude.length == 0 ? 0 : exclude.length)), list, lineSize, exclude)){
            menu.setItem(menu.getSize()-1, new Item(Material.PAPER).withName(Lang.getMessage("SNO_WAR.INVENTORY.RECURSIVE.NEXT", lang, new Lang.Args(Lang.Parameter.PAGE, ""+(page+1)))).get());
        }
    }
}
