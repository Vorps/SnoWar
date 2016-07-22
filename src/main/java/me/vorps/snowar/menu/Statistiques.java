package me.vorps.snowar.menu;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.objects.Stats;
import me.vorps.snowar.utils.ConvertMillis;
import me.vorps.snowar.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class Statistiques extends Menu{

	public Statistiques(Player player){
        super(null, null, null);
		Stats stat = new Stats(player.getUniqueId());
        PlayerData playerData = PlayerData.getPlayerData(player.getName());
        menu = Bukkit.createInventory(player, 9, Lang.getMessage("SNO_WAR.INVENTORY.STATS.TITLE", playerData.getLang()));

        menu.setItem(0, new Item(Material.ANVIL).withName(Lang.getMessage("SNO_WAR.INVENTORY.STATS.VICTORY", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+stat.getVictory()))).withEnchant(Enchantment.ARROW_DAMAGE, 1).hideEnchant(true).get());
        menu.setItem(1, new Item(Material.ANVIL).withName(Lang.getMessage("SNO_WAR.INVENTORY.STATS.DEFEAT", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+stat.getDefeat()))).get());

        menu.setItem(3, new Item(Material.SNOW_BALL).withName(Lang.getMessage("SNO_WAR.INVENTORY.STATS.BALL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.INVENTORY.STATS.BALL.LORE.1", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+stat.getBallShot())), Lang.getMessage("SNO_WAR.INVENTORY.STATS.BALL.LORE.2", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+stat.getBallTouch())),  Lang.getMessage("SNO_WAR.INVENTORY.STATS.BALL.LORE.3", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+stat.getBallShot()/stat.getBallTouch()))}).get());

        menu.setItem(4, new Item(Material.IRON_SWORD).withName(Lang.getMessage("SNO_WAR.INVENTORY.STATS.KILL", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+stat.getKill()))).get());
        menu.setItem(5, new Item(Material.SKULL_ITEM).withName(Lang.getMessage("SNO_WAR.INVENTORY.STATS.DEATH", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+stat.getDead()))).get());

        menu.setItem(7, new Item(Material.GOLD_INGOT).withName(Lang.getMessage("SNO_WAR.INVENTORY.STATS.BONUS", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+stat.getBonus()))).get());
        menu.setItem(8, new Item(Material.WATCH).withName(Lang.getMessage("SNO_WAR.INVENTORY.STATS.TIME", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ConvertMillis.convertMillisToTime((stat.getTime()*1000))))).get());

        player.openInventory(menu);
	}

    @Override
    public void interractInventory(InventoryClickEvent e) {
    }

}
