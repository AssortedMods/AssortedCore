package com.grim3212.assorted.machines.common.blocks.blockentity;

import com.google.common.collect.Lists;
import com.grim3212.assorted.machines.api.crafting.BaseMachineRecipe;
import com.grim3212.assorted.machines.api.crafting.MachineRecipeInput;
import com.grim3212.assorted.machines.api.machines.MachineTier;
import com.grim3212.assorted.machines.common.blocks.BaseMachineBlock;
import com.grim3212.assorted.machines.common.inventory.BaseMachineInventory;
import com.grim3212.assorted.lib.core.inventory.IInventoryBlockEntity;
import com.grim3212.assorted.lib.core.inventory.IPlatformInventoryStorageHandler;
import com.grim3212.assorted.lib.platform.Services;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class BaseMachineBlockEntity extends BlockEntity implements IInventoryBlockEntity, MenuProvider, Nameable, RecipeCraftingHolder, StackedContentsCompatible {

    /**
     * Recipes no longer carry their own id, so the crafted-recipe tally is keyed by
     * {@code ResourceKey<Recipe<?>>} and written with the same codec vanilla's furnace uses.
     */
    private static final Codec<Map<ResourceKey<Recipe<?>>, Integer>> RECIPES_USED_CODEC = Codec.unboundedMap(Recipe.KEY_CODEC, Codec.INT);

    protected final Object2IntOpenHashMap<ResourceKey<Recipe<?>>> recipes = new Object2IntOpenHashMap<>();
    protected final RecipeType<? extends BaseMachineRecipe> recipeType;
    protected final MachineTier tier;
    protected int burnTime;
    protected int recipesUsed;
    protected int cookTime;
    protected int cookTimeTotal;
    protected final ContainerData machineData = new ContainerData() {
        public int get(int index) {
            switch (index) {
                case 0:
                    return BaseMachineBlockEntity.this.burnTime;
                case 1:
                    return BaseMachineBlockEntity.this.recipesUsed;
                case 2:
                    return BaseMachineBlockEntity.this.cookTime;
                case 3:
                    return BaseMachineBlockEntity.this.cookTimeTotal;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case 0:
                    BaseMachineBlockEntity.this.burnTime = value;
                    break;
                case 1:
                    BaseMachineBlockEntity.this.recipesUsed = value;
                    break;
                case 2:
                    BaseMachineBlockEntity.this.cookTime = value;
                    break;
                case 3:
                    BaseMachineBlockEntity.this.cookTimeTotal = value;
            }

        }

        public int getCount() {
            return 4;
        }
    };
    protected int defaultCookTime;
    private Component customName;
    protected NonNullList<ItemStack> items;

    protected IPlatformInventoryStorageHandler platformInventoryStorageHandler;
    private final Map<Direction, BaseMachineInventory> inventoryCache;

    public BaseMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, MachineTier tier, int slots, int defaultCookTime, RecipeType<? extends BaseMachineRecipe> recipeTypeIn) {
        super(type, pos, state);
        this.tier = tier;
        this.recipeType = recipeTypeIn;
        this.defaultCookTime = defaultCookTime;
        this.items = NonNullList.withSize(slots, ItemStack.EMPTY);
        this.inventoryCache = new HashMap<>();
    }

    @Override
    public IPlatformInventoryStorageHandler getStorageHandler() {
        if (this.platformInventoryStorageHandler == null) {
            this.platformInventoryStorageHandler = this.createStorageHandler();
        }

        return this.platformInventoryStorageHandler;
    }

    public BaseMachineInventory getInventory(@Nullable Direction direction) {
        if (!this.inventoryCache.containsKey(direction)) {
            this.inventoryCache.put(direction, new BaseMachineInventory(this, direction));
        }

        return this.inventoryCache.get(direction);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (this.platformInventoryStorageHandler != null) {
            this.platformInventoryStorageHandler.invalidate();
        }

        this.inventoryCache.clear();
    }

    public IPlatformInventoryStorageHandler createStorageHandler() {
        return Services.INVENTORY.createSidedStorageInventoryHandler(this::getInventory);
    }

    public abstract List<Integer> inputSlots();

    public abstract int fuelSlot();

    public abstract int outputSlot();

    public abstract int[] getSlotsForFace(Direction side);

    public boolean inputsWithItems() {
        return this.inputSlots().stream().allMatch((slot) -> !this.items.get(slot).isEmpty());
    }

    public boolean canPlaceItem(int index, ItemStack stack) {
        if (index == this.outputSlot()) {
            return false;
        } else if (index != this.fuelSlot()) {
            return true;
        } else {
            return this.getBurnTime(stack) > 0;
        }
    }

    private static void splitAndSpawnExperience(ServerLevel world, Vec3 pos, int craftedAmount, float experience) {
        int i = Mth.floor((float) craftedAmount * experience);
        float f = Mth.frac((float) craftedAmount * experience);
        if (f != 0.0F && world.getRandom().nextFloat() < f) {
            ++i;
        }

        ExperienceOrb.award(world, pos, i);
    }

    /**
     * The recipe the input slots currently satisfy. Always empty on the client, which has no recipe
     * manager.
     */
    @SuppressWarnings("unchecked")
    public Optional<RecipeHolder<BaseMachineRecipe>> checkRecipe() {
        if (!(this.level instanceof ServerLevel serverLevel)) {
            return Optional.empty();
        }

        MachineRecipeInput input = new MachineRecipeInput(this.inputSlots().stream().map(this.items::get).toList());
        return serverLevel.recipeAccess().getRecipeFor((RecipeType<BaseMachineRecipe>) this.recipeType, input, serverLevel);
    }

    public void tick() {
        boolean flag = this.isBurning();
        boolean flag1 = false;
        if (this.isBurning()) {
            --this.burnTime;
        }

        ItemStack fuelSlot = this.items.get(this.fuelSlot());
        if (this.isBurning() || !fuelSlot.isEmpty() && this.inputsWithItems()) {
            RecipeHolder<BaseMachineRecipe> holder = this.checkRecipe().orElse(null);
            BaseMachineRecipe irecipe = holder == null ? null : holder.value();

            if (!this.isBurning() && this.canCombine(irecipe)) {
                this.burnTime = this.getBurnTime(fuelSlot);
                this.recipesUsed = this.burnTime;
                if (this.isBurning()) {
                    flag1 = true;
                    // The crafting remainder is a nullable ItemStackTemplate now rather than an
                    // Item plus a hasCraftingRemainingItem() flag.
                    // NeoForge-only deprecation; its replacement is not on the vanilla jar.
                    @SuppressWarnings("deprecation")
                    ItemStackTemplate remainder = fuelSlot.getItem().getCraftingRemainder();
                    if (remainder != null)
                        this.items.set(this.fuelSlot(), remainder.create());
                    else if (!fuelSlot.isEmpty()) {
                        fuelSlot.shrink(1);
                        if (fuelSlot.isEmpty()) {
                            this.items.set(this.fuelSlot(), ItemStack.EMPTY);
                        }
                    }
                }
            }

            if (this.isBurning() && this.canCombine(irecipe)) {
                ++this.cookTime;
                if (this.cookTime == this.cookTimeTotal) {
                    this.cookTime = 0;
                    this.cookTimeTotal = this.getCookTime();
                    this.combine(holder);
                    flag1 = true;
                }
            } else {
                this.cookTime = 0;
            }
        } else if (!this.isBurning() && this.cookTime > 0) {
            this.cookTime = Mth.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
        }

        if (flag != this.isBurning()) {
            flag1 = true;
            this.level.setBlock(this.worldPosition, this.level.getBlockState(this.worldPosition).setValue(BaseMachineBlock.ON, this.isBurning()), 3);
        }

        if (flag1) {
            this.setChanged();
        }
    }

    protected boolean isBurning() {
        return this.burnTime > 0;
    }

    protected abstract boolean canCombine(@Nullable BaseMachineRecipe recipeIn);

    protected abstract void combine(@Nullable RecipeHolder<BaseMachineRecipe> holder);

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public int getBurnTime(ItemStack fuel) {
        if (fuel.isEmpty() || this.level == null) {
            return 0;
        } else {
            return Services.PLATFORM.getFuelTime(this.level, fuel);
        }
    }

    public int getCookTime() {
        return (int) ((this.checkRecipe().map((holder) -> holder.value().getCookTime()).orElse(this.defaultCookTime)) * this.tier.getSpeedModifier());
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public void setCookTimeTotal(int cookTimeTotal) {
        this.cookTimeTotal = cookTimeTotal;
    }

    @Override
    public abstract AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity);

    @Override
    public Component getName() {
        return this.customName != null ? this.customName : this.getDefaultName();
    }

    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    @Override
    public Component getCustomName() {
        return this.customName;
    }

    public void setCustomName(Component name) {
        this.customName = name;
    }

    protected abstract Component getDefaultName();

    @Override
    public RecipeHolder<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void setRecipeUsed(@Nullable RecipeHolder<?> recipe) {
        if (recipe != null) {
            this.recipes.addTo(recipe.id(), 1);
        }
    }

    @Override
    public void awardUsedRecipes(Player player, List<ItemStack> stacks) {
    }

    public void unlockRecipes(Player player) {
        if (!(player.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        List<RecipeHolder<?>> list = this.grantStoredRecipeExperience(serverLevel, player.position());
        player.awardRecipes(list);
        this.recipes.clear();
    }

    public List<RecipeHolder<?>> grantStoredRecipeExperience(ServerLevel world, Vec3 pos) {
        List<RecipeHolder<?>> list = Lists.newArrayList();

        for (Entry<ResourceKey<Recipe<?>>> entry : this.recipes.object2IntEntrySet()) {
            world.recipeAccess().byKey(entry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                splitAndSpawnExperience(world, pos, entry.getIntValue(), ((BaseMachineRecipe) recipe.value()).getExperience());
            });
        }

        return list;
    }

    @Override
    public void fillStackedContents(StackedItemContents helper) {
        for (ItemStack itemstack : this.items) {
            helper.accountStack(itemstack);
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.items = NonNullList.withSize(this.items.size(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, this.items);
        this.burnTime = input.getIntOr("BurnTime", 0);
        this.cookTime = input.getIntOr("CookTime", 0);
        this.cookTimeTotal = input.getIntOr("CookTimeTotal", 0);
        this.recipesUsed = this.getBurnTime(this.items.get(1));
        this.recipes.clear();
        this.recipes.putAll(input.read("RecipesUsed", RECIPES_USED_CODEC).orElse(Map.of()));
        this.customName = parseCustomNameSafe(input, "CustomName");
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        output.putInt("BurnTime", this.burnTime);
        output.putInt("CookTime", this.cookTime);
        output.putInt("CookTimeTotal", this.cookTimeTotal);
        ContainerHelper.saveAllItems(output, this.items);
        output.store("RecipesUsed", RECIPES_USED_CODEC, this.recipes);
        output.storeNullable("CustomName", ComponentSerialization.CODEC, this.customName);
    }

    /**
     * Drops the inventory and banked experience when the block goes away. It has to happen here:
     * the block entity is already gone by {@code affectNeighborsAfterRemoval}.
     */
    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        super.preRemoveSideEffects(pos, state);

        if (this.level == null) {
            return;
        }

        Containers.dropContents(this.level, pos, this.items);

        if (this.level instanceof ServerLevel serverLevel) {
            this.grantStoredRecipeExperience(serverLevel, Vec3.atCenterOf(pos));
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

}
