package com.grim3212.assorted.machines.api.crafting;

import com.mojang.serialization.Codec;
import com.grim3212.assorted.lib.manual.ManualSlot;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.List;
import java.util.function.Predicate;

/**
 * An {@link Ingredient} with a required count, serialized through a {@link MapCodec} and a
 * {@link StreamCodec}.
 */
public class MachineIngredient implements Predicate<ItemStack> {

    public static final MapCodec<MachineIngredient> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(MachineIngredient::getBaseIngredient),
            Codec.INT.optionalFieldOf("count", 1).forGetter(MachineIngredient::getCount)
    ).apply(instance, MachineIngredient::new));

    public static final Codec<MachineIngredient> CODEC = MAP_CODEC.codec();

    public static final StreamCodec<RegistryFriendlyByteBuf, MachineIngredient> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, MachineIngredient::getBaseIngredient,
            ByteBufCodecs.VAR_INT, MachineIngredient::getCount,
            MachineIngredient::new);

    private final Ingredient ingredient;
    private final int count;

    public MachineIngredient(Ingredient ingredient) {
        this(ingredient, 1);
    }

    /** A count below one would ask for nothing, and divide by zero in the recipe book placer. */
    public MachineIngredient(Ingredient ingredient, int count) {
        this.ingredient = ingredient;
        this.count = Math.max(1, count);
    }

    public int getCount() {
        return count;
    }

    public Ingredient getBaseIngredient() {
        return this.ingredient;
    }

    @Override
    public boolean test(ItemStack t) {
        if (t == null)
            return false;
        return ingredient.test(t) && t.getCount() >= this.count;
    }

    /**
     * The stacks this ingredient accepts at its count, resolved from its {@link SlotDisplay}. For
     * display.
     */
    public List<ItemStack> getMatchingStacks(ContextMap context) {
        return this.ingredient.display().resolveForStacks(context).stream().map((stack) -> stack.copyWithCount(this.count)).toList();
    }

    /** Unresolved, so the manual keeps the tag behind the slot for its tooltip. */
    public ManualSlot manualSlot() {
        return ManualSlot.of(this.ingredient.display(), this.count);
    }
}
