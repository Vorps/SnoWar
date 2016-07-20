package me.vorps.snowar.utils;

import lombok.Getter;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class EntityManager {
	
	private static @Getter Entity entity;

	private static void disableAI(Entity entity) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        nmsEntity.f(tag);
    }
	
	public static void entityManager(EntityType ent, Location locEnt, String name, Villager.Profession profession){
		Villager entity = (Villager)Bukkit.getWorlds().get(0).spawnEntity(locEnt, ent);
        entity.setCustomName(name);
        entity.setCustomNameVisible(true);
        entity.setProfession(profession);
		disableAI(entity);
	}

    public static void removeEntity(){
        Bukkit.getWorlds().get(0).getEntities().forEach((Entity entity) -> {
            if(entity.getType() == EntityType.VILLAGER){
                entity.remove();
            }
        });
    }
}
