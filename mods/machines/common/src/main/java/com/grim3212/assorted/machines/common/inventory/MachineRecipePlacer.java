package com.grim3212.assorted.machines.common.inventory;

import com.grim3212.assorted.machines.api.crafting.MachineIngredient;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Fills a machine's input slots from a recipe book click. Not {@code ServerPlaceRecipe}: it places
 * one item per grid slot and cannot honour a {@link MachineIngredient} count above one.
 */
final class MachineRecipePlacer {

    private MachineRecipePlacer() {
    }

    /** {@code allowDroppingItemsToClear} is vanilla's name for "creative", who may drop on the floor. */
    static RecipeBookMenu.PostPlaceAction place(List<Slot> inputSlots, List<Slot> slotsToClear, List<MachineIngredient> ingredients, Inventory inventory, boolean useMaxItems, boolean allowDroppingItemsToClear) {
        int slotCount = Math.min(inputSlots.size(), ingredients.size());
        if (slotCount == 0) {
            return RecipeBookMenu.PostPlaceAction.NOTHING;
        }

        if (!allowDroppingItemsToClear && !hasRoomToClear(slotsToClear, inventory)) {
            return RecipeBookMenu.PostPlaceAction.NOTHING;
        }

        clear(slotsToClear, inventory);
        inventory.setChanged();

        // Read after clearing: what the slots held is back in the inventory and placeable again.
        Object2IntMap<Holder<Item>> pool = craftingItems(inventory);
        List<Choice> choices = new ArrayList<>(slotCount);
        int batches = useMaxItems ? Integer.MAX_VALUE : 1;

        for (int i = 0; i < slotCount; i++) {
            MachineIngredient ingredient = ingredients.get(i);
            Choice choice = choose(pool, inputSlots.get(i), ingredient);
            if (choice == null) {
                return RecipeBookMenu.PostPlaceAction.PLACE_GHOST_RECIPE;
            }

            pool.put(choice.item(), pool.getInt(choice.item()) - choice.available());
            choices.add(choice);
            batches = Math.min(batches, choice.available() / ingredient.getCount());
        }

        if (batches < 1) {
            return RecipeBookMenu.PostPlaceAction.PLACE_GHOST_RECIPE;
        }

        for (int i = 0; i < slotCount; i++) {
            moveToSlot(inventory, inputSlots.get(i), choices.get(i).item(), ingredients.get(i).getCount() * batches);
        }

        inventory.setChanged();
        return RecipeBookMenu.PostPlaceAction.NOTHING;
    }

    /** Keyed by item, not stack, like {@code StackedItemContents}: {@link Inventory#isUsableForCrafting} already dropped the stacks whose components would matter. */
    private static @Nullable Choice choose(Object2IntMap<Holder<Item>> pool, Slot slot, MachineIngredient ingredient) {
        Choice best = null;

        for (Object2IntMap.Entry<Holder<Item>> entry : pool.object2IntEntrySet()) {
            Holder<Item> item = entry.getKey();
            if (entry.getIntValue() < ingredient.getCount() || !ingredient.getBaseIngredient().acceptsItem(item)) {
                continue;
            }

            ItemStack stack = new ItemStack(item);
            if (!slot.mayPlace(stack)) {
                continue;
            }

            // Capped so count * batches can never exceed what the slot holds.
            int available = Math.min(entry.getIntValue(), Math.min(stack.getMaxStackSize(), slot.getMaxStackSize(stack)));
            if (best == null || available > best.available()) {
                best = new Choice(item, available);
            }
        }

        return best;
    }

    /** Main inventory only, like {@link Inventory#fillStackedContents}. */
    private static Object2IntMap<Holder<Item>> craftingItems(Inventory inventory) {
        Object2IntMap<Holder<Item>> pool = new Object2IntOpenHashMap<>();

        for (ItemStack stack : inventory.getNonEquipmentItems()) {
            if (!stack.isEmpty() && Inventory.isUsableForCrafting(stack)) {
                pool.mergeInt(stack.typeHolder(), stack.getCount(), Integer::sum);
            }
        }

        return pool;
    }

    /** {@link Slot#set}, not {@link ItemStack#grow}: a storage handler slot need not hand out the stack it stores. */
    private static void moveToSlot(Inventory inventory, Slot slot, Holder<Item> item, int amount) {
        while (amount > 0) {
            ItemStack inSlot = slot.getItem();
            int index = inventory.findSlotMatchingCraftingIngredient(item, inSlot);
            if (index == -1) {
                return;
            }

            ItemStack inInventory = inventory.getItem(index);
            ItemStack taken = amount < inInventory.getCount() ? inventory.removeItem(index, amount) : inventory.removeItemNoUpdate(index);
            amount -= taken.getCount();

            if (inSlot.isEmpty()) {
                slot.set(taken);
            } else {
                ItemStack merged = inSlot.copy();
                merged.grow(taken.getCount());
                slot.set(merged);
            }
        }
    }

    private static void clear(List<Slot> slots, Inventory inventory) {
        for (Slot slot : slots) {
            ItemStack leftover = slot.getItem().copy();
            if (leftover.isEmpty()) {
                continue;
            }

            inventory.placeItemBackInInventory(leftover, false);
            slot.set(leftover);
        }
    }

    /** {@link Inventory#placeItemBackInInventory} drops what does not fit, so refuse instead. */
    private static boolean hasRoomToClear(List<Slot> slots, Inventory inventory) {
        List<ItemStack> needingAFreeSlot = new ArrayList<>();
        int freeSlots = freeSlots(inventory);

        for (Slot slot : slots) {
            ItemStack stack = slot.getItem().copy();
            if (stack.isEmpty() || inventory.getSlotWithRemainingSpace(stack) != -1) {
                continue;
            }

            for (ItemStack waiting : needingAFreeSlot) {
                if (ItemStack.isSameItemSameComponents(waiting, stack) && waiting.getCount() + stack.getCount() <= waiting.getMaxStackSize()) {
                    waiting.grow(stack.getCount());
                    stack.setCount(0);
                    break;
                }
            }

            if (!stack.isEmpty()) {
                if (needingAFreeSlot.size() >= freeSlots) {
                    return false;
                }

                needingAFreeSlot.add(stack);
            }
        }

        return true;
    }

    private static int freeSlots(Inventory inventory) {
        int free = 0;

        for (ItemStack stack : inventory.getNonEquipmentItems()) {
            if (stack.isEmpty()) {
                free++;
            }
        }

        return free;
    }

    private record Choice(Holder<Item> item, int available) {
    }
}
