package com.cynquil.mango.payphone

import com.cynquil.amethyst.datagen.DataProvider
import com.cynquil.amethyst.datagen.recipe
import com.cynquil.amethyst.module.Container
import com.cynquil.amethyst.rpg.Rarity
import com.cynquil.amethyst.rpg.item.RpgItem
import com.cynquil.mango.compact.Compactor
import com.cynquil.mango.compact.Decompactor
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

object PayphoneUpgrades : Container() {
    val PayphoneUpgradeAnvil = Item(upgrade = PayphoneFunction.Anvil, rarity = Rarity.Uncommon)
    val PayphoneUpgradeWorkbench = Item(upgrade = PayphoneFunction.Workbench, rarity = Rarity.Uncommon)
    val PayphoneUpgradeSmithingTable = Item(upgrade = PayphoneFunction.Smithing, rarity = Rarity.Uncommon)
    val PayphoneUpgradeEnchantmentTable = Item(upgrade = PayphoneFunction.Enchanting, rarity = Rarity.Uncommon)

    val PayphoneUpgradeStoneCutter = Item(upgrade = PayphoneFunction.StoneCutting, rarity = Rarity.Uncommon) { provide ->
        provide {
            recipe {
                ShapedRecipeJsonBuilder
                    .create(RecipeCategory.MISC, this@Item)
                    .pattern("#DJ")
                    .pattern("ECI")
                    .pattern("XBO")
                    .input('#', Items.POLISHED_BLACKSTONE)
                    .input('D', Items.POLISHED_GRANITE)
                    .input('J', Items.POLISHED_DIORITE)
                    .input('E', Items.POLISHED_DEEPSLATE)
                    .input('C', Items.STONECUTTER)
                    .input('I', Items.POLISHED_ANDESITE)
                    .input('X', Items.SMOOTH_SANDSTONE)
                    .input('B', Items.SMOOTH_RED_SANDSTONE)
                    .input('O', Items.MUD_BRICKS)
                    .criterion(FabricRecipeProvider.hasItem(Items.STONECUTTER), FabricRecipeProvider.conditionsFromItem(Items.STONECUTTER))
                    .offerTo(it)
            }
        }
    }

    val PayphoneUpgradeCompact = Item(upgrade = PayphoneFunction.Compact, rarity = Rarity.Uncommon) { provide ->
        provide {
            recipe {
                ShapedRecipeJsonBuilder
                    .create(RecipeCategory.MISC, this@Item)
                    .pattern(" # ")
                    .pattern("SCS")
                    .pattern(" P ")
                    .input('#', Items.POINTED_DRIPSTONE)
                    .input('S', Items.SLIME_BLOCK)
                    .input('C', Compactor)
                    .input('P', Items.PITCHER_PLANT)
                    .criterion(FabricRecipeProvider.hasItem(Compactor), FabricRecipeProvider.conditionsFromItem(Compactor))
                    .offerTo(it)
            }
        }
    }

    val PayphoneUpgradeDecompact = Item(upgrade = PayphoneFunction.Decompact, rarity = Rarity.Uncommon)  { provide ->
        provide {
            recipe {
                ShapedRecipeJsonBuilder
                    .create(RecipeCategory.MISC, this@Item)
                    .pattern("#DJ")
                    .pattern("ECI")
                    .pattern("XBO")
                    .input('#', Items.DEEPSLATE_COPPER_ORE)
                    .input('D', Items.DEEPSLATE_EMERALD_ORE)
                    .input('J', Items.DEEPSLATE_COPPER_ORE)
                    .input('E', Items.DEEPSLATE_IRON_ORE)
                    .input('C', Decompactor)
                    .input('I', Items.DEEPSLATE_REDSTONE_ORE)
                    .input('X', Items.DEEPSLATE_LAPIS_ORE)
                    .input('B', Items.DEEPSLATE_DIAMOND_ORE)
                    .input('O', Items.DEEPSLATE_GOLD_ORE)
                    .criterion(FabricRecipeProvider.hasItem(Compactor), FabricRecipeProvider.conditionsFromItem(Compactor))
                    .offerTo(it)
            }
        }
    }

    val PayphoneUpgradeEnderchest = Item(upgrade = PayphoneFunction.EnderChest, rarity = Rarity.Uncommon)
    val PayphoneUpgradeSlot1 = Item(upgrade = PayphoneFunction.Slot1, rarity = Rarity.Uncommon)
    val PayphoneUpgradeSlot2 = Item(upgrade = PayphoneFunction.Slot2, rarity = Rarity.Rare)
    val PayphoneUpgradeSlot3 = Item(upgrade = PayphoneFunction.Slot3, rarity = Rarity.Epic)
    val PayphoneUpgradeSlot4 = Item(upgrade = PayphoneFunction.Slot4, rarity = Rarity.Legendary)
    val PayphoneUpgradeSlot5 = Item(upgrade = PayphoneFunction.Slot5, rarity = Rarity.Mythic)

    class Item(
        val upgrade: PayphoneFunction,
        rarity: Rarity,
        private val provider: (Item.(provide: DataProvider.Provide) -> Unit)? = null
    ) : RpgItem(upgrade.id, rarity, listOf("Right click to upgrade", "your selected payphone!")), DataProvider {
        override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
            val stack = user.getStackInHand(hand)
            val item = PayphoneUtils.getPayphone(user) ?: return TypedActionResult.pass(stack)

            if (PayphoneUtils.hasUpgrade(item, upgrade))
                return TypedActionResult.pass(stack)

            PayphoneUtils.addFunction(item, upgrade)

            user.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f)
            stack.count--

            return TypedActionResult.success(stack)
        }

        override fun generate(provide: DataProvider.Provide) {
            provider?.invoke(this, provide)
        }
    }
}