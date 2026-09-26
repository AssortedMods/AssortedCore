package com.grim3212.assorted.machines.common.blocks;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.Family;
import com.grim3212.assorted.machines.api.machines.MachineTier;
import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class MachinesBlocks {

    public static final RegistryProvider<Block> BLOCKS = RegistryProvider.create(Registries.BLOCK, Constants.MOD_ID).aliasFrom(Family.ID);
    public static final RegistryProvider<Item> ITEMS = RegistryProvider.create(Registries.ITEM, Constants.MOD_ID).aliasFrom(Family.ID);

    public static final IRegistryObject<Block> BRONZE_BLOCK = register("bronze_block", props -> new Block(props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<Block> ELECTRUM_BLOCK = register("electrum_block", props -> new Block(props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<Block> INVAR_BLOCK = register("invar_block", props -> new Block(props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<Block> STEEL_BLOCK = register("steel_block", props -> new Block(props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));

    public static final IRegistryObject<Block> MACHINE_CORE = register("machine_core", props -> new Block(props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(4.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<AlloyForgeBlock> BASIC_ALLOY_FORGE = register("basic_alloy_forge", props -> new AlloyForgeBlock(MachineTier.BASIC, props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(4.0F, 6.0F).requiresCorrectToolForDrops().lightLevel(getLightValueOn(13))));
    public static final IRegistryObject<AlloyForgeBlock> INTERMEDIATE_ALLOY_FORGE = register("intermediate_alloy_forge", props -> new AlloyForgeBlock(MachineTier.INTERMEDIATE, props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(4.0F, 8.0F).requiresCorrectToolForDrops().lightLevel(getLightValueOn(13))));
    public static final IRegistryObject<AlloyForgeBlock> ADVANCED_ALLOY_FORGE = register("advanced_alloy_forge", props -> new AlloyForgeBlock(MachineTier.ADVANCED, props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 9.0F).requiresCorrectToolForDrops().lightLevel(getLightValueOn(13))));
    public static final IRegistryObject<AlloyForgeBlock> EXPERT_ALLOY_FORGE = register("expert_alloy_forge", props -> new AlloyForgeBlock(MachineTier.EXPERT, props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(6.0F, 10.0F).requiresCorrectToolForDrops().lightLevel(getLightValueOn(13))));
    public static final IRegistryObject<GrindingMillBlock> BASIC_GRINDING_MILL = register("basic_grinding_mill", props -> new GrindingMillBlock(MachineTier.BASIC, props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(4.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<GrindingMillBlock> INTERMEDIATE_GRINDING_MILL = register("intermediate_grinding_mill", props -> new GrindingMillBlock(MachineTier.INTERMEDIATE, props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(4.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<GrindingMillBlock> ADVANCED_GRINDING_MILL = register("advanced_grinding_mill", props -> new GrindingMillBlock(MachineTier.ADVANCED, props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(4.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<GrindingMillBlock> EXPERT_GRINDING_MILL = register("expert_grinding_mill", props -> new GrindingMillBlock(MachineTier.EXPERT, props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(4.0F, 6.0F).requiresCorrectToolForDrops()));

    private static <T extends Block> IRegistryObject<T> register(String name, Function<BlockBehaviour.Properties, ? extends T> factory) {
        return register(name, factory, block -> item(name, block));
    }

    private static <T extends Block> IRegistryObject<T> register(String name, Function<BlockBehaviour.Properties, ? extends T> factory, Function<IRegistryObject<T>, Supplier<? extends Item>> itemCreator) {
        IRegistryObject<T> ret = registerNoItem(name, factory);
        ITEMS.register(name, itemCreator.apply(ret));
        return ret;
    }

    private static <T extends Block> IRegistryObject<T> registerNoItem(String name, Function<BlockBehaviour.Properties, ? extends T> factory) {
        // Since 1.21.2 every block has to know its own id before it is constructed, so the
        // properties are built here where the registration name is known.
        final ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
        return BLOCKS.register(name, () -> factory.apply(BlockBehaviour.Properties.of().setId(key)));
    }

    private static Supplier<BlockItem> item(final String name, final IRegistryObject<? extends Block> block) {
        final ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
        return () -> new BlockItem(block.get(), new Item.Properties().useBlockDescriptionPrefix().setId(key));
    }

    private static ToIntFunction<BlockState> getLightValueOn(int lightValue) {
        return (state) -> {
            return state.getValue(BaseMachineBlock.ON) ? lightValue : 0;
        };
    }

    public static void init() {
    }
}
