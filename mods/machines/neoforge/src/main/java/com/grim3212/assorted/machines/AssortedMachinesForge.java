package com.grim3212.assorted.machines;

import com.grim3212.assorted.lib.data.ForgeBlockTagProvider;
import com.grim3212.assorted.lib.data.ForgeItemTagProvider;
import com.grim3212.assorted.lib.data.ForgeRegistryEntries;
import com.grim3212.assorted.lib.inventory.ForgePlatformInventoryStorageHandlerSided;
import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.machines.client.data.MachinesBlockstateProvider;
import com.grim3212.assorted.machines.client.data.MachinesItemModelProvider;
import com.grim3212.assorted.machines.client.data.MachinesLanguageProvider;
import com.grim3212.assorted.machines.client.data.MachinesManualProvider;
import com.grim3212.assorted.machines.common.blocks.blockentity.BaseMachineBlockEntity;
import com.grim3212.assorted.machines.common.blocks.blockentity.MachinesBlockEntityTypes;
import com.grim3212.assorted.machines.data.MachinesBlockLoot;
import com.grim3212.assorted.machines.data.MachinesBlockTagProvider;
import com.grim3212.assorted.machines.data.MachinesItemTagProvider;
import com.grim3212.assorted.machines.data.MachinesRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Mod(Constants.MOD_ID)
public class AssortedMachinesForge {

    public AssortedMachinesForge(IEventBus modBus, ModContainer modContainer) {
        modBus.addListener(this::gatherData);
        modBus.addListener(this::registerCapabilities);

        MachinesCommonMod.init();
    }

    /** One event for both halves; each provider is added for the half its output belongs to. */
    private void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        ForgeRegistryEntries.of(Constants.MOD_ID).recipes(MachinesRecipes::new).blockLoot(MachinesBlockLoot::new).addTo(event);
        ForgeBlockTagProvider blockTagProvider = generator.addProvider(event.includeServer(), new ForgeBlockTagProvider(packOutput, lookupProvider, Constants.MOD_ID, fileHelper, new MachinesBlockTagProvider(packOutput, lookupProvider)));
        generator.addProvider(event.includeServer(), new ForgeItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), Constants.MOD_ID, fileHelper, new MachinesItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter())));

        generator.addProvider(event.includeClient(), new MachinesBlockstateProvider(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new MachinesItemModelProvider(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new MachinesLanguageProvider(packOutput));
        generator.addProvider(event.includeClient(), new MachinesManualProvider(packOutput));
    }

    /** Exposes the machines' sided inventory to NeoForge, as Fabric does with {@code ItemStorage.SIDED}. */
    private void registerCapabilities(final RegisterCapabilitiesEvent event) {
        registerMachineItemHandler(event, MachinesBlockEntityTypes.BASIC_ALLOY_FORGE);
        registerMachineItemHandler(event, MachinesBlockEntityTypes.INTERMEDIATE_ALLOY_FORGE);
        registerMachineItemHandler(event, MachinesBlockEntityTypes.ADVANCED_ALLOY_FORGE);
        registerMachineItemHandler(event, MachinesBlockEntityTypes.EXPERT_ALLOY_FORGE);

        registerMachineItemHandler(event, MachinesBlockEntityTypes.BASIC_GRINDING_MILL);
        registerMachineItemHandler(event, MachinesBlockEntityTypes.INTERMEDIATE_GRINDING_MILL);
        registerMachineItemHandler(event, MachinesBlockEntityTypes.ADVANCED_GRINDING_MILL);
        registerMachineItemHandler(event, MachinesBlockEntityTypes.EXPERT_GRINDING_MILL);
    }

    /** NeoForge 21.1's item capability is {@code IItemHandler}; the library's sided handler already exposes one. */
    private static <BE extends BaseMachineBlockEntity> void registerMachineItemHandler(RegisterCapabilitiesEvent event, IRegistryObject<BlockEntityType<BE>> type) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, type.get(), (blockEntity, side) -> {
            if (blockEntity.isRemoved()) {
                return null;
            }
            return ((ForgePlatformInventoryStorageHandlerSided) blockEntity.getStorageHandler()).getCapability(side);
        });
    }

}
