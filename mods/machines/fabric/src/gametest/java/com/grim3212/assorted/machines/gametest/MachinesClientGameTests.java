package com.grim3212.assorted.machines.gametest;

import com.grim3212.assorted.lib.test.ClientChecks;
import com.grim3212.assorted.lib.crafting.SyncedRecipes;
import com.grim3212.assorted.lib.test.ManualCheck;
import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.client.screen.AlloyForgeScreen;
import com.grim3212.assorted.machines.client.screen.GrindingMillScreen;
import com.grim3212.assorted.machines.common.blocks.MachinesBlocks;
import com.grim3212.assorted.machines.common.crafting.MachinesRecipeTypes;
import com.grim3212.assorted.machines.common.inventory.BaseMachineContainer;
import com.grim3212.assorted.machines.common.items.MachinesItems;
import com.grim3212.assorted.machines.compat.jei.JEIAssortedMachines;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import mezz.jei.api.runtime.IJeiRuntime;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeButton;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.lang.reflect.Field;
import java.util.List;

/**
 * The machines as a player uses them, with real clicks: what a server test cannot see, from the art and the manual
 * to the client half of every slot click, the flame and arrow, the recipe book and JEI.
 */
public class MachinesClientGameTests implements FabricClientGameTest {

    /** The first slot of the player's own inventory in both menus, which holds inventory slot 9. */
    private static final int PLAYER_SLOT = 4;

    @Override
    public void runTest(ClientGameTestContext context) {
        try (TestSingleplayerContext world = context.worldBuilder().create()) {
            ClientChecks.assertDrawsRight(context, Constants.MOD_ID);
            world.getServer().runCommand("gamemode survival @a");
            context.waitFor(client -> ManualCheck.recipesHaveSynced() && !SyncedRecipes.byType(MachinesRecipeTypes.GRINDING_MILL.get()).isEmpty(), 200);

            jeiShowsEveryMachineRecipe(context);
            grindingMillWorksByHand(context, world);
            recipeBookFillsTheAlloyForge(context, world);
        }
    }

    /** JEI lists recipes the client was sent, not ones it loaded, so they arrive after the world opens. */
    private static void jeiShowsEveryMachineRecipe(ClientGameTestContext context) {
        int forgeRecipes = context.computeOnClient(client -> SyncedRecipes.byType(MachinesRecipeTypes.ALLOY_FORGE.get()).size());
        int millRecipes = context.computeOnClient(client -> SyncedRecipes.byType(MachinesRecipeTypes.GRINDING_MILL.get()).size());
        check(forgeRecipes > 0 && millRecipes > 0, "the client has " + forgeRecipes + " alloy forge and " + millRecipes + " grinding mill recipes");

        try {
            context.waitFor(client -> {
                IJeiRuntime jei = MachinesJeiProbe.runtime;
                return jei != null
                        && jei.getRecipeManager().createRecipeLookup(JEIAssortedMachines.ALLOY_FORGE).get().count() == forgeRecipes
                        && jei.getRecipeManager().createRecipeLookup(JEIAssortedMachines.GRINDING_MILL).get().count() == millRecipes;
            }, 200);
        } catch (AssertionError timedOut) {
            String shown = context.computeOnClient(client -> {
                IJeiRuntime jei = MachinesJeiProbe.runtime;
                return jei == null ? "no JEI runtime" : jei.getRecipeManager().createRecipeLookup(JEIAssortedMachines.ALLOY_FORGE).get().count()
                        + " alloy forge and " + jei.getRecipeManager().createRecipeLookup(JEIAssortedMachines.GRINDING_MILL).get().count() + " grinding mill recipes";
            });
            throw new AssertionError("JEI shows " + shown + ", not " + forgeRecipes + " and " + millRecipes, timedOut);
        }
    }

