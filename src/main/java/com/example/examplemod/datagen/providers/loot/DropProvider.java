package com.example.examplemod.datagen.providers.loot;

import com.example.examplemod.core.Mod;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class DropProvider extends BlockLootSubProvider {

    private final Map<Block, Function<Block, LootTable.Builder>> overrides = createOverrides();

    @NotNull
    private ImmutableMap<Block, Function<Block, LootTable.Builder>> createOverrides () {
        return ImmutableMap.<Block, Function<Block, LootTable.Builder>>builder()
                .build();
    }

    public DropProvider (HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks () {
        return BuiltInRegistries.BLOCK.stream().filter(entry -> entry.getLootTable().location().getNamespace().equals(Mod.MOD_ID))
                .toList();
    }

    @Override
    public void generate () {
        for (var block : getKnownBlocks()) {
            add(block, overrides.getOrDefault(block, this::defaultBuilder).apply(block));
        }
    }

    private LootTable.Builder defaultBuilder (Block block) {
        LootPoolSingletonContainer.Builder<?> entry = LootItem.lootTableItem(block);
        LootPool.Builder pool = LootPool.lootPool().setRolls(ConstantValue.exactly(1f)).add(entry).when(ExplosionCondition.survivesExplosion());
        return LootTable.lootTable().withPool(pool);
    }

    private Function<Block, LootTable.Builder> oreBlock (Block block, Item itemDropped) {
        return b -> createSilkTouchDispatchTable(block,
                LootItem.lootTableItem(itemDropped)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                        .apply(ApplyBonusCount.addUniformBonusCount(getEnchantment(Enchantments.FORTUNE)))
                        .apply(ApplyExplosionDecay.explosionDecay()));
    }

    private void dropSelfWithState (Block block, Property<?>[] properties) {
        CopyBlockState.Builder blockStateBuilder = CopyBlockState.copyState(block);
        for (Property<?> property : properties) {
            blockStateBuilder.copy(property);
        }
        LootPoolSingletonContainer.Builder<?> itemLootBuilder = LootItem.lootTableItem(block);
        itemLootBuilder.apply(blockStateBuilder);
        LootPool.Builder lootPoolBuilder = LootPool.lootPool();
        lootPoolBuilder.setRolls(ConstantValue.exactly(1.0f));
        lootPoolBuilder.add(itemLootBuilder);

        lootPoolBuilder = applyExplosionCondition(block, lootPoolBuilder);
        LootTable.Builder lootTableBuilder = LootTable.lootTable();
        lootTableBuilder.withPool(lootPoolBuilder);
        this.add(block, lootTableBuilder);
    }

    @SuppressWarnings("SameParameterValue")
    protected final Holder<Enchantment> getEnchantment (ResourceKey<Enchantment> key) {
        return registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(key);
    }

}
