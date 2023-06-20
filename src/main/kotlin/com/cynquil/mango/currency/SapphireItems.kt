package com.cynquil.mango.currency

import com.cynquil.amethyst.datagen.DataProvider
import com.cynquil.amethyst.datagen.itemModel
import com.cynquil.amethyst.id
import com.cynquil.amethyst.module.Container
import com.cynquil.amethyst.rpg.Rarity
import com.cynquil.amethyst.rpg.item.RpgItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.item.TooltipContext
import net.minecraft.data.client.Models
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import net.minecraft.world.World
import kotlin.math.abs

object SapphireItems : Container() {
    val SapphireFragment = Item("mango:sapphire_fragment".id, Rarity.Common,    value = 1)
    val SapphireSmall =    Item("mango:sapphire_small".id,    Rarity.Uncommon,  value = 64)
    val SapphireMedium =   Item("mango:sapphire_medium".id,   Rarity.Rare,      value = 64 * 64)
    val SapphireLarge =    Item("mango:sapphire_large".id,    Rarity.Epic,      value = 64 * 64 * 64)
    val SapphireRefined =  Item("mango:sapphire_refined".id,  Rarity.Legendary, value = 64 * 64 * 64 * 64)
    val SapphireCube =     Item("mango:sapphire_cube".id,     Rarity.Mythic,    value = 64 * 64 * 64 * 64 * 64)

    class Item(
        id: Identifier,
        rarity: Rarity,
        private val value: Long,
        settings: FabricItemSettings = FabricItemSettings().maxCount(64)
    ) : RpgItem(
        id,
        rarity,
        settings = settings
    ), DataProvider {
        override fun generate(provide: DataProvider.Provide) {
            provide {
                itemModel {
                    it.register(this@Item, Models.GENERATED)
                }
            }
        }

        override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
            tooltip.add(Text.literal("${stack.count}x ${formatValue(value)}").formatted(Formatting.DARK_GRAY))
            tooltip.add(Text.literal("= ${formatValue(stack.count * value)}").formatted(Formatting.DARK_GRAY))
        }
    }

    fun formatValue(value: Long): String {
        val a = abs(value).toDouble()
        val s = if (a < 0) "-" else ""

        if (a >= 1_000_000_000_000)
            return s + "%.2f".format(a / 1_000_000_000_000.0) + "t$"

        if (a >= 1_000_000_000)
            return s + "%.2f".format(a / 1_000_000_000.0) + "b$"

        if (a >= 1_000_000)
            return s + "%.2f".format(a / 1_000_000.0) + "m$"

        if (a >= 1_000)
            return s + "%.2f".format(a / 1_000.0) + "k$"

        return "${value}$"
    }
}