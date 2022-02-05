package com.gmail.willramanand.RamPacks.player;

import com.gmail.willramanand.RamPacks.RamPacks;
import com.gmail.willramanand.RamPacks.config.Size;
import com.gmail.willramanand.RamPacks.utils.ColorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PackPlayer implements InventoryHolder {

    private final RamPacks plugin;

    private final Player player;
    private final UUID uuid;

    private Inventory backpack;
    private final Map<Size, Boolean> boughtBackpacks;

    private Size backpackSize;

    private boolean saving;
    private boolean shouldSave;

    public PackPlayer(RamPacks plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.uuid = this.player.getUniqueId();
        this.saving = false;
        this.shouldSave = true;
        this.boughtBackpacks = new HashMap<>();
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setItem(int slot, ItemStack item) {
        this.backpack.setItem(slot, item);
    }

    public ItemStack getItem(int slot) {
        return backpack.getItem(slot);
    }

    public void setBought(Size size, boolean value) {
        if (value) {
            this.boughtBackpacks.put(size, true);
            if (size == Size.GIGANTIC) {
                this.boughtBackpacks.put(Size.X_LARGE, true);
                this.boughtBackpacks.put(Size.LARGE, true);
                this.boughtBackpacks.put(Size.MEDIUM, true);
                this.boughtBackpacks.put(Size.SMALL, true);
            } else if (size == Size.X_LARGE) {
                this.boughtBackpacks.put(Size.LARGE, true);
                this.boughtBackpacks.put(Size.MEDIUM, true);
                this.boughtBackpacks.put(Size.SMALL, true);
            } else if (size == Size.LARGE) {
                this.boughtBackpacks.put(Size.MEDIUM, true);
                this.boughtBackpacks.put(Size.SMALL, true);
            } else if (size == Size.MEDIUM) {
                this.boughtBackpacks.put(Size.SMALL, true);
            }
        } else {
            this.boughtBackpacks.put(size, false);
        }
    }

    public boolean getBought(Size size) {
        return this.boughtBackpacks.get(size);
    }

    public void size(Size size) {
        backpackSize = size;
        convertBackpack(size);
    }

    public Size size() {
        return backpackSize;
    }

    public boolean isSaving() {
        return saving;
    }

    public void setSaving(boolean saving) {
        this.saving = saving;
    }

    public boolean shouldNotSave() {
        return !shouldSave;
    }

    public void setShouldSave(boolean shouldSave) {
        this.shouldSave = shouldSave;
    }

    public void convertBackpack(Size newSize) {
        if (newSize == null || newSize == Size.NONE) return;
        Inventory newBackpack = Bukkit.createInventory(this, newSize.getSlot(), Component.text(ColorUtils.colorMessage("&b" + player.getName() + "'s Backpack")));

        if (backpack != null) {
            for (int i = 0; i < backpack.getContents().length; i++) {
                newBackpack.setItem(i, backpack.getItem(i));
            }
        }
            backpack = newBackpack;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return backpack;
    }
}
