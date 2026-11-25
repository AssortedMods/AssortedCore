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
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Mod(Constants.MOD_ID)
public class AssortedOresForge {

    public AssortedOresForge(IEventBus modBus, ModContainer modContainer) {
        modBus.addListener(this::gatherData);

        OresCommonMod.init();
    }

    /** One event for both halves; the data run's --server and --client flags pick which providers run. */
    private void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        ForgeRegistryEntries.of(Constants.MOD_ID).world(new OresWorldGenData()).recipes(OresRecipes::new).blockLoot(OresBlockLoot::new).addTo(event);
        generator.addProvider(event.includeServer(), new OresMachineRecipes(packOutput));
        ForgeBlockTagProvider blockTagProvider = generator.addProvider(event.includeServer(), new ForgeBlockTagProvider(packOutput, lookupProvider, Constants.MOD_ID, fileHelper, new OresBlockTagProvider(packOutput, lookupProvider)));
        generator.addProvider(event.includeServer(), new ForgeItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), Constants.MOD_ID, fileHelper, new OresItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter())));

        generator.addProvider(event.includeClient(), new OresBlockstateProvider(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new OresItemModelProvider(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new OresLanguageProvider(packOutput));
        generator.addProvider(event.includeClient(), new OresManualProvider(packOutput));
    }
}
