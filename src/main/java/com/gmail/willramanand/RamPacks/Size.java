package com.gmail.willramanand.RamPacks;

import org.bukkit.entity.Player;

public enum Size {

    NONE(0, "No Backpack", 0),
    SMALL(9, "Small Backpack", 100),
    MEDIUM(18, "Medium Backpack", 1000),
    LARGE(27, "Large Backpack", 10000),
    X_LARGE(36, "X-Large Backpack", 100000),
    GIGANTIC(54, "Gigantic Backpack", 1000000),

    ;

    private final int slot;
    private final String name;
    private final int price;

    Size(int slot, String name, int price) {
        this.slot = slot;
        this.name = name;
        this.price = price;
    }

    public int getSlot() {return slot;}

    public String getName() {return name;}

    public int getPrice() { return price; }

    public static Size matchSize(String value) {
        for (Size size : values()) {
            if (size.name().equalsIgnoreCase(value)) return size;
        }
        return null;
    }
}
