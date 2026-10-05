package com.grim3212.assorted.machines.common.items;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.lib.registry.IRegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class MachinesItems {

    public static final IRegistryObject<Item> BRONZE_INGOT = register("bronze_ingot", props -> new Item(props));
    public static final IRegistryObject<Item> ELECTRUM_INGOT = register("electrum_ingot", props -> new Item(props));
    public static final IRegistryObject<Item> INVAR_INGOT = register("invar_ingot", props -> new Item(props));
    public static final IRegistryObject<Item> STEEL_INGOT = register("steel_ingot", props -> new Item(props));

    public static final IRegistryObject<Item> BRONZE_NUGGET = register("bronze_nugget", props -> new Item(props));
    public static final IRegistryObject<Item> ELECTRUM_NUGGET = register("electrum_nugget", props -> new Item(props));
    public static final IRegistryObject<Item> INVAR_NUGGET = register("invar_nugget", props -> new Item(props));
    public static final IRegistryObject<Item> STEEL_NUGGET = register("steel_nugget", props -> new Item(props));

    public static final IRegistryObject<Item> COPPER_DUST = register("copper_dust", props -> new Item(props));
    public static final IRegistryObject<Item> BRONZE_DUST = register("bronze_dust", props -> new Item(props));
    public static final IRegistryObject<Item> ELECTRUM_DUST = register("electrum_dust", props -> new Item(props));
    public static final IRegistryObject<Item> INVAR_DUST = register("invar_dust", props -> new Item(props));
    public static final IRegistryObject<Item> STEEL_DUST = register("steel_dust", props -> new Item(props));
    public static final IRegistryObject<Item> IRON_DUST = register("iron_dust", props -> new Item(props));
    public static final IRegistryObject<Item> GOLD_DUST = register("gold_dust", props -> new Item(props));

    public static final IRegistryObject<Item> COPPER_GEAR = register("copper_gear", props -> new Item(props));
    public static final IRegistryObject<Item> BRONZE_GEAR = register("bronze_gear", props -> new Item(props));
    public static final IRegistryObject<Item> ELECTRUM_GEAR = register("electrum_gear", props -> new Item(props));
    public static final IRegistryObject<Item> INVAR_GEAR = register("invar_gear", props -> new Item(props));
    public static final IRegistryObject<Item> STEEL_GEAR = register("steel_gear", props -> new Item(props));
    public static final IRegistryObject<Item> IRON_GEAR = register("iron_gear", props -> new Item(props));
    public static final IRegistryObject<Item> GOLD_GEAR = register("gold_gear", props -> new Item(props));

    private static <T extends Item> IRegistryObject<T> register(final String name, final Function<Item.Properties, ? extends T> factory) {
        // Since 1.21.2 every item has to know its own id before it is constructed, so the
        // properties are built here where the registration name is known.
        final ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
        return MachinesBlocks.ITEMS.register(name, () -> factory.apply(new Item.Properties().setId(key)));
    }

    public static void init() {
    }
}
