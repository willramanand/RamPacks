package com.gmail.willramanand.RamPacks.gui.items;

import com.gmail.willramanand.RamPacks.RamPacks;
import com.gmail.willramanand.RamPacks.config.PriceManager;
import com.gmail.willramanand.RamPacks.config.Size;
import com.gmail.willramanand.RamPacks.player.PackPlayer;
import com.gmail.willramanand.RamPacks.utils.ColorUtils;
import com.gmail.willramanand.RamPacks.utils.Formatter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class InventoryItem {

    public static ItemStack getClose() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(ColorUtils.colorMessage("&4Close")).decoration(TextDecoration.ITALIC, false));
        meta.getPersistentDataContainer().set(new NamespacedKey(RamPacks.getInstance(), "close_button"), PersistentDataType.INTEGER, 0);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getBlank() {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.empty());
        meta.getPersistentDataContainer().set(new NamespacedKey(RamPacks.getInstance(), "empty"), PersistentDataType.INTEGER, 0);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getHead(Player player) {
        PackPlayer packPlayer = RamPacks.getInstance().getPlayerManager().getPlayerData(player);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.displayName(Component.text(player.getName()).decoration(TextDecoration.ITALIC, false));
        skull.setOwningPlayer(offlinePlayer);

        List<Component> lore = new ArrayList<>();
        lore.add(Component.empty());
        lore.add(Component.text(ColorUtils.colorMessage("&6Owned Backpack:")));
        lore.add(Component.text(ColorUtils.colorMessage("&3" + packPlayer.size().getName())));
        skull.lore(lore);

        item.setItemMeta(skull);
        return item;
    }

    public static ItemStack getBackpackItem(Player player, Size size) {
        PackPlayer packPlayer = RamPacks.getInstance().getPlayerManager().getPlayerData(player);
        boolean isBought = packPlayer.getBought(size);
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(ColorUtils.colorMessage(size.getName())).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.empty());

        lore.add(Component.text(ColorUtils.colorMessage("&r&6Price:")));
        if (isBought) {
            lore.add(Component.text(ColorUtils.colorMessage("&r&aBought")));
            meta.getPersistentDataContainer().set(new NamespacedKey(RamPacks.getInstance(), "bought"), PersistentDataType.INTEGER, 1);
        } else {
            lore.add(Component.text(ColorUtils.colorMessage("&r&c" + Formatter.formatMoney(PriceManager.getPrice(size)))));
            meta.getPersistentDataContainer().set(new NamespacedKey(RamPacks.getInstance(), "price"), PersistentDataType.DOUBLE, PriceManager.getPrice(size));
        }
        meta.getPersistentDataContainer().set(new NamespacedKey(RamPacks.getInstance(), "size"), PersistentDataType.STRING, size.name().toLowerCase());

        lore.add(Component.empty());
        lore.add(Component.text(ColorUtils.colorMessage("&r&6Slots: &f" + size.getSlot())));

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
