package com.grim3212.assorted.ores.common.items;

import com.grim3212.assorted.ores.Constants;
import com.grim3212.assorted.ores.common.blocks.OresBlocks;
import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.lib.registry.IRegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.function.Function;

public class OresItems {

    public static final IRegistryObject<Item> RUBY = register("ruby", props -> new Item(props));
    public static final IRegistryObject<Item> PERIDOT = register("peridot", props -> new Item(props));
    public static final IRegistryObject<Item> SAPPHIRE = register("sapphire", props -> new Item(props));
    public static final IRegistryObject<Item> TOPAZ = register("topaz", props -> new Item(props));

    public static final IRegistryObject<Item> TIN_INGOT = register("tin_ingot", props -> new Item(props));
    public static final IRegistryObject<Item> SILVER_INGOT = register("silver_ingot", props -> new Item(props));
    public static final IRegistryObject<Item> ALUMINUM_INGOT = register("aluminum_ingot", props -> new Item(props));
    public static final IRegistryObject<Item> NICKEL_INGOT = register("nickel_ingot", props -> new Item(props));
    public static final IRegistryObject<Item> PLATINUM_INGOT = register("platinum_ingot", props -> new Item(props));
    public static final IRegistryObject<Item> LEAD_INGOT = register("lead_ingot", props -> new Item(props));

    public static final IRegistryObject<Item> TIN_NUGGET = register("tin_nugget", props -> new Item(props));
    public static final IRegistryObject<Item> SILVER_NUGGET = register("silver_nugget", props -> new Item(props));
    public static final IRegistryObject<Item> ALUMINUM_NUGGET = register("aluminum_nugget", props -> new Item(props));
    public static final IRegistryObject<Item> NICKEL_NUGGET = register("nickel_nugget", props -> new Item(props));
    public static final IRegistryObject<Item> PLATINUM_NUGGET = register("platinum_nugget", props -> new Item(props));
    public static final IRegistryObject<Item> LEAD_NUGGET = register("lead_nugget", props -> new Item(props));

    public static final IRegistryObject<Item> RAW_TIN = register("raw_tin", props -> new Item(props));
    public static final IRegistryObject<Item> RAW_SILVER = register("raw_silver", props -> new Item(props));
    public static final IRegistryObject<Item> RAW_ALUMINUM = register("raw_aluminum", props -> new Item(props));
    public static final IRegistryObject<Item> RAW_NICKEL = register("raw_nickel", props -> new Item(props));
    public static final IRegistryObject<Item> RAW_PLATINUM = register("raw_platinum", props -> new Item(props));
    public static final IRegistryObject<Item> RAW_LEAD = register("raw_lead", props -> new Item(props));

    public static final IRegistryObject<Item> TIN_DUST = register("tin_dust", props -> new Item(props));
    public static final IRegistryObject<Item> SILVER_DUST = register("silver_dust", props -> new Item(props));
    public static final IRegistryObject<Item> ALUMINUM_DUST = register("aluminum_dust", props -> new Item(props));
    public static final IRegistryObject<Item> NICKEL_DUST = register("nickel_dust", props -> new Item(props));
    public static final IRegistryObject<Item> PLATINUM_DUST = register("platinum_dust", props -> new Item(props));
    public static final IRegistryObject<Item> LEAD_DUST = register("lead_dust", props -> new Item(props));

    public static final IRegistryObject<Item> TIN_GEAR = register("tin_gear", props -> new Item(props));
    public static final IRegistryObject<Item> SILVER_GEAR = register("silver_gear", props -> new Item(props));
    public static final IRegistryObject<Item> ALUMINUM_GEAR = register("aluminum_gear", props -> new Item(props));
    public static final IRegistryObject<Item> NICKEL_GEAR = register("nickel_gear", props -> new Item(props));
    public static final IRegistryObject<Item> PLATINUM_GEAR = register("platinum_gear", props -> new Item(props));
    public static final IRegistryObject<Item> LEAD_GEAR = register("lead_gear", props -> new Item(props));

    /** Ores had its own copper nugget until vanilla added one, so old copper nuggets turn into the vanilla one. */
    static {
        for (String namespace : List.of(Constants.FAMILY_ID, Constants.MOD_ID)) {
            Services.REGISTRY_FACTORY.alias(Registries.ITEM, Identifier.fromNamespaceAndPath(namespace, "copper_nugget"), Identifier.withDefaultNamespace("copper_nugget"));
        }
    }

    private static <T extends Item> IRegistryObject<T> register(final String name, final Function<Item.Properties, ? extends T> factory) {
        // Since 1.21.2 every item has to know its own id before it is constructed, so the
        // properties are built here where the registration name is known.
        final ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
        return OresBlocks.ITEMS.register(name, () -> factory.apply(new Item.Properties().setId(key)));
    }

    public static void init() {
    }
}
