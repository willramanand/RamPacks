package com.gmail.willramanand.RamPacks;

import co.aikar.commands.PaperCommandManager;
import com.gmail.willramanand.RamPacks.commands.BackpackCommand;
import com.gmail.willramanand.RamPacks.config.PriceManager;
import com.gmail.willramanand.RamPacks.listeners.GUIListener;
import com.gmail.willramanand.RamPacks.listeners.PlayerListener;
import com.gmail.willramanand.RamPacks.player.PlayerConfiguration;
import com.gmail.willramanand.RamPacks.player.PlayerManager;
import com.gmail.willramanand.RamPacks.utils.ColorUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class RamPacks extends JavaPlugin {

    private static RamPacks i;
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;

    private PlayerManager playerManager;
    private PlayerConfiguration playerConfiguration;

    private PaperCommandManager commandManager;


    @Override
    public void onEnable() {
        i = this;

        long startTime = System.currentTimeMillis();
        log.info(ColorUtils.colorMessage("[" + this.getName() + "] &6===&b ENABLE START &6==="));

        // Dependencies
        if (isVaultActive()) {
            log.info(ColorUtils.colorMessage("[" + this.getName() + "] &2Enabling Vault integration."));
            setupEconomy();
        }

        // Config
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        playerManager = new PlayerManager(this);
        playerConfiguration = new PlayerConfiguration(this);

        playerManager.startAutoSave();

        PriceManager.loadPrices();

        registerCommands();
        registerEvents();


        startTime = System.currentTimeMillis() - startTime;
        log.info(ColorUtils.colorMessage("[" + this.getName() + "] &6=== &bENABLE &2COMPLETE &6(&eTook &d" + startTime +"ms&6) ==="));
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers())
            playerConfiguration.save(player, true);

        log.info("Disabled");
    }

    public static RamPacks getInstance() {
        return i;
    }

    public boolean isVaultActive() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            log.warning("Vault is not installed, disabling Vault integration!");
            return false;
        }
        return true;
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private void registerEvents() {
        PluginManager pm = this.getServer().getPluginManager();

        pm.registerEvents(new PlayerListener(this), this);
        pm.registerEvents(new GUIListener(this), this);
    }

    private void registerCommands() {
        commandManager = new PaperCommandManager(this);

        commandManager.enableUnstableAPI("help");

        commandManager.registerCommand(new BackpackCommand(this));
    }

    public static Economy getEconomy() {
        return econ;
    }

    public PlayerManager getPlayerManager() { return playerManager; }

    public PlayerConfiguration getPlayerConfiguration() { return playerConfiguration; }

    public PaperCommandManager getCommandManager() { return commandManager; }

}
