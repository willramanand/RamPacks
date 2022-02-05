package com.gmail.willramanand.RamPacks.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import com.gmail.willramanand.RamPacks.RamPacks;
import com.gmail.willramanand.RamPacks.config.Size;
import com.gmail.willramanand.RamPacks.gui.BuyScreen;
import com.gmail.willramanand.RamPacks.player.PackPlayer;
import com.gmail.willramanand.RamPacks.utils.ColorUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("backpack|bp")
@Description("Base RamPacks Command")
public class BackpackCommand extends BaseCommand {

    private final RamPacks plugin;

    public BackpackCommand(RamPacks plugin) {
        this.plugin = plugin;
    }

    @Default
    @Description("Open backpack")
    public void openBackpack(Player player) {
        PackPlayer packPlayer = plugin.getPlayerManager().getPlayerData(player);
        if (packPlayer.size() == Size.NONE) {
            player.sendMessage(ColorUtils.colorMessage("&cYou do not have a backpack! Use &d/bp buy&c."));
            return;
        }
        player.openInventory(packPlayer.getInventory());
    }

    @Subcommand("buy")
    @Description("Buy backpacks")
    public void buyBackpack(Player player) {
        BuyScreen buyScreen = new BuyScreen(plugin, player);
        player.openInventory(buyScreen.getInventory());
    }

    @Subcommand("admin")
    @CommandPermission("backpack.admin")
    @CommandCompletion("@players")
    @Description("Admin command for viewing other players backpacks.")
    public void adminBackpack(CommandSender sender, @Flags("other") Player player) {
        PackPlayer packPlayer = plugin.getPlayerManager().getPlayerData(player);

        if (packPlayer.size() == Size.NONE) {
            sender.sendMessage(ColorUtils.colorMessage("&cThis user does not have a backpack!"));
            return;
        }

        Player admin = (Player) sender;
        admin.openInventory(packPlayer.getInventory());
    }

    @Subcommand("version|v")
    @Description("Displays plugin version and information")
    public void displayVersion(CommandSender sender) {
        sender.sendMessage(ColorUtils.colorMessage("&6---- &b" + plugin.getName() + "&6----"));
        sender.sendMessage(ColorUtils.colorMessage("&dAuthor: &eWillRam"));
        sender.sendMessage(ColorUtils.colorMessage("&dVersion: &e" + plugin.getDescription().getVersion()));
        sender.sendMessage(ColorUtils.colorMessage("&e" + plugin.getDescription().getDescription()));
    }


    @Subcommand("help|h")
    public void onHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }
}
