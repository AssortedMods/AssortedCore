package com.grim3212.assorted.machines;

import com.grim3212.assorted.machines.client.data.MachinesLanguageProvider;
import com.grim3212.assorted.machines.client.data.MachinesManualProvider;
import com.grim3212.assorted.machines.client.data.MachinesBlockstateProvider;
import com.grim3212.assorted.machines.client.data.MachinesItemModelProvider;
import com.grim3212.assorted.machines.common.blocks.blockentity.BaseMachineBlockEntity;
import com.grim3212.assorted.machines.common.blocks.blockentity.MachinesBlockEntityTypes;
import com.grim3212.assorted.machines.common.crafting.MachineRecipeBackfill;
import com.grim3212.assorted.machines.data.*;
import com.grim3212.assorted.lib.data.ForgeBlockTagProvider;
import com.grim3212.assorted.lib.data.ForgeItemTagProvider;
import com.grim3212.assorted.lib.inventory.ForgePlatformInventoryStorageHandlerSided;
import com.grim3212.assorted.lib.registry.IRegistryObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod(Constants.MOD_ID)
public class AssortedMachinesForge {

    /**
     * {@code FMLJavaModLoadingContext} is gone; the mod event bus and the mod container are injected
     * into the {@code @Mod} constructor instead.
     */
    public AssortedMachinesForge(IEventBus modBus, ModContainer modContainer) {
        modBus.addListener(this::gatherServerData);
        modBus.addListener(this::gatherClientData);
        modBus.addListener(this::registerCapabilities);
        NeoForge.EVENT_BUS.addListener(this::onPlayerLoggedIn);

        MachinesCommonMod.init();
    }

    /** TODO(11.0.0): remove along with {@link MachineRecipeBackfill}. */
    private void onPlayerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            MachineRecipeBackfill.award(player);
        }
    }

    /**
     * {@code ExistingFileHelper} was removed from datagen, the event owns the provider list now
     * ({@code addProvider}), and the include flags are gone because the server and client halves are
     * separate events.
     */
    private void gatherServerData(final GatherDataEvent.Server event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Recipe providers are not data providers any more - the Runner owns the output.
        event.addProvider(new MachinesRecipes.Runner(packOutput, lookupProvider));
        ForgeBlockTagProvider blockTagProvider = event.addProvider(new ForgeBlockTagProvider(packOutput, lookupProvider, Constants.MOD_ID, new MachinesBlockTagProvider(packOutput, lookupProvider)));
        event.addProvider(new ForgeItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), Constants.MOD_ID, new MachinesItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter())));
        event.addProvider(new LootTableProvider(packOutput, Collections.emptySet(), List.of(new LootTableProvider.SubProviderEntry(MachinesBlockLoot::new, LootContextParamSets.BLOCK)), lookupProvider));
    }

    private void gatherClientData(final GatherDataEvent.Client event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();

        event.addProvider(new MachinesBlockstateProvider(packOutput));
        event.addProvider(new MachinesItemModelProvider(packOutput));
        event.addProvider(new MachinesLanguageProvider(packOutput));
        event.addProvider(new MachinesManualProvider(packOutput));
    }

    /**
     * Exposes the machines' sided inventory to NeoForge, as Fabric does with {@code
     * ItemStorage.SIDED}.
     */
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

    /**
     * {@code ForgeCapabilities.ITEM_HANDLER} and the deprecated {@code IItemHandler} it was typed
     * with are replaced by {@code Capabilities.Item.BLOCK}, a transactional
     * {@code ResourceHandler<ItemResource>}; the library's sided handler already exposes one.
     */
    private static <BE extends BaseMachineBlockEntity> void registerMachineItemHandler(RegisterCapabilitiesEvent event, IRegistryObject<BlockEntityType<BE>> type) {
        event.registerBlockEntity(Capabilities.Item.BLOCK, type.get(), (blockEntity, side) -> {
            if (blockEntity.isRemoved()) {
                return null;
            }
            return ((ForgePlatformInventoryStorageHandlerSided) blockEntity.getStorageHandler()).getCapability(side);
        });
    }

}
