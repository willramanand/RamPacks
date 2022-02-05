package com.gmail.willramanand.RamPacks.gui;

import com.gmail.willramanand.RamPacks.RamPacks;
import com.gmail.willramanand.RamPacks.config.Size;
import com.gmail.willramanand.RamPacks.player.PackPlayer;
import com.gmail.willramanand.RamPacks.utils.ColorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BackpackScreen implements InventoryHolder {

    private final RamPacks plugin;
    private final Player player;
    private final PackPlayer packPlayer;
    private final Size size;
    private final Inventory inv;


    public BackpackScreen(RamPacks plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.packPlayer = plugin.getPlayerManager().getPlayerData(player);
        this.size = packPlayer.size();
        this.inv = Bukkit.createInventory(this, size.getSlot(), Component.text(ColorUtils.colorMessage("&b" + size.getName())));
        init();
    }

    private void init() {
        for (int i = 0; i < size.getSlot(); i++) {
            ItemStack item = packPlayer.getItem(i);
            if (item == null) continue;
            inv.setItem(i, item);
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}
