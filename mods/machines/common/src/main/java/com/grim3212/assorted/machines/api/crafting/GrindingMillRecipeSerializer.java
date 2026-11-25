package com.grim3212.assorted.machines.api.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

/** The codecs and serializer instance for {@link GrindingMillRecipe}. */
public final class GrindingMillRecipeSerializer implements RecipeSerializer<GrindingMillRecipe> {

    public static final MapCodec<GrindingMillRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
            MachineIngredient.CODEC.fieldOf("ingredient").forGetter(GrindingMillRecipe::getIngredient),
            ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
            Codec.FLOAT.optionalFieldOf("experience", 0.0F).forGetter(recipe -> recipe.experience),
            Codec.INT.optionalFieldOf("cookingtime", 400).forGetter(recipe -> recipe.cookTime)
    ).apply(instance, GrindingMillRecipe::new));

    /** Every recipe is sent to every client in 1.21.1, so this has to carry the count too. */
    public static final StreamCodec<RegistryFriendlyByteBuf, GrindingMillRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, recipe -> recipe.group,
            MachineIngredient.STREAM_CODEC, GrindingMillRecipe::getIngredient,
            ItemStack.STREAM_CODEC, recipe -> recipe.result,
            ByteBufCodecs.FLOAT, recipe -> recipe.experience,
            ByteBufCodecs.VAR_INT, recipe -> recipe.cookTime,
            GrindingMillRecipe::new);

    public static final GrindingMillRecipeSerializer INSTANCE = new GrindingMillRecipeSerializer();

    private GrindingMillRecipeSerializer() {
    }

    @Override
    public MapCodec<GrindingMillRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, GrindingMillRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
