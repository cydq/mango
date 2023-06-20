package com.cynquil.amethyst.rpg.block

import com.cynquil.amethyst.block.AmBlock
import com.cynquil.amethyst.group.AmItemGroup
import com.cynquil.amethyst.item.AmBlockItem
import com.cynquil.amethyst.rpg.Rarity
import com.cynquil.amethyst.rpg.item.RpgBlockItem
import com.cynquil.amethyst.rpg.util.HasRarity
import com.cynquil.amethyst.rpg.util.MaybeHasLore
import net.minecraft.item.ItemGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.util.Identifier

open class RpgBlock(
    key: Identifier,

    override val rarity: Rarity,
    override val lore: List<String>? = null,

    group: AmItemGroup? = null,
    settings: Settings? = null
) : AmBlock(key, group, settings), HasRarity, MaybeHasLore {
    override val item by lazy { RpgBlockItem(this) }
}