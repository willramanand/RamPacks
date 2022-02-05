package com.gmail.willramanand.RamPacks.listeners;

import com.gmail.willramanand.RamPacks.RamPacks;
import com.gmail.willramanand.RamPacks.Size;
import com.gmail.willramanand.RamPacks.gui.BackpackScreen;
import com.gmail.willramanand.RamPacks.gui.BuyScreen;
import com.gmail.willramanand.RamPacks.gui.items.InventoryItem;
import com.gmail.willramanand.RamPacks.player.PackPlayer;
import com.gmail.willramanand.RamPacks.utils.ColorUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class GUIListener implements Listener {

    private final RamPacks plugin;

    public GUIListener(RamPacks plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void saveBackpack(InventoryCloseEvent event) {
        if (event.getInventory() == null) return;
        if (event.getInventory().getHolder() instanceof BackpackScreen) {
            Player player = (Player) event.getPlayer();
            PackPlayer packPlayer = plugin.getPlayerManager().getPlayerData(player);

            int i = 0;
            for (ItemStack item : event.getInventory().getContents()) {
                packPlayer.setItem(i, item);
                i++;
            }
        }
    }

    @EventHandler
    public void buyBackpack(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getHolder() instanceof BuyScreen) {
            Player player = (Player) e.getWhoClicked();
            PackPlayer packPlayer = plugin.getPlayerManager().getPlayerData(player);

            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "bought")));
            if (e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "price"))) {
                int price = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "price"), PersistentDataType.INTEGER);
                if (!(RamPacks.getEconomy().hasAccount(player)) || !(RamPacks.getEconomy().has(player, price))) {
                    player.sendMessage(ColorUtils.colorMessage("&4You do not have enough currency to buy that!"));
                    return;
                }
                Size size = Size.matchSize(e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "size"), PersistentDataType.STRING));
                RamPacks.getEconomy().withdrawPlayer(player, price);

                player.sendMessage(ColorUtils.colorMessage("&eYou bought the &d" + size.getName() + "&efor &d$" + size.getPrice()));

                packPlayer.setBought(size, true);
                packPlayer.size(size);

                Inventory inv = e.getClickedInventory();
                int k = 2;
                for (Size sizes : Size.values()) {
                    if (sizes == Size.NONE) continue;
                    inv.setItem(k, InventoryItem.getBackpackItem(player, sizes));
                    k++;
                }
                inv.setItem(0, InventoryItem.getHead(player));
            } else if (e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "close_button"))) {
                player.closeInventory();
            }
        }
    }
}
