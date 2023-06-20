package com.cynquil.mango.compact

import com.cynquil.mango.compact.items.CompactOres
import com.cynquil.mango.currency.ExperienceOrbs
import com.cynquil.mango.currency.SapphireItems
import net.minecraft.item.Item
import net.minecraft.item.Items

object CompressionChain {
    val compressesInto = mutableMapOf<Item, Item?>()
    val expandsInto = mutableMapOf<Item, Item?>()

    private fun define(vararg items: Item) {
        items.forEachIndexed { index, item ->
            expandsInto[item] = items.getOrNull(index - 1)
            compressesInto[item] = items.getOrNull(index + 1)
        }
    }

    init {
        define(ExperienceOrbs.ChaosOrb, ExperienceOrbs.ExaltedOrb, ExperienceOrbs.DivineOrb)

        define(
            SapphireItems.SapphireFragment,
            SapphireItems.SapphireSmall,
            SapphireItems.SapphireMedium,
            SapphireItems.SapphireLarge,
            SapphireItems.SapphireRefined,
            SapphireItems.SapphireCube
        )

        define(Items.COPPER_INGOT, CompactOres.EnchantedCopperIngot, CompactOres.EnchantedCopperBlock, CompactOres.MagicalCopperIngot, CompactOres.MagicalCopperBlock)
        define(Items.DIAMOND, CompactOres.EnchantedDiamond, CompactOres.EnchantedDiamondBlock, CompactOres.MagicalDiamond, CompactOres.MagicalDiamondBlock)
        define(Items.EMERALD, CompactOres.EnchantedEmerald, CompactOres.EnchantedEmeraldBlock, CompactOres.MagicalEmerald, CompactOres.MagicalEmeraldBlock)
        define(Items.GOLD_INGOT, CompactOres.EnchantedGoldIngot, CompactOres.EnchantedGoldBlock, CompactOres.MagicalGoldIngot, CompactOres.MagicalGoldBlock)
        define(Items.IRON_INGOT, CompactOres.EnchantedIronIngot, CompactOres.EnchantedIronBlock, CompactOres.MagicalIronIngot, CompactOres.MagicalIronBlock)
        define(Items.LAPIS_LAZULI, CompactOres.EnchantedLapisLazuli, CompactOres.EnchantedLapisBlock, CompactOres.MagicalLapisLazuli, CompactOres.MagicalLapisBlock)
        define(Items.REDSTONE, CompactOres.EnchantedRedstone, CompactOres.EnchantedRedstoneBlock, CompactOres.MagicalRedstone, CompactOres.MagicalRedstoneBlock)
    }
}