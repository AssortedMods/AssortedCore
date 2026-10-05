package com.grim3212.assorted.machines.api.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.List;

/**
 * A machine recipe in the recipe book, one input per input slot. No vanilla display fits: a
 * {@link SlotDisplay} carries no count, so the count lives on {@link Input}.
 */
public record MachineRecipeDisplay(List<Input> inputs, SlotDisplay result, SlotDisplay craftingStation, int duration, float experience) implements RecipeDisplay {

    public static final MapCodec<MachineRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Input.CODEC.listOf().fieldOf("inputs").forGetter(MachineRecipeDisplay::inputs),
            SlotDisplay.CODEC.fieldOf("result").forGetter(MachineRecipeDisplay::result),
            SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(MachineRecipeDisplay::craftingStation),
            Codec.INT.fieldOf("duration").forGetter(MachineRecipeDisplay::duration),
            Codec.FLOAT.fieldOf("experience").forGetter(MachineRecipeDisplay::experience)
    ).apply(instance, MachineRecipeDisplay::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MachineRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
            Input.STREAM_CODEC.apply(ByteBufCodecs.list()), MachineRecipeDisplay::inputs,
            SlotDisplay.STREAM_CODEC, MachineRecipeDisplay::result,
            SlotDisplay.STREAM_CODEC, MachineRecipeDisplay::craftingStation,
            ByteBufCodecs.VAR_INT, MachineRecipeDisplay::duration,
            ByteBufCodecs.FLOAT, MachineRecipeDisplay::experience,
            MachineRecipeDisplay::new);

    public static final RecipeDisplay.Type<MachineRecipeDisplay> TYPE = new RecipeDisplay.Type<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public RecipeDisplay.Type<MachineRecipeDisplay> type() {
        return TYPE;
    }

    @Override
    public boolean isEnabled(FeatureFlagSet enabledFeatures) {
        return this.inputs.stream().allMatch(input -> input.display().isEnabled(enabledFeatures)) && RecipeDisplay.super.isEnabled(enabledFeatures);
    }

    /** One machine input slot: what it takes, and how many of it. */
    public record Input(SlotDisplay display, int count) {

        public static final Codec<Input> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                SlotDisplay.CODEC.fieldOf("display").forGetter(Input::display),
                Codec.INT.optionalFieldOf("count", 1).forGetter(Input::count)
        ).apply(instance, Input::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, Input> STREAM_CODEC = StreamCodec.composite(
                SlotDisplay.STREAM_CODEC, Input::display,
                ByteBufCodecs.VAR_INT, Input::count,
                Input::new);

        public Input(MachineIngredient ingredient) {
            this(ingredient.getBaseIngredient().display(), ingredient.getCount());
        }

        /** For ghost slots: the accepted stacks, each at the recipe's count. */
        public List<ItemStack> stacksAtCount(ContextMap context) {
            return this.display.resolveForStacks(context).stream().filter(stack -> !stack.isEmpty()).map(stack -> stack.copyWithCount(this.count)).toList();
        }
    }
}
