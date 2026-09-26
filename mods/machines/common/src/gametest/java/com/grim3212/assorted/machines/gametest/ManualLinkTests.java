package com.grim3212.assorted.machines.gametest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.Family;
import com.grim3212.assorted.lib.manual.ManualLinks;
import com.grim3212.assorted.lib.manual.ManualPageRef;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.grim3212.assorted.machines.gametest.MachinesTestSupport.*;

/**
 * The shipped {@code links.json}: right clicking anything this mod adds with the instruction manual
 * has to open a page, so every block and item is expected to be named in it.
 */
final class ManualLinkTests {

    private static final String LINKS = "/assets/" + Constants.MOD_ID + "/manual/links.json";

    private ManualLinkTests() {
    }

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("manual_links_cover_every_block_and_item", ManualLinkTests::linksCoverEveryBlockAndItem);
        out.accept("manual_links_point_at_pages_that_exist", ManualLinkTests::linksPointAtPagesThatExist);
    }

    /** Every block and item in this mod's namespace opens something. Every gap is reported at once. */
    private static void linksCoverEveryBlockAndItem(GameTestHelper helper) {
        ManualLinks.Loaded links = ManualLinks.Loaded.of(groups(helper));
        List<String> missing = new ArrayList<>();

        for (Map.Entry<ResourceKey<Block>, Block> entry : BuiltInRegistries.BLOCK.entrySet()) {
            Identifier id = entry.getKey().identifier();
            if (Constants.MOD_ID.equals(id.getNamespace()) && !links.blocks().containsKey(id)) {
                missing.add("block " + id);
            }
        }

        for (Map.Entry<ResourceKey<Item>, Item> entry : BuiltInRegistries.ITEM.entrySet()) {
            Identifier id = entry.getKey().identifier();
            if (Constants.MOD_ID.equals(id.getNamespace()) && !links.items().containsKey(id)) {
                missing.add("item " + id);
            }
        }

        helper.assertTrue(missing.isEmpty(), missing.size() + " with no manual link: " + String.join(", ", missing));
        helper.succeed();
    }

    /**
     * Every page a link names is one this mod ships, so a renamed chapter or page shows up here
     * rather than as a book that opens where it was left.
     */
    private static void linksPointAtPagesThatExist(GameTestHelper helper) {
        List<String> dangling = new ArrayList<>();

        for (ManualLinks.Group group : groups(helper)) {
            ManualPageRef page = group.page();
            if (!Family.ID.equals(page.section())) {
                // A link may point into another mod's section, which this mod cannot check.
                continue;
            }
            if (!pagesOf(helper, page.chapter()).contains(page.page())) {
                dangling.add(page.toString());
            }
        }

        helper.assertTrue(dangling.isEmpty(), "links pointing at pages that do not exist: " + String.join(", ", dangling));
        helper.succeed();
    }

    private static List<ManualLinks.Group> groups(GameTestHelper helper) {
        JsonObject json = readJson(helper, LINKS);
        return ManualLinks.Group.FILE_CODEC.parse(JsonOps.INSTANCE, json)
                .getOrThrow(message -> helper.assertionException("could not read " + LINKS + ": " + message));
    }

    /** The page ids of one chapter this mod ships into the family's section; empty when there is no such chapter. */
    private static Set<String> pagesOf(GameTestHelper helper, String chapter) {
        String path = "/assets/" + Family.ID + "/manual/chapters/" + chapter + ".json";
        if (!resourceExists(path)) {
            return Set.of();
        }

        Set<String> ids = new HashSet<>();
        for (JsonElement page : readJson(helper, path).getAsJsonArray("pages")) {
            ids.add(page.getAsJsonObject().get("id").getAsString());
        }

        return ids;
    }
}
