package com.cynquil.mango.automation.loader

import com.cynquil.amethyst.datagen.DataProvider
import com.cynquil.amethyst.datagen.itemModel
import com.cynquil.amethyst.datagen.recipe
import com.cynquil.amethyst.id
import com.cynquil.amethyst.rpg.Rarity
import com.cynquil.amethyst.rpg.util.HasRarity
import com.cynquil.amethyst.util.Registerable
import com.cynquil.mango.compact.items.CompactOres
import com.cynquil.mango.items.AugmentedMechanicalCore
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.BlockItem
import net.minecraft.item.Items
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object ChunkLoaderItem : BlockItem(ChunkLoaderBlock, FabricItemSettings()), Registerable, HasRarity, DataProvider {
    override val rarity: Rarity
        get() = ChunkLoaderBlock.rarity

    override fun register() {
        Registry.register(Registries.ITEM, "mango:chunk_loader".id, this)
    }

    override fun generate(provide: DataProvider.Provide) {
        provide {
            recipe {
                ShapedRecipeJsonBuilder
                    .create(RecipeCategory.REDSTONE, this@ChunkLoaderItem)
                    .pattern("#D#")
                    .pattern("NCN")
                    .pattern("QQQ")
                    .input('#', CompactOres.EnchantedEmeraldBlock)
                    .input('D', CompactOres.EnchantedDiamond)
                    .input('N', Items.NETHER_STAR)
                    .input('C', AugmentedMechanicalCore)
                    .input('Q', Items.QUARTZ)
                    .criterion(
                        FabricRecipeProvider.hasItem(AugmentedMechanicalCore),
                        FabricRecipeProvider.conditionsFromItem(AugmentedMechanicalCore)
                    )
                    .offerTo(it)
            }
        }
    }
}