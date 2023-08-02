package com.cynquil.mango.items

import com.cynquil.amethyst.datagen.DataProvider
import com.cynquil.amethyst.datagen.itemModel
import com.cynquil.amethyst.datagen.recipe
import com.cynquil.amethyst.id
import com.cynquil.amethyst.rpg.Rarity
import com.cynquil.amethyst.rpg.item.RpgItem
import com.cynquil.mango.compact.items.CompactOres
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.client.Models
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.recipe.book.RecipeCategory

object AugmentedMechanicalCore : RpgItem(
    key = "mango:augmented_mechanical_core".id,
    rarity = Rarity.Uncommon
), DataProvider {
    override fun generate(provide: DataProvider.Provide) {
        provide {
            itemModel {
                it.register(this@AugmentedMechanicalCore, Models.GENERATED)
            }
        }

        provide {
            recipe {
                ShapedRecipeJsonBuilder
                    .create(RecipeCategory.MISC, this@AugmentedMechanicalCore)
                    .pattern("iii")
                    .pattern("i i")
                    .pattern("iii")
                    .input('i', CompactOres.EnchantedIronIngot)
                    .criterion(
                        FabricRecipeProvider.hasItem(CompactOres.EnchantedIronIngot),
                        FabricRecipeProvider.conditionsFromItem(CompactOres.EnchantedIronIngot)
                    )
                    .offerTo(it)
            }
        }
    }
}