package me.vorps.snowar.menu;

import me.vorps.snowar.PlayerData;
import me.vorps.snowar.objects.Stats;
import me.vorps.syluriapi.lang.Lang;
import me.vorps.syluriapi.menu.Item;
import me.vorps.syluriapi.menu.Menu;
import me.vorps.syluriapi.utils.ConvertMillis;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class Statistiques extends Menu{

	public Statistiques(final Player player){
        super(null, null, null);
		Stats stat = new Stats(player.getUniqueId());
        PlayerData playerData = PlayerData.getPlayerData(player.getName());
        super.menu = Bukkit.createInventory(player, 9, Lang.getMessage("SNO_WAR.INVENTORY.STATS.TITLE", playerData.getLang()));
        super.menu.setItem(0, new Item(Material.ANVIL).withName(Lang.getMessage("SNO_WAR.INVENTORY.STATS.VICTORY", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+stat.getVictory()))).withEnchant(Enchantment.ARROW_DAMAGE, 1).hideEnchant(true).get());
        super.menu.setItem(1, new Item(Material.ANVIL).withName(Lang.getMessage("SNO_WAR.INVENTORY.STATS.DEFEAT", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+stat.getDefeat()))).get());
        super.menu.setItem(3, new Item(Material.SNOW_BALL).withName(Lang.getMessage("SNO_WAR.INVENTORY.STATS.BALL", playerData.getLang())).withLore(new String[] {Lang.getMessage("SNO_WAR.INVENTORY.STATS.BALL.LORE.1", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+stat.getBallShot())), Lang.getMessage("SNO_WAR.INVENTORY.STATS.BALL.LORE.2", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+stat.getBallTouch())),  Lang.getMessage("SNO_WAR.INVENTORY.STATS.BALL.LORE.3", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+(stat.getBallTouch() != 0 ? stat.getBallShot()/stat.getBallTouch() : 0)))}).get());
        super.menu.setItem(4, new Item(Material.IRON_SWORD).withName(Lang.getMessage("SNO_WAR.INVENTORY.STATS.KILL", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+stat.getKill()))).get());
        super.menu.setItem(5, new Item(Material.SKULL_ITEM).withName(Lang.getMessage("SNO_WAR.INVENTORY.STATS.DEATH", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+stat.getDead()))).get());
        super.menu.setItem(7, new Item(Material.GOLD_INGOT).withName(Lang.getMessage("SNO_WAR.INVENTORY.STATS.BONUS", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ""+stat.getBonus()))).get());
        super.menu.setItem(8, new Item(Material.WATCH).withName(Lang.getMessage("SNO_WAR.INVENTORY.STATS.TIME", playerData.getLang(), new Lang.Args(Lang.Parameter.VAR, ConvertMillis.convertMillisToTime((stat.getTime()*1000))))).get());
        player.openInventory(super.menu);
	}

    @Override
    public void interractInventory(InventoryClickEvent e) {
    }

}
