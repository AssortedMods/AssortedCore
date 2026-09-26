package com.grim3212.assorted.ores.common.blocks;

import com.grim3212.assorted.ores.Constants;
import com.grim3212.assorted.ores.Family;
import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;
import java.util.function.Supplier;

public class OresBlocks {

    public static final RegistryProvider<Block> BLOCKS = RegistryProvider.create(Registries.BLOCK, Constants.MOD_ID).aliasFrom(Family.ID);
    public static final RegistryProvider<Item> ITEMS = RegistryProvider.create(Registries.ITEM, Constants.MOD_ID).aliasFrom(Family.ID);

    public static final IRegistryObject<OreBlock> TIN_ORE = register("tin_ore", props -> new OreBlock(props.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(3.0f, 3.0f).requiresCorrectToolForDrops()));
    public static final IRegistryObject<OreBlock> SILVER_ORE = register("silver_ore", props -> new OreBlock(props.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(3.0f, 3.0f).requiresCorrectToolForDrops()));
    public static final IRegistryObject<OreBlock> ALUMINUM_ORE = register("aluminum_ore", props -> new OreBlock(props.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(3.0f, 3.0f).requiresCorrectToolForDrops()));
    public static final IRegistryObject<OreBlock> NICKEL_ORE = register("nickel_ore", props -> new OreBlock(props.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(3.0f, 3.0f).requiresCorrectToolForDrops()));
    public static final IRegistryObject<OreBlock> PLATINUM_ORE = register("platinum_ore", props -> new OreBlock(props.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(3.0f, 3.0f).requiresCorrectToolForDrops()));
    public static final IRegistryObject<OreBlock> LEAD_ORE = register("lead_ore", props -> new OreBlock(props.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(3.0f, 3.0f).requiresCorrectToolForDrops()));
    public static final IRegistryObject<OreBlock> RUBY_ORE = register("ruby_ore", props -> new OreBlock(props.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(3.0f, 3.0f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));
    public static final IRegistryObject<OreBlock> PERIDOT_ORE = register("peridot_ore", props -> new OreBlock(props.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(3.0f, 3.0f).requiresCorrectToolForDrops(), UniformInt.of(2, 5)));
    public static final IRegistryObject<OreBlock> SAPPHIRE_ORE = register("sapphire_ore", props -> new OreBlock(props.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(3.0f, 3.0f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));
    public static final IRegistryObject<OreBlock> TOPAZ_ORE = register("topaz_ore", props -> new OreBlock(props.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(3.0f, 3.0f).requiresCorrectToolForDrops(), UniformInt.of(2, 5)));

    public static final IRegistryObject<OreBlock> DEEPSLATE_TIN_ORE = register("deepslate_tin_ore", props -> new OreBlock(props.mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(4.5F, 3.0f).requiresCorrectToolForDrops()));
    public static final IRegistryObject<OreBlock> DEEPSLATE_SILVER_ORE = register("deepslate_silver_ore", props -> new OreBlock(props.mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(4.5F, 3.0f).requiresCorrectToolForDrops()));
    public static final IRegistryObject<OreBlock> DEEPSLATE_ALUMINUM_ORE = register("deepslate_aluminum_ore", props -> new OreBlock(props.mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(4.5F, 3.0f).requiresCorrectToolForDrops()));
    public static final IRegistryObject<OreBlock> DEEPSLATE_NICKEL_ORE = register("deepslate_nickel_ore", props -> new OreBlock(props.mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(4.5F, 3.0f).requiresCorrectToolForDrops()));
    public static final IRegistryObject<OreBlock> DEEPSLATE_PLATINUM_ORE = register("deepslate_platinum_ore", props -> new OreBlock(props.mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(4.5F, 3.0f).requiresCorrectToolForDrops()));
    public static final IRegistryObject<OreBlock> DEEPSLATE_LEAD_ORE = register("deepslate_lead_ore", props -> new OreBlock(props.mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(4.5F, 3.0f).requiresCorrectToolForDrops()));
    public static final IRegistryObject<OreBlock> DEEPSLATE_RUBY_ORE = register("deepslate_ruby_ore", props -> new OreBlock(props.mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(4.5F, 3.0f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));
    public static final IRegistryObject<OreBlock> DEEPSLATE_PERIDOT_ORE = register("deepslate_peridot_ore", props -> new OreBlock(props.mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(4.5F, 3.0f).requiresCorrectToolForDrops(), UniformInt.of(2, 5)));
    public static final IRegistryObject<OreBlock> DEEPSLATE_SAPPHIRE_ORE = register("deepslate_sapphire_ore", props -> new OreBlock(props.mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));
    public static final IRegistryObject<OreBlock> DEEPSLATE_TOPAZ_ORE = register("deepslate_topaz_ore", props -> new OreBlock(props.mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops(), UniformInt.of(2, 5)));

    public static final IRegistryObject<Block> TIN_BLOCK = register("tin_block", props -> new Block(props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<Block> SILVER_BLOCK = register("silver_block", props -> new Block(props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<Block> ALUMINUM_BLOCK = register("aluminum_block", props -> new Block(props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<Block> NICKEL_BLOCK = register("nickel_block", props -> new Block(props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<Block> PLATINUM_BLOCK = register("platinum_block", props -> new Block(props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<Block> LEAD_BLOCK = register("lead_block", props -> new Block(props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<Block> RUBY_BLOCK = register("ruby_block", props -> new Block(props.mapColor(MapColor.COLOR_RED).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<Block> PERIDOT_BLOCK = register("peridot_block", props -> new Block(props.mapColor(MapColor.COLOR_GREEN).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<Block> SAPPHIRE_BLOCK = register("sapphire_block", props -> new Block(props.mapColor(MapColor.COLOR_BLUE).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<Block> TOPAZ_BLOCK = register("topaz_block", props -> new Block(props.mapColor(MapColor.COLOR_YELLOW).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));

    public static final IRegistryObject<Block> RAW_TIN_BLOCK = register("raw_tin_block", props -> new Block(props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<Block> RAW_SILVER_BLOCK = register("raw_silver_block", props -> new Block(props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<Block> RAW_ALUMINUM_BLOCK = register("raw_aluminum_block", props -> new Block(props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<Block> RAW_NICKEL_BLOCK = register("raw_nickel_block", props -> new Block(props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<Block> RAW_PLATINUM_BLOCK = register("raw_platinum_block", props -> new Block(props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<Block> RAW_LEAD_BLOCK = register("raw_lead_block", props -> new Block(props.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));

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

    public static void init() {
    }
}
