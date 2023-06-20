package com.cynquil.amethyst.rpg

import com.cynquil.amethyst.id
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier

enum class Rarity(val id: Identifier, val style: Style, val secondaryStyle: Style) {
    Common("amethyst:common".id, Style.EMPTY.withColor(Formatting.WHITE), Style.EMPTY.withColor(0xE5E5E5)),
    Uncommon("amethyst:uncommon".id, Style.EMPTY.withColor(Formatting.GREEN), Style.EMPTY.withColor(0x4CE54C)),
    Rare("amethyst:rare".id, Style.EMPTY.withColor(Formatting.BLUE), Style.EMPTY.withColor(0x4C4CE5)),
    Epic("amethyst:epic".id, Style.EMPTY.withColor(Formatting.DARK_PURPLE), Style.EMPTY.withColor(0x990099)),
    Legendary("amethyst:legendary".id, Style.EMPTY.withColor(Formatting.GOLD), Style.EMPTY.withColor(0xE59900)),
    Mythic("amethyst:mythic".id, Style.EMPTY.withColor(Formatting.LIGHT_PURPLE), Style.EMPTY.withColor(0xE54CE5)),
    Special("amethyst:special".id, Style.EMPTY.withColor(Formatting.RED), Style.EMPTY.withColor(0xE54C4C));

    val tooltipText: Text
        get() = Text.translatable("rarity.${id.namespace}.${id.path}")
            .setStyle(style.withBold(true))

    val minecraftRarity
        get() = when (this) {
            Common -> net.minecraft.util.Rarity.COMMON
            Uncommon -> net.minecraft.util.Rarity.UNCOMMON
            Rare -> net.minecraft.util.Rarity.RARE
            else -> net.minecraft.util.Rarity.EPIC
        }

    val enchantmentRarity
        get() = when (this) {
            Common -> net.minecraft.enchantment.Enchantment.Rarity.COMMON
            Uncommon -> net.minecraft.enchantment.Enchantment.Rarity.UNCOMMON
            Rare -> net.minecraft.enchantment.Enchantment.Rarity.RARE
            else -> net.minecraft.enchantment.Enchantment.Rarity.VERY_RARE
        }

    operator fun compareTo(other: net.minecraft.util.Rarity) =
        ordinal.compareTo(other.ordinal)

    companion object {
        fun fromMinecraftRarity(rarity: net.minecraft.util.Rarity) = when (rarity) {
            net.minecraft.util.Rarity.COMMON -> Common
            net.minecraft.util.Rarity.UNCOMMON -> Uncommon
            net.minecraft.util.Rarity.RARE -> Rare
            net.minecraft.util.Rarity.EPIC -> Epic
        }

        fun fromEnchantmentRarity(rarity: net.minecraft.enchantment.Enchantment.Rarity) = when (rarity) {
            net.minecraft.enchantment.Enchantment.Rarity.COMMON -> Common
            net.minecraft.enchantment.Enchantment.Rarity.UNCOMMON -> Uncommon
            net.minecraft.enchantment.Enchantment.Rarity.RARE -> Rare
            net.minecraft.enchantment.Enchantment.Rarity.VERY_RARE -> Epic
        }
    }
}