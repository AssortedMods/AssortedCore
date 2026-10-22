package com.grim3212.assorted.ores;

import com.grim3212.assorted.ores.client.data.OresLanguageProvider;
import com.grim3212.assorted.ores.client.data.OresManualProvider;
import com.grim3212.assorted.ores.client.data.OresBlockstateProvider;
import com.grim3212.assorted.ores.client.data.OresItemModelProvider;
import com.grim3212.assorted.ores.data.*;
import com.grim3212.assorted.lib.data.ForgeBlockTagProvider;
import com.grim3212.assorted.lib.data.ForgeItemTagProvider;
import com.grim3212.assorted.lib.data.ForgeRegistryEntries;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Mod(Constants.MOD_ID)
public class AssortedOresForge {

    /**
     * {@code FMLJavaModLoadingContext} is gone; the mod event bus and the mod container are injected
     * into the {@code @Mod} constructor instead.
     */
    public AssortedOresForge(IEventBus modBus, ModContainer modContainer) {
        modBus.addListener(this::gatherServerData);
        modBus.addListener(this::gatherClientData);

        OresCommonMod.init();
    }

    /**
     * {@code ExistingFileHelper} was removed from datagen, the event owns the provider list now
     * ({@code addProvider}), and the include flags are gone because the server and client halves are
     * separate events.
     */
    private void gatherServerData(final GatherDataEvent.Server event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getWorldLookupProvider();

        ForgeRegistryEntries.of(Constants.MOD_ID).world(new OresWorldGenData()).recipes(OresRecipes::new).blockLoot(OresBlockLoot::new).addTo(event);
        event.addProvider(new OresMachineRecipes(packOutput));
        ForgeBlockTagProvider blockTagProvider = event.addProvider(new ForgeBlockTagProvider(packOutput, lookupProvider, Constants.MOD_ID, new OresBlockTagProvider(packOutput, lookupProvider)));
        event.addProvider(new ForgeItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), Constants.MOD_ID, new OresItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter())));
    }

    private void gatherClientData(final GatherDataEvent.Client event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();

        event.addProvider(new OresBlockstateProvider(packOutput));
        event.addProvider(new OresItemModelProvider(packOutput));
        event.addProvider(new OresLanguageProvider(packOutput));
        event.addProvider(new OresManualProvider(packOutput));
    }
}
