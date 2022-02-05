package com.gmail.willramanand.RamPacks.player;

import com.gmail.willramanand.RamPacks.RamPacks;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    private final RamPacks plugin;
    private final ConcurrentHashMap<UUID, PackPlayer> playerData;

    public PlayerManager(RamPacks plugin) {
        this.plugin = plugin;
        this.playerData = new ConcurrentHashMap<>();
        startAutoSave();
    }


    @Nullable
    public PackPlayer getPlayerData(Player player) {
        return playerData.get(player.getUniqueId());
    }

    @Nullable
    public PackPlayer getPlayerData(UUID id) {
        return this.playerData.get(id);
    }

    public void addPlayerData(@NotNull PackPlayer packPlayer) {
        this.playerData.put(packPlayer.getUuid(), packPlayer);
    }

    public void removePlayerData(UUID id) {
        this.playerData.remove(id);
    }

    public boolean hasPlayerData(Player player) {
        return playerData.containsKey(player.getUniqueId());
    }

    public ConcurrentHashMap<UUID, PackPlayer> getPlayerDataMap() {
        return playerData;
    }

    public void startAutoSave() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    PackPlayer packPlayer = plugin.getPlayerManager().getPlayerData(player);
                    if (packPlayer != null && !packPlayer.isSaving()) {
                        plugin.getPlayerConfiguration().save(packPlayer.getPlayer(), false);
                    }
                }
            }
        }.runTaskTimer(plugin, 6000L, 6000L);
    }
}
