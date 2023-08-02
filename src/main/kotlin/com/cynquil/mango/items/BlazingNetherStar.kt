package com.cynquil.mango.items

import com.cynquil.amethyst.datagen.DataProvider
import com.cynquil.amethyst.datagen.itemModel
import com.cynquil.amethyst.datagen.recipe
import com.cynquil.amethyst.id
import com.cynquil.amethyst.rpg.Rarity
import com.cynquil.amethyst.rpg.item.RpgItem
import com.cynquil.mango.compact.Compactor
import com.cynquil.mango.compact.items.CompactOres
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.client.Models
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.item.Items
import net.minecraft.recipe.book.RecipeCategory

object BlazingNetherStar : RpgItem(
    key = "mango:blazing_nether_star".id,
    rarity = Rarity.Rare
), DataProvider {
    override fun generate(provide: DataProvider.Provide) {
        provide {
            itemModel {
                it.register(this@BlazingNetherStar, Models.GENERATED)
            }
        }

        provide {
            recipe {
                ShapelessRecipeJsonBuilder
                    .create(RecipeCategory.MISC, this@BlazingNetherStar)
                    .input(Items.NETHER_STAR)
                    .input(Items.BLAZE_POWDER)
                    .input(Items.FIRE_CHARGE)
                    .input(CompactOres.EnchantedIronIngot)
                    .criterion(FabricRecipeProvider.hasItem(CompactOres.EnchantedIronIngot), FabricRecipeProvider.conditionsFromItem(CompactOres.EnchantedIronIngot))
                    .criterion(FabricRecipeProvider.hasItem(Items.NETHER_STAR), FabricRecipeProvider.conditionsFromItem(Items.NETHER_STAR))
                    .offerTo(it)
            }
        }
    }
}