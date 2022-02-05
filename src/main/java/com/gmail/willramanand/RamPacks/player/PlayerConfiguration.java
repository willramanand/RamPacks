package com.gmail.willramanand.RamPacks.player;

import com.gmail.willramanand.RamPacks.RamPacks;
import com.gmail.willramanand.RamPacks.config.Size;
import com.gmail.willramanand.RamPacks.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class PlayerConfiguration {

    private final RamPacks plugin;
    public PlayerConfiguration(RamPacks plugin) {
        this.plugin = plugin;
    }

    public void setup(Player player) {
        File dir = new File(plugin.getDataFolder() + "/playerdata/");
        if (!dir.exists()) {
            dir.mkdir();
        }

        File file = new File(plugin.getDataFolder() + "/playerdata/" + player.getUniqueId() + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
                Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&2Created player config for UUID: " + player.getUniqueId()));

                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                PackPlayer packPlayer = new PackPlayer(plugin, player);

                if (file.exists()) {
                    config.set("size", Size.NONE.name());

                    for (Size size : Size.values()) {
                        if (size == Size.NONE) continue;
                        config.set("bought." + size.name().toLowerCase(), false);
                        packPlayer.setBought(size, false);
                    }

                    packPlayer.size(Size.NONE);
                    plugin.getPlayerManager().addPlayerData(packPlayer);
                    try {
                        config.save(file);
                    } catch (IOException e) {
                        Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&4Could not save player config for UUID: " + player.getUniqueId()));
                    }
                }
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&4Could not create player config for UUID: " + player.getUniqueId()));
            }
        }
    }

    public void load(Player player) {
        File file = new File(plugin.getDataFolder() + "/playerdata/" + player.getUniqueId() + ".yml");

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        PackPlayer packPlayer = new PackPlayer(plugin, player);

        if (file.exists()) {
            Size size = Size.matchSize(config.getString("size"));
            packPlayer.size(size);

            for (int i = 0; i < packPlayer.size().getSlot(); i++) {
                ItemStack item = config.getItemStack("items." + i);
                if (item == null) continue;
                packPlayer.setItem(i, item);
            }

            for (Size sizes : Size.values()) {
                if (sizes == Size.NONE) continue;
                boolean isBought = config.getBoolean("bought." + sizes.name().toLowerCase());
                packPlayer.setBought(sizes, isBought);
            }

            plugin.getPlayerManager().addPlayerData(packPlayer);
        } else {
            Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&bCould not load player config for UUID: " + player.getUniqueId()));
            setup(player);
        }
    }

    public void save(Player player, boolean isShutdown) {
        File file = new File(plugin.getDataFolder() + "/playerdata/" + player.getUniqueId() + ".yml");
        PackPlayer packPlayer = plugin.getPlayerManager().getPlayerData(player);

        if (packPlayer == null) return;
        if (packPlayer.shouldNotSave()) return;
        if (packPlayer.isSaving()) return;
        packPlayer.setSaving(true);

        if (file.exists()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            try {
                if (packPlayer.size() == Size.NONE) return;
                config.set("size", packPlayer.size().name());

                config.set("items", null);
                for (int i = 0; i < packPlayer.size().getSlot(); i++) {
                    ItemStack item = packPlayer.getItem(i);
                    if (item == null) continue;
                    config.set("items." + i, item);
                }

                for (Size size : Size.values()) {
                    if (size == Size.NONE) continue;
                    config.set("bought." + size.name().toLowerCase(), packPlayer.getBought(size));
                }

                config.save(file);
                if (isShutdown) {
                    plugin.getPlayerManager().removePlayerData(player.getUniqueId());
                }
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&bCould not save player config for UUID: " + player.getUniqueId()));
            }
        } else {
            Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&bCould not save player config for UUID: " + player.getUniqueId() + " because it does not exist!"));
        }
        packPlayer.setSaving(false);
    }

}
