package com.grim3212.assorted.machines.api.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeSerializer;

/**
 * Codecs for {@link GrindingMillRecipe}. See {@link AlloyForgeRecipeSerializer} for why this is a
 * codec holder rather than an implemented interface.
 */
public final class GrindingMillRecipeSerializer {

    public static final MapCodec<GrindingMillRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
            MachineIngredient.CODEC.fieldOf("ingredient").forGetter(GrindingMillRecipe::getIngredient),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
            Codec.FLOAT.optionalFieldOf("experience", 0.0F).forGetter(recipe -> recipe.experience),
            Codec.INT.optionalFieldOf("cookingtime", 400).forGetter(recipe -> recipe.cookTime)
    ).apply(instance, GrindingMillRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, GrindingMillRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, recipe -> recipe.group,
            MachineIngredient.STREAM_CODEC, GrindingMillRecipe::getIngredient,
            ItemStackTemplate.STREAM_CODEC, recipe -> recipe.result,
            ByteBufCodecs.FLOAT, recipe -> recipe.experience,
            ByteBufCodecs.VAR_INT, recipe -> recipe.cookTime,
            GrindingMillRecipe::new);

    public static final RecipeSerializer<GrindingMillRecipe> INSTANCE = new RecipeSerializer<>(CODEC, STREAM_CODEC);

    private GrindingMillRecipeSerializer() {
    }
}
