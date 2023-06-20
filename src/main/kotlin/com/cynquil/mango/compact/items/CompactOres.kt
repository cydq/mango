package com.cynquil.mango.compact.items

import com.cynquil.amethyst.id
import com.cynquil.amethyst.module.Container
import com.cynquil.amethyst.rpg.Rarity
import com.cynquil.mango.compact.CompactItem
import net.minecraft.item.Items

object CompactOres : Container() {
    val EnchantedCopperIngot = CompactItem("mango:enchanted_copper_ingot".id, Rarity.Uncommon, model = "copper_ingot")
    val EnchantedCopperBlock = CompactItem("mango:enchanted_copper_block".id, Rarity.Rare, model = "copper_block")
    val MagicalCopperIngot = CompactItem("mango:magical_copper_ingot".id, Rarity.Epic)
    val MagicalCopperBlock = CompactItem("mango:magical_copper_block".id, Rarity.Legendary)

    val EnchantedDiamond = CompactItem("mango:enchanted_diamond".id, Rarity.Uncommon, model = "diamond")
    val EnchantedDiamondBlock = CompactItem("mango:enchanted_diamond_block".id, Rarity.Rare, model = "diamond_block")
    val MagicalDiamond = CompactItem("mango:magical_diamond".id, Rarity.Epic)
    val MagicalDiamondBlock = CompactItem("mango:magical_diamond_block".id, Rarity.Legendary)

    val EnchantedEmerald = CompactItem("mango:enchanted_emerald".id, Rarity.Uncommon, model = "emerald")
    val EnchantedEmeraldBlock = CompactItem("mango:enchanted_emerald_block".id, Rarity.Rare, model = "emerald_block")
    val MagicalEmerald = CompactItem("mango:magical_emerald".id, Rarity.Epic)
    val MagicalEmeraldBlock = CompactItem("mango:magical_emerald_block".id, Rarity.Legendary)

    val EnchantedGoldIngot = CompactItem("mango:enchanted_gold_ingot".id, Rarity.Uncommon, model = "gold_ingot")
    val EnchantedGoldBlock = CompactItem("mango:enchanted_gold_block".id, Rarity.Rare, model = "gold_block")
    val MagicalGoldIngot = CompactItem("mango:magical_gold_ingot".id, Rarity.Epic)
    val MagicalGoldBlock = CompactItem("mango:magical_gold_block".id, Rarity.Legendary)

    val EnchantedIronIngot = CompactItem("mango:enchanted_iron_ingot".id, Rarity.Uncommon, model = "iron_ingot")
    val EnchantedIronBlock = CompactItem("mango:enchanted_iron_block".id, Rarity.Rare, model = "iron_block")
    val MagicalIronIngot = CompactItem("mango:magical_iron_ingot".id, Rarity.Epic)
    val MagicalIronBlock = CompactItem("mango:magical_iron_block".id, Rarity.Legendary)

    val EnchantedLapisLazuli = CompactItem("mango:enchanted_lapis_lazuli".id, Rarity.Uncommon, model = "lapis_lazuli")
    val EnchantedLapisBlock = CompactItem("mango:enchanted_lapis_block".id, Rarity.Rare, model = "lapis_block")
    val MagicalLapisLazuli = CompactItem("mango:magical_lapis_lazuli".id, Rarity.Epic)
    val MagicalLapisBlock = CompactItem("mango:magical_lapis_block".id, Rarity.Legendary)

    val EnchantedRedstone = CompactItem("mango:enchanted_redstone".id, Rarity.Uncommon, model = "redstone")
    val EnchantedRedstoneBlock = CompactItem("mango:enchanted_redstone_block".id, Rarity.Rare, model = "redstone_block")
    val MagicalRedstone = CompactItem("mango:magical_redstone".id, Rarity.Epic)
    val MagicalRedstoneBlock = CompactItem("mango:magical_redstone_block".id, Rarity.Legendary)
}