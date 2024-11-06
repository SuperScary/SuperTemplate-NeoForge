package com.example.examplemod.datagen.providers.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class LootTableProvider extends net.minecraft.data.loot.LootTableProvider {

    private static final List<SubProviderEntry> SUB_PROVIDERS = List.of(new SubProviderEntry(DropProvider::new, LootContextParamSets.BLOCK));

    public LootTableProvider (PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, Set.of(), SUB_PROVIDERS, provider);
    }

    @Override
    protected void validate (@NotNull WritableRegistry<LootTable> writableRegistry, @NotNull ValidationContext validationContext, ProblemReporter.@NotNull Collector collector) {

    }

}