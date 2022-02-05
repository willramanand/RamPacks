package com.gmail.willramanand.RamPacks.config;

import com.gmail.willramanand.RamPacks.RamPacks;
import com.gmail.willramanand.RamPacks.utils.ColorUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class PriceManager {

    private final static Map<Size, Double> prices = new HashMap<>();

    public static void loadPrices() {
        prices.clear();

        FileConfiguration config = RamPacks.getInstance().getConfig();

        for (Size size : Size.values()) {
            if (size == Size.NONE) continue;
            double price = config.getDouble("prices." + size.name().toLowerCase());
            prices.put(size, price);
        }
        RamPacks.getInstance().getLogger().info(ColorUtils.colorMessage("&eLoaded prices for backpacks."));
    }

    public static double getPrice(Size size) {
        return prices.get(size);
    }
}
