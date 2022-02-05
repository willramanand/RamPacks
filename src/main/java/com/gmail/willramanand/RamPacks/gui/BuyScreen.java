package com.gmail.willramanand.RamPacks.gui;

import com.gmail.willramanand.RamPacks.RamPacks;
import com.gmail.willramanand.RamPacks.config.Size;
import com.gmail.willramanand.RamPacks.gui.items.InventoryItem;
import com.gmail.willramanand.RamPacks.utils.ColorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class BuyScreen implements InventoryHolder {

    private final RamPacks plugin;
    private final Player player;
    private final Inventory inv;

    public BuyScreen(RamPacks plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.inv = Bukkit.createInventory(this, 9, Component.text(ColorUtils.colorMessage("&bBuy Backpacks")));
        init();
    }

    private void init() {
        inv.setItem(0, InventoryItem.getHead(player));

        int k = 2;
        for (Size size : Size.values()) {
            if (size == Size.NONE) continue;
            inv.setItem(k, InventoryItem.getBackpackItem(player, size));
            k++;
        }

        for (int i = 0; i < inv.getContents().length; i++) {
            if (inv.getItem(i) != null) continue;
            inv.setItem(i, InventoryItem.getBlank());
        }

        inv.setItem(8, InventoryItem.getClose());
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}
