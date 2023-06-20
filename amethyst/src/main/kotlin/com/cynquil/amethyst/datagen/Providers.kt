package com.cynquil.amethyst.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.server.recipe.RecipeJsonProvider
import java.util.function.Consumer

fun blockLootTable(
    generate: FabricBlockLootTableProvider.() -> Unit = {}
) = FabricDataGenerator.Pack.Factory<FabricBlockLootTableProvider> { output: FabricDataOutput ->
    object : FabricBlockLootTableProvider(output) {
        override fun generate() = generate(this)
        override fun getName() = System.nanoTime().toString()
    }
}

fun blockModel(
    generate: FabricModelProvider.(BlockStateModelGenerator) -> Unit = {}
) = FabricDataGenerator.Pack.Factory<FabricModelProvider> { output: FabricDataOutput ->
    object : FabricModelProvider(output) {
        override fun generateBlockStateModels(generator: BlockStateModelGenerator) = generate(this, generator)
        override fun generateItemModels(generator: ItemModelGenerator) {}
        override fun getName() = System.nanoTime().toString()
    }
}

fun itemModel(
    generate: FabricModelProvider.(ItemModelGenerator) -> Unit = {}
) = FabricDataGenerator.Pack.Factory<FabricModelProvider> { output: FabricDataOutput ->
    object : FabricModelProvider(output) {
        override fun generateBlockStateModels(generator: BlockStateModelGenerator) {}
        override fun generateItemModels(generator: ItemModelGenerator) = generate(this, generator)
        override fun getName() = System.nanoTime().toString()
    }
}

fun recipe(
    generate: FabricRecipeProvider.(Consumer<RecipeJsonProvider>) -> Unit = {},
) = FabricDataGenerator.Pack.Factory<FabricRecipeProvider> { output: FabricDataOutput ->
    object : FabricRecipeProvider(output) {
        override fun generate(exporter: Consumer<RecipeJsonProvider>) = generate(this, exporter)
        override fun getName() = System.nanoTime().toString()
    }
}

