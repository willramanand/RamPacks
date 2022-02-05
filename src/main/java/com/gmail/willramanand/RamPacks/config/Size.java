package com.gmail.willramanand.RamPacks.config;

public enum Size {

    NONE(0, "No Backpack"),
    SMALL(9, "Small Backpack"),
    MEDIUM(18, "Medium Backpack"),
    LARGE(27, "Large Backpack"),
    X_LARGE(36, "X-Large Backpack"),
    GIGANTIC(54, "Gigantic Backpack"),

    ;

    private final int slot;
    private final String name;

    Size(int slot, String name) {
        this.slot = slot;
        this.name = name;
    }

    public int getSlot() {return slot;}

    public String getName() {return name;}

    public static Size matchSize(String value) {
        for (Size size : values()) {
            if (size.name().equalsIgnoreCase(value)) return size;
        }
        return null;
    }
}
