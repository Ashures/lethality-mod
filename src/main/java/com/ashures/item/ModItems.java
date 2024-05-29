package com.ashures.item;

import com.ashures.Lethality;
import com.ashures.item.custom.AssassinBladeItem;
import com.ashures.item.custom.DragonLanceItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item KATANA = registerItem("katana", new SwordItem(ToolMaterials.NETHERITE, 2, -2.0f, new FabricItemSettings()));
    public static final Item ASSASSIN_BLADE = registerItem("assassin_blade", new AssassinBladeItem(ToolMaterials.NETHERITE, 2, -2.0f, 40, new FabricItemSettings()));
    public static final Item DRACONIC_RIFTSTEEL = registerItem("draconic_riftsteel", new Item(new FabricItemSettings()));
    public static final Item DRAGON_LANCE = registerItem("dragon_lance", new DragonLanceItem(ToolMaterials.NETHERITE, 12, -3.2f, 200, new FabricItemSettings()));

    // To add a different type of item (for example, Ingredient) create a new method for that new type.
    private static void addItemsToCombatItemGroup(FabricItemGroupEntries entries) {
        entries.add(KATANA);
        entries.add(ASSASSIN_BLADE);
        entries.add(DRAGON_LANCE);
    }

    private static void addItemsToIngredientsItemGroup(FabricItemGroupEntries entries) {
        entries.add(DRACONIC_RIFTSTEEL);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Lethality.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Lethality.LOGGER.info("Registering Mod Items for " + Lethality.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemsToCombatItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientsItemGroup);
    }
}
