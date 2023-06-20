package com.cynquil.amethyst.rpg.item

import com.cynquil.amethyst.group.AmItemGroup
import com.cynquil.amethyst.item.AmItem
import com.cynquil.amethyst.rpg.Rarity
import com.cynquil.amethyst.rpg.util.HasRarity
import com.cynquil.amethyst.rpg.util.MaybeHasLore
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.util.Identifier

open class RpgItem(
    key: Identifier,

    override val rarity: Rarity,
    override val lore: List<String>? = null,

    group: AmItemGroup? = null,
    settings: Settings = FabricItemSettings()
) : AmItem(key, group, settings.rarity(rarity.minecraftRarity)), HasRarity, MaybeHasLore