    /** Opened with the use key and filled by picking items up and putting them down, then run, watched and emptied with a shift click. */
    private static void grindingMillWorksByHand(ClientGameTestContext context, TestSingleplayerContext world) {
        BlockPos pos = placeInFront(context, world, MachinesBlocks.EXPERT_GRINDING_MILL.get(),
                new ItemStack(Items.COPPER_INGOT), new ItemStack(Items.IRON_PICKAXE), new ItemStack(Items.COAL, 4));
        open(context, pos, GrindingMillScreen.class);

        moveWithClicks(context, PLAYER_SLOT, 0);
        moveWithClicks(context, PLAYER_SLOT + 1, 1);
        moveWithClicks(context, PLAYER_SLOT + 2, 2);
        check(context.computeOnClient(client -> menu(client.player.containerMenu).getSlot(2).getItem().is(Items.COAL)),
                "clicking coal onto the grinding mill's fuel slot did not put it there");

        // The flame and arrow are drawn from the menu's synced data, so a sync gap shows an idle machine.
        context.waitFor(client -> menu(client.player.containerMenu).isBurning() && menu(client.player.containerMenu).getCookProgressionScaled() > 0, 200);
        context.waitFor(client -> menu(client.player.containerMenu).getSlot(3).getItem().is(MachinesItems.COPPER_DUST.get()), 400);

        shiftClickSlot(context, 3);
        try {
            context.waitFor(client -> client.player.getInventory().countItem(MachinesItems.COPPER_DUST.get()) == 1, 20);
        } catch (AssertionError timedOut) {
            String seen = context.computeOnClient(client -> "output " + menu(client.player.containerMenu).getSlot(3).getItem()
                    + ", cursor " + client.player.containerMenu.getCarried() + ", inventory " + client.player.getInventory().countItem(MachinesItems.COPPER_DUST.get()));
            throw new AssertionError("shift clicking the mill's copper dust left " + seen, timedOut);
        }

        context.setScreen(() -> null);
        world.getServer().runOnServer(server -> player(server).level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState()));
    }

    /** With steel's ingredients in hand, its recipe book button fills both inputs at the recipe's counts. */
    private static void recipeBookFillsTheAlloyForge(ClientGameTestContext context, TestSingleplayerContext world) {
        world.getServer().runCommand("recipe give @a *");
        BlockPos pos = placeInFront(context, world, MachinesBlocks.EXPERT_ALLOY_FORGE.get(),
                new ItemStack(Items.COAL, 4), new ItemStack(MachinesItems.IRON_DUST.get()));
        open(context, pos, AlloyForgeScreen.class);

        int[] button = context.computeOnClient(client -> {
            AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) client.gui.screen();
            return new int[]{(int) read(AbstractContainerScreen.class, screen, "leftPos") + 18, (int) read(AbstractContainerScreen.class, screen, "topPos") + 42};
        });
        click(context, button[0], button[1]);
        context.waitFor(client -> book(client.gui.screen()).isVisible(), 20);

        int[] steel = context.computeOnClient(client -> {
            Object page = read(RecipeBookComponent.class, book(client.gui.screen()), "recipeBookPage");
            @SuppressWarnings("unchecked")
            List<RecipeButton> buttons = (List<RecipeButton>) read(page.getClass(), page, "buttons");
            for (RecipeButton recipe : buttons) {
                if (recipe.visible && recipe.getDisplayStack().is(MachinesItems.STEEL_INGOT.get())) {
                    return new int[]{recipe.getX() + recipe.getWidth() / 2, recipe.getY() + recipe.getHeight() / 2};
                }
            }
            throw new AssertionError("the alloy forge's recipe book does not show steel");
        });
        click(context, steel[0], steel[1]);

        context.waitFor(client -> {
            BaseMachineContainer menu = menu(client.player.containerMenu);
            return count(menu, Items.COAL) == 4 && count(menu, MachinesItems.IRON_DUST.get()) == 1;
        }, 40);

        context.setScreen(() -> null);
        world.getServer().runOnServer(server -> player(server).level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState()));
    }

    /** Two blocks ahead at eye height, with the way cleared, and the player's inventory down to {@code items} from slot 9. */
    private static BlockPos placeInFront(ClientGameTestContext context, TestSingleplayerContext world, Block block, ItemStack... items) {
        BlockPos pos = world.getServer().computeOnServer(server -> {
            ServerPlayer player = player(server);
            BlockPos at = player.blockPosition().offset(0, 1, 2);
            player.level().setBlockAndUpdate(at.north(), Blocks.AIR.defaultBlockState());
            player.level().setBlockAndUpdate(at, block.defaultBlockState());
            player.getInventory().clearContent();
            for (int i = 0; i < items.length; i++) {
                player.getInventory().setItem(9 + i, items[i].copy());
            }
            return at;
        });
        context.waitFor(client -> client.level.getBlockState(pos).is(block) && client.player.getInventory().getItem(9).getCount() == items[0].getCount());
        return pos;
    }

    private static void open(ClientGameTestContext context, BlockPos pos, Class<? extends Screen> screen) {
        context.getInput().lookAt(pos);
        context.waitTick();
        context.getInput().pressKey(options -> options.keyUse);
        context.waitForScreen(screen);
        context.waitTick();
    }

    /** Picks a stack up and puts it down, as a player moves an item, so both halves of a slot click run on the client. */
    private static void moveWithClicks(ClientGameTestContext context, int from, int to) {
        clickSlot(context, from);
        clickSlot(context, to);
    }

    private static void clickSlot(ClientGameTestContext context, int index) {
        int[] at = slotCentre(context, index);
        click(context, at[0], at[1]);
    }

    /** Handed to the screen directly, as Fabric's test input sends every click with no modifier keys held. */
    private static void shiftClickSlot(ClientGameTestContext context, int index) {
        int[] at = slotCentre(context, index);
        context.runOnClient(client -> {
            MouseButtonEvent event = new MouseButtonEvent(at[0] + 0.5D, at[1] + 0.5D, new MouseButtonInfo(InputConstants.MOUSE_BUTTON_LEFT, InputConstants.MOD_SHIFT));
            client.gui.screen().mouseClicked(event, false);
            client.gui.screen().mouseReleased(event);
        });
        context.waitTicks(2);
    }

    private static int[] slotCentre(ClientGameTestContext context, int index) {
        return context.computeOnClient(client -> {
            AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) client.gui.screen();
            Slot slot = screen.getMenu().getSlot(index);
            return new int[]{(int) read(AbstractContainerScreen.class, screen, "leftPos") + slot.x + 8,
                    (int) read(AbstractContainerScreen.class, screen, "topPos") + slot.y + 8};
        });
    }

    /** Moves the real cursor there in window pixels and presses the left button. */
    private static void click(ClientGameTestContext context, int guiX, int guiY) {
        double[] pixels = context.computeOnClient(client -> {
            Window window = client.getWindow();
            return new double[]{(guiX + 0.5) * window.getScreenWidth() / window.getGuiScaledWidth(),
                    (guiY + 0.5) * window.getScreenHeight() / window.getGuiScaledHeight()};
        });
        context.getInput().setCursorPos(pixels[0], pixels[1]);
        context.getInput().pressMouse(InputConstants.MOUSE_BUTTON_LEFT);
        context.waitTicks(2);
    }

    private static BaseMachineContainer menu(Object menu) {
        check(menu instanceof BaseMachineContainer, "the open menu is " + menu + ", not a machine's");
        return (BaseMachineContainer) menu;
    }

    private static RecipeBookComponent<?> book(Screen screen) {
        return (RecipeBookComponent<?>) read(AbstractRecipeBookScreen.class, screen, "recipeBookComponent");
    }

    private static int count(BaseMachineContainer menu, Item item) {
        int count = 0;
        for (int slot = 0; slot < 2; slot++) {
            ItemStack stack = menu.getSlot(slot).getItem();
            count += stack.is(item) ? stack.getCount() : 0;
        }
        return count;
    }

    /** The screen keeps where it is drawn and its book to itself; these tests are the only outside reader. */
    private static Object read(Class<?> owner, Object instance, String name) {
        try {
            Field field = owner.getDeclaredField(name);
            field.setAccessible(true);
            return field.get(instance);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(owner.getSimpleName() + " has no field " + name, e);
        }
    }

    private static ServerPlayer player(MinecraftServer server) {
        return server.getPlayerList().getPlayers().getFirst();
    }

    private static void check(boolean ok, String message) {
        if (!ok) {
            throw new AssertionError(message);
        }
    }
}
