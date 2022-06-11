package com.gmail.willramanand.RamPacks.listeners;

import com.gmail.willramanand.RamPacks.RamPacks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private final RamPacks plugin;

    public PlayerListener(RamPacks plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void join(PlayerLoginEvent event) {
        plugin.getPlayerConfiguration().load(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void leave(PlayerQuitEvent event) {
        plugin.getPlayerConfiguration().save(event.getPlayer(), false);
    }
}
