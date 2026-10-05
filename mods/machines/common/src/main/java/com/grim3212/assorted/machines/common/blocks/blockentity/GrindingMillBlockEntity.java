package com.grim3212.assorted.machines.common.blocks.blockentity;

import com.grim3212.assorted.machines.Constants;
import com.grim3212.assorted.machines.MachinesCommonMod;
import com.grim3212.assorted.machines.api.crafting.BaseMachineRecipe;
import com.grim3212.assorted.machines.api.crafting.GrindingMillRecipe;
import com.grim3212.assorted.machines.api.machines.MachineTier;
import com.grim3212.assorted.machines.api.machines.MachineUtil;
import com.grim3212.assorted.machines.common.crafting.MachinesRecipeTypes;
import com.grim3212.assorted.machines.common.inventory.GrindingMillContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GrindingMillBlockEntity extends BaseMachineBlockEntity {

    public GrindingMillBlockEntity(BlockEntityType<GrindingMillBlockEntity> tileEntityType, BlockPos pos, BlockState state, MachineTier tier) {
        super(tileEntityType, pos, state, tier, 4, 600, MachinesRecipeTypes.GRINDING_MILL.get());
    }

    public static GrindingMillBlockEntity basicBlockEntity(BlockPos pos, BlockState state) {
        return new GrindingMillBlockEntity(MachinesBlockEntityTypes.BASIC_GRINDING_MILL.get(), pos, state, MachineTier.BASIC);
    }

    public static GrindingMillBlockEntity intermediateBlockEntity(BlockPos pos, BlockState state) {
        return new GrindingMillBlockEntity(MachinesBlockEntityTypes.INTERMEDIATE_GRINDING_MILL.get(), pos, state, MachineTier.INTERMEDIATE);
    }

    public static GrindingMillBlockEntity advancedBlockEntity(BlockPos pos, BlockState state) {
        return new GrindingMillBlockEntity(MachinesBlockEntityTypes.ADVANCED_GRINDING_MILL.get(), pos, state, MachineTier.ADVANCED);
    }

    public static GrindingMillBlockEntity expertBlockEntity(BlockPos pos, BlockState state) {
        return new GrindingMillBlockEntity(MachinesBlockEntityTypes.EXPERT_GRINDING_MILL.get(), pos, state, MachineTier.EXPERT);
    }

    @Override
    protected boolean canCombine(@Nullable BaseMachineRecipe recipeIn) {
        if (!this.items.get(0).isEmpty() && !this.items.get(1).isEmpty() && recipeIn != null) {
            ItemStack itemstack = recipeIn.getResultItem();
            if (itemstack.isEmpty() || !MachineUtil.allowedInGrindingMillToolSlot(this.items.get(1))) {
                return false;
            } else {
                ItemStack outputSlot = this.items.get(this.outputSlot());
                if (outputSlot.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItem(outputSlot, itemstack)) {
                    return false;
                } else if (outputSlot.getCount() + itemstack.getCount() <= this.getInventory(null).getSlotLimit(outputSlot()) && outputSlot.getCount() + itemstack.getCount() <= outputSlot.getMaxStackSize()) {
                    return true;
                } else {
                    return outputSlot.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    @Override
    protected void combine(@Nullable RecipeHolder<BaseMachineRecipe> holder) {
        BaseMachineRecipe recipe = holder == null ? null : holder.value();
        if (recipe != null && this.canCombine(recipe)) {
            GrindingMillRecipe millRecipe = (GrindingMillRecipe) recipe;
            ItemStack ingredient = this.items.get(0);
            ItemStack toolSlot = this.items.get(1);
            ItemStack itemstack1 = recipe.getResultItem();
            ItemStack outputSlot = this.items.get(this.outputSlot());
            if (outputSlot.isEmpty()) {
                this.items.set(this.outputSlot(), itemstack1.copy());
            } else if (outputSlot.getItem() == itemstack1.getItem()) {
                outputSlot.grow(itemstack1.getCount());
            }

            this.setRecipeUsed(holder);

            ingredient.shrink(millRecipe.getIngredient().getCount());

            // ItemStack#hurt is gone; damage now runs through hurtAndBreak, which needs a
            // ServerLevel and clears the stack itself when it breaks.
            if (this.getLevel() instanceof ServerLevel serverLevel) {
                toolSlot.hurtAndBreak(1, serverLevel, (ServerPlayer) null, (item) -> {
                });
                if (toolSlot.isEmpty()) {
                    this.items.set(1, ItemStack.EMPTY);
                }
            }

            if (MachinesCommonMod.COMMON_CONFIG.grindingMillHasBreakSound.get()) {
                Block b = Block.byItem(ingredient.getItem());
                if (b != null && b != Blocks.AIR) {
                    // NeoForge-only deprecation; the level/pos-aware form is not on the vanilla jar.
                    @SuppressWarnings("deprecation")
                    SoundType soundtype = b.defaultBlockState().getSoundType();
                    this.getLevel().playSound((Player) null, this.getBlockPos(), soundtype.getBreakSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                }
            }
        }
    }

    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
        return new GrindingMillContainer(windowId, playerInventory, this.getInventory(null), this.machineData);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(Constants.MOD_ID + ".container.grinding_mill");
    }

    private static final int[] SLOTS = new int[]{0, 1, 2};
    private static final int[] SLOTS_DOWN = new int[]{3};
    // Only slot 0 feeds the recipe; slot 1 is the tool, which canCombine checks separately.
    // Was NonNullList.of(0, 1), whose first argument is the list's default value rather than
    // an element, so this was really [1] - the tool slot, and never the ingredient.
    private static final List<Integer> INPUT_SLOTS = List.of(0);

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_DOWN;
        } else {
            return SLOTS;
        }
    }

    @Override
    public List<Integer> inputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int fuelSlot() {
        return 2;
    }

    @Override
    public int outputSlot() {
        return 3;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        if (index == this.outputSlot()) {
            return false;
        } else if (index != this.fuelSlot()) {
            if (index == 1) {
                return MachineUtil.allowedInGrindingMillToolSlot(stack);
            } else {
                return MachineUtil.isValidGrindingMillInput(this.level, stack);
            }
        } else {
            return getBurnTime(stack) > 0;
        }
    }
}
