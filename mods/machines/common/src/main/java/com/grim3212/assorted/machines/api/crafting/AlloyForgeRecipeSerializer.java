package com.grim3212.assorted.machines.api.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeSerializer;

/** The codecs and serializer instance for {@link AlloyForgeRecipe}. */
public final class AlloyForgeRecipeSerializer {

    public static final MapCodec<AlloyForgeRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
            MachineIngredient.CODEC.fieldOf("ingredient1").forGetter(AlloyForgeRecipe::getIngredient1),
            MachineIngredient.CODEC.fieldOf("ingredient2").forGetter(AlloyForgeRecipe::getIngredient2),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
            Codec.FLOAT.optionalFieldOf("experience", 0.0F).forGetter(recipe -> recipe.experience),
            Codec.INT.optionalFieldOf("cookingtime", 400).forGetter(recipe -> recipe.cookTime)
    ).apply(instance, AlloyForgeRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AlloyForgeRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, recipe -> recipe.group,
            MachineIngredient.STREAM_CODEC, AlloyForgeRecipe::getIngredient1,
            MachineIngredient.STREAM_CODEC, AlloyForgeRecipe::getIngredient2,
            ItemStackTemplate.STREAM_CODEC, recipe -> recipe.result,
            ByteBufCodecs.FLOAT, recipe -> recipe.experience,
            ByteBufCodecs.VAR_INT, recipe -> recipe.cookTime,
            AlloyForgeRecipe::new);

    public static final RecipeSerializer<AlloyForgeRecipe> INSTANCE = new RecipeSerializer<>(CODEC, STREAM_CODEC);

    private AlloyForgeRecipeSerializer() {
    }
}
