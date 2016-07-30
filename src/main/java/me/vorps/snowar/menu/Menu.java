package me.vorps.snowar.menu;

import me.vorps.snowar.SnowWar;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Project Hub Created by Vorps on 03/03/2016 at 06:09.
 */
public abstract class Menu implements Listener{

    protected int[][] model;
    private ItemStack[] items;
    protected byte[] ids;
    protected Inventory menu;
    private ModelList[] modelList;
    private ArrayList<Integer> place = new ArrayList<>();

    /**
     * Constructor
     * @param ids byte[] Null
     * @param menu Inventory Null
     * @param model int[][] Null
     */
    protected Menu(byte[] ids, Inventory menu, int[][] model){
        this.menu = menu;
        this.model = model;
        this.ids = ids;
        if(ids != null){
            init();
            if(menu != null && model != null){
                constructModel();
            }
        }
        Bukkit.getServer().getPluginManager().registerEvents(this, SnowWar.getInstance());
    }

    /**
     * Init Model
     */
    private void init(){
        items = new ItemStack[ids.length];
        for(int i = 0; i < ids.length; i++){
            items[i] = new Item(160).withData(ids[i]).withName(" ").get();
        }
    }

    /**
     * Set item model
     */
    protected void constructModel(){
        for(int i = 0; i < model.length; i++){
            menu.setItem(model[i][0], items[model[i][1]]);
        }
    }

    /**
     * Test if pos is exclude
     * @param i int
     * @param exclude int[]
     * @return boolean
     */
    private boolean content(int i, int[] exclude){
        if(exclude.length > 0){
            for(int y : exclude){
                if(y == i){
                    return true;
                }
            }
            if(place.contains(i)){
                return true;
            }
        }
        return false;
    }

    /**
     * Place item pos
     * @param i int
     * @param exclude int[]
     * @param state boolean
     * @return int
     */
    private int placeExclude(int i, int[] exclude, boolean state){
        if(exclude.length > 0 && content(i, exclude)){
            int var = 0;
            if(state){
                while(content(i+var, exclude) && (i+var)%9 != 0){
                    var--;
                }
                if((i+var)%9 != 8){
                    place.add(i+var);
                    return i + var;
                }
            } else {
                while(content(i+var, exclude) && (i+var)%9 != 8){
                    var++;
                }
                if((i+var)%9 != 0){
                    place.add(i+var);
                    return i + var;
                }
            }
        }
        return i;
    }

    /**
     * Get Nummber slot exclude per line
     * @param exclude int[]
     * @param lineSize int
     * @param i int
     * @return int
     */
    private int getVar(int[] exclude, int lineSize, int i){
        int y = 0;
        if(exclude != null){
            for(int var : exclude){
                if(var <= lineSize + i && var >= i){
                    y++;
                }
            }
        }
        return y;
    }

    /**
     * @param start int
     * @param size int
     */
    protected void init(int start, final int size, int listSize, int index, int lineSize, int[] exclude){
        modelList = new ModelList[((size-9)/9)-1-start/9];
        if(modelList.length > 0){
            int middle = (modelList.length+1)/2;
            modelList[0] = new ModelList(middle);
            for(int i = 1; i < modelList.length; i++){
                if((i & 1) == 1){
                    if((modelList.length & 1) == 1){
                        modelList[i] = new ModelList(middle-((i+2)/2));
                    } else {
                        modelList[i] = new ModelList(middle+((i+2)/2));
                    }
                } else {
                    if((modelList.length & 1) == 1){
                        modelList[i] = new ModelList(middle+((i+1)/2));
                    } else {
                        modelList[i] = new ModelList(middle-((i+1)/2));
                    }
                }
            }
        } else {
            modelList = new ModelList[1];
            modelList[0] = new ModelList(start/9);
        }
        int max = 0;
        for(ModelList modelList : this.modelList){
            modelList.nbrMax =  lineSize-getVar(exclude, lineSize,  modelList.pos*9);
            int rest = listSize - (index+max);
            max += modelList.nbrMax;
            if(modelList.nbrMax >= rest){
                modelList.nbrMax = rest;
                break;
            }
        }
        ModelList[] modelLists = modelList.clone();
        Arrays.sort(modelLists);
        for(int i = 0; i < modelLists.length; i++){
            if(i == 0){
                modelLists[i].index = index;
            } else {
                modelLists[i].index = modelLists[i-1].nbrMax+modelLists[i-1].index;
            }
        }
    }

    /**
     * Gestion des menu dynamique :: lineSize : lineSize == 7 || 9 -- exclude : exclude.lenght/line < lineSize
     * @param pos int
     * @param index int
     * @param list ArrayList<Item>
     * @param lineSize int
     * @param exclude int[]
     * @return boolean
     */
    boolean list(int pos, int index, final ArrayList<Item> list , final int lineSize, final int[] exclude){
        place.clear();
        if (this.modelList != null ? this.modelList.length > pos : pos < menu.getSize()-9){
            int i = this.modelList != null ? this.modelList[pos].pos*9 : pos*9;
            index = this.modelList != null ? this.modelList[pos].index : index;
            if (index + lineSize - getVar(exclude, lineSize, i) < list.size()) {
                i += ((9-lineSize) / 2 + ((lineSize & 1) == 0 ? 1 : 0));
                do {
                    if(!content(i, exclude)){
                        menu.setItem(i, list.get(index++).get());
                    }
                } while (i++%9 !=  9 + (((9 - lineSize) / 2 + (lineSize & 1) == 0 ? 1 : 0) + lineSize - (lineSize == 9 ? 10 : 9)));
            } else {
                int rest = list.size() - index;
                if(this.modelList != null){
                    for(int e = -rest / 2; e <= rest/2 && rest > 1; e++){
                        if (e == 0) {
                            if((rest & 1) == 1){
                                menu.setItem(placeExclude(i+4, exclude, true), list.get(index++).get());
                            }
                        } else {
                            menu.setItem(placeExclude((i + 4) + e, exclude, false), list.get(index++).get());
                        }
                    }
                    if ((rest & 1) == 1 && rest == 1) {
                        menu.setItem(placeExclude(i + 4, exclude, true), list.get(index).get());
                    }
                } else {
                    i += ((9-lineSize) / 2 + ((lineSize & 1) == 0 ? 1 : 0));
                    while (index < list.size()){
                        if(!content(i, exclude)){
                            menu.setItem(i, list.get(index++).get());
                        }
                        i++;
                    }
                }
                return false;
            }
            return list(++pos, index, list, lineSize, exclude);
        } else {
            return true;
        }
    }

    protected abstract void interractInventory(InventoryClickEvent e);

    @EventHandler
    public void onInterractInventory(InventoryClickEvent e){
        if(e.getInventory().equals(menu) && e.getCurrentItem() != null){
            interractInventory(e);
        }
    }

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent e){
        if(e.getInventory().equals(menu)){
            HandlerList.unregisterAll(this);
        }
    }

    public enum Type{
        DYNAMIQUE,
        STATIC;
    }

    private static class ModelList implements Comparable<ModelList>{
        private int pos;
        private int index;
        private int nbrMax;

        public int compareTo(ModelList modelList){
            return this.pos >= modelList.pos ? 1 : -1;
        }

        private ModelList(int pos){
            this.pos = pos;
        }
    }
}
