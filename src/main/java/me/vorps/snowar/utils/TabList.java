package me.vorps.snowar.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class TabList {
    public TabList() {
    }

    public static void setPlayerList(Player player, String header, String footer) {
        IChatBaseComponent hj = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
        IChatBaseComponent fj = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
        PacketPlayOutPlayerListHeaderFooter packet = (PacketPlayOutPlayerListHeaderFooter) constructHeaderAndFooterPacket(hj, fj);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

    private static Object constructHeaderAndFooterPacket(Object header, Object footer) {
        try {
            Object e = PacketPlayOutPlayerListHeaderFooter.class.newInstance();
            Field field;
            if(header != null) {
                field = PacketPlayOutPlayerListHeaderFooter.class.getDeclaredField("a");
                field.setAccessible(true);
                field.set(e, header);
                field.setAccessible(false);
            }

            if(footer != null) {
                field = PacketPlayOutPlayerListHeaderFooter.class.getDeclaredField("b");
                field.setAccessible(true);
                field.set(e, footer);
                field.setAccessible(false);
            }

            return e;
        } catch (IllegalAccessException | NoSuchFieldException | InstantiationException var4) {
            var4.printStackTrace();
            return null;
        }
    }
}