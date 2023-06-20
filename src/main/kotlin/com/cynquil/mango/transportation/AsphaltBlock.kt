package com.cynquil.mango.transportation

import com.cynquil.amethyst.datagen.*
import com.cynquil.amethyst.id
import com.cynquil.amethyst.rpg.Rarity
import com.cynquil.amethyst.rpg.block.RpgBlock
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Blocks
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.item.Items
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.sound.BlockSoundGroup

object AsphaltBlock : RpgBlock(
    key = "mango:asphalt".id,
    rarity = Rarity.Common,
    settings = FabricBlockSettings.create().strength(1f).sounds(BlockSoundGroup.GRAVEL)
), DataProvider {
    override fun generate(provide: DataProvider.Provide) {
        provide {
            blockModel {
                it.registerSimpleCubeAll(this@AsphaltBlock)
            }
        }

        provide {
            blockLootTable {
                addDrop(this@AsphaltBlock)
            }
        }

        provide {
            recipe {
                ShapelessRecipeJsonBuilder
                    .create(RecipeCategory.BUILDING_BLOCKS, this@AsphaltBlock, 2)
                    .input(Items.BLACK_DYE)
                    .input(Items.GRAVEL)
                    .input(Items.SAND)
                    .input(Items.IRON_INGOT, 3)
                    .criterion(FabricRecipeProvider.hasItem(Items.BLACK_DYE), FabricRecipeProvider.conditionsFromItem(Items.BLACK_DYE))
                    .criterion(FabricRecipeProvider.hasItem(Items.GRAVEL), FabricRecipeProvider.conditionsFromItem(Items.GRAVEL))
                    .criterion(FabricRecipeProvider.hasItem(Items.SAND), FabricRecipeProvider.conditionsFromItem(Items.SAND))
                    .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT), FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                    .offerTo(it)
            }
        }
    }
}