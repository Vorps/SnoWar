package me.vorps.snowar.menu;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.*;

/**
 * Project Hub Created by Vorps on 01/02/2016 at 01:41.
 */
public class Item {
    private String name;
    private Material material;
    private int amount;
    private boolean hideEnchant;
    private byte data;
    private int id;
    private List<String> lore = new ArrayList<>();
    private Map<Enchantment, Integer> enchantments = new HashMap<>();
    private String skullOwnerName = null;
    private Color color;
    private PotionType potionType = null;
    private short durability;

    {
        this.amount = 1;
        this.data = (byte) 0;
        this.durability = -1;
    }

    public String name(){
        return name;
    }

    public Material getMaterial(){
        return material;
    }

    public int getAmount(){
        return amount;
    }

    public boolean isHideEnchant(){
        return hideEnchant;
    }

    public byte getData(){
        return data;
    }

    public int id(){
        return id;
    }

    public List<String> getLore(){
        return lore;
    }

    public Map<Enchantment, Integer> getEnchantments(){
        return enchantments;
    }

    public String getSkullOwnerName(){
        return skullOwnerName;
    }

    public Color getColor(){
        return color;
    }

    public PotionType getPotionType(){
        return potionType;
    }


    public short getDurability(){
        return durability;
    }


    /**
     * @param material Material
     */
    public Item(Material material) {
        this.material = material;
        this.id = material.getId();
    }

    /**
     * @param potionType PotionType
     */
    public Item(PotionType potionType){
        this.potionType = potionType;
    }

    /**
     * @param id int
     */
    public Item(int id) {
        this.id = id;
        this.material = Material.getMaterial(id);
    }

    /**
     * Clone item
     * @param item Item
     */
    public Item(Item item){
        this.name = item.name;
        this.material = item.material;
        this.amount = item.amount;
        this.hideEnchant = item.hideEnchant;
        this.data = item.data;
        this.id = item.id;
        this.lore = item.lore;
        this.enchantments = item.enchantments;
        this.skullOwnerName = item.skullOwnerName;
        this.color = item.color;
        this.potionType = item.potionType;
        this.durability = item.durability;
    }

    /**
     * Skull Player
     * @param namePlayer String
     */
    public Item(String namePlayer) {
        skullOwnerName = namePlayer;
    }

    /**
     * Color
     * @param color Color
     * @return
     */
    public Item withColor(Color color){
        this.color = color;
        return this;
    }
    /**
     * @param name String
     * @return Item
     */
    public Item withName(String name) {
        this.name = name;
        return this;
    }
    /**
     * @param amount int
     * @return Item
     */
    public Item withAmount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * @param hideEnchantement boolean
     * @return Item
     */
    public Item hideEnchant(boolean hideEnchantement) {
        this.hideEnchant = hideEnchantement;
        return this;
    }

    /**
     *
     * @param durability
     * @return
     */
    public Item withDurability(int durability){
        this.durability = (short) durability;
        return this;
    }
    /**
     * @param data byte
     * @return Item
     */
    public Item withData(byte data) {
        this.data = data;
        return this;
    }

    /**
     * @param lore String[]
     * @return Item
     */
    public Item withLore(String[] lore) {
        this.lore = Arrays.asList(lore);
        return this;
    }

    /**
     * @param enchant Enchantment
     * @param level int
     * @return Item
     */
    public Item withEnchant(Enchantment enchant, int level) {
        this.enchantments.put(enchant, level);
        return this;
    }

    /**
     * Return the item.
     * @return ItemStack
     */
    public ItemStack get() {
        ItemStack item;
        if(color != null){
            item = new ItemStack(this.material, 1);
            LeatherArmorMeta lam = (LeatherArmorMeta) item.getItemMeta();
            lam.setColor(this.color);
            item.setItemMeta(lam);
            if(this.name != null) {
                lam.setDisplayName(this.name);
            }
            if(this.enchantments.size() > 0)
                for(Map.Entry<Enchantment, Integer> enchant : this.enchantments.entrySet())
                    lam.addEnchant(enchant.getKey(), enchant.getValue(), true);
            if(this.hideEnchant)
                lam.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            lam.setLore(this.lore);
            item.setItemMeta(lam);
        } else {
            if(skullOwnerName != null){
                item = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

                SkullMeta sm = (SkullMeta)item.getItemMeta();
                sm.setOwner(skullOwnerName);
                if(this.name != null) {
                    sm.setDisplayName(this.name);
                }
                if(this.enchantments.size() > 0)
                    for(Map.Entry<Enchantment, Integer> enchant : this.enchantments.entrySet())
                        sm.addEnchant(enchant.getKey(), enchant.getValue(), true);
                if(this.hideEnchant)
                    sm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                sm.setLore(this.lore);
                item.setItemMeta(sm);
            } else {
                if(id > 0){
                    item = new ItemStack(this.id);
                } else {
                    if(potionType != null){
                        item = new Potion(potionType).toItemStack(1);
                    } else {
                        item = new ItemStack(this.material);
                    }
                }
                item.setDurability((short) this.data);
                ItemMeta meta = item.getItemMeta();
                meta.setLore(this.lore);
                if(this.name != null) {
                    meta.setDisplayName(this.name);
                }
                if(this.enchantments.size() > 0)
                    for(Map.Entry<Enchantment, Integer> enchant : this.enchantments.entrySet())
                        meta.addEnchant(enchant.getKey(), enchant.getValue(), true);
                if(this.hideEnchant)
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                item.setItemMeta(meta);
            }
        }
        item.setAmount(this.amount);
        if(durability != -1){
            item.setDurability((short)  (item.getType().getMaxDurability()-durability));
        }
        return item;
    }
}