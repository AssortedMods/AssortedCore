package com.grim3212.assorted.machines.gametest;

import com.google.gson.JsonParser;
import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.machines.Family;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.machines.common.blocks.blockentity.MachinesBlockEntityTypes;
import com.grim3212.assorted.machines.common.crafting.MachinesRecipeSerializers;
import com.grim3212.assorted.machines.common.crafting.MachinesRecipeTypes;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * A world saved when this was all one mod, Assorted Core, still has these machines and metals in it, and other
 * mods' machine recipes still name the old recipe types.
 */
final class AliasTests {

    private AliasTests() {
    }

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("assortedcore_ids_still_load", AliasTests::assortedcoreIdsStillLoad);
    }

    private static void assortedcoreIdsStillLoad(GameTestHelper helper) {
        // Every item, block item or not, registers through MachinesBlocks.ITEMS.
        for (IRegistryObject<Item> item : MachinesBlocks.ITEMS.getEntries()) {
            ItemStack stack = ItemStack.CODEC.parse(helper.getLevel().registryAccess().createSerializationContext(JsonOps.INSTANCE),
                    JsonParser.parseString("{\"id\": \"" + old(item.getId()) + "\", \"count\": 1}")).getOrThrow();
            helper.assertTrue(stack.is(item.get()), "a stack saved as " + old(item.getId()) + " reads back as " + stack);
        }

        for (IRegistryObject<Block> block : MachinesBlocks.BLOCKS.getEntries()) {
            helper.assertValueEqual(BuiltInRegistries.BLOCK.getValue(old(block.getId())), block.get(), "the block saved as " + old(block.getId()));
        }

        for (IRegistryObject<BlockEntityType<?>> type : MachinesBlockEntityTypes.BLOCK_ENTITIES.getEntries()) {
            helper.assertValueEqual(BuiltInRegistries.BLOCK_ENTITY_TYPE.getValue(old(type.getId())), type.get(), "the block entity saved as " + old(type.getId()));
        }

        for (IRegistryObject<RecipeSerializer<?>> serializer : MachinesRecipeSerializers.RECIPE_SERIALIZERS.getEntries()) {
            helper.assertValueEqual(BuiltInRegistries.RECIPE_SERIALIZER.getValue(old(serializer.getId())), serializer.get(), "the recipe serializer named " + old(serializer.getId()));
        }

        for (IRegistryObject<RecipeType<?>> type : MachinesRecipeTypes.RECIPE_TYPES.getEntries()) {
            helper.assertValueEqual(BuiltInRegistries.RECIPE_TYPE.getValue(old(type.getId())), type.get(), "the recipe type named " + old(type.getId()));
        }
        helper.succeed();
    }

    private static Identifier old(Identifier id) {
        return Identifier.fromNamespaceAndPath(Family.ID, id.getPath());
    }
}
