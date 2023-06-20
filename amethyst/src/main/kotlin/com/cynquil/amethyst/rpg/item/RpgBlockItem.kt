package com.cynquil.amethyst.rpg.item

import com.cynquil.amethyst.item.AmBlockItem
import com.cynquil.amethyst.rpg.Rarity
import com.cynquil.amethyst.rpg.block.RpgBlock
import com.cynquil.amethyst.rpg.util.HasRarity
import com.cynquil.amethyst.rpg.util.MaybeHasLore
import net.fabricmc.fabric.api.item.v1.FabricItemSettings

open class RpgBlockItem(
    override val block: RpgBlock,
    settings: Settings = FabricItemSettings()
) : AmBlockItem(block, settings), HasRarity, MaybeHasLore {
    override val rarity: Rarity
        get() = block.rarity

    override val lore: List<String>?
        get() = block.lore
}