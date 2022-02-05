package com.gmail.willramanand.RamPacks.player;

import com.gmail.willramanand.RamPacks.RamPacks;
import com.gmail.willramanand.RamPacks.Size;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PackPlayer {

    private final RamPacks plugin;

    private final Player player;
    private final UUID uuid;

    private final Map<Integer, ItemStack> backpack;
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
        this.backpack = new HashMap<>();
        this.boughtBackpacks = new HashMap<>();
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setItem(int slot, ItemStack item) {
        this.backpack.put(slot, item);
    }

    public ItemStack getItem(int slot) {
        return backpack.get(slot);
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

}
