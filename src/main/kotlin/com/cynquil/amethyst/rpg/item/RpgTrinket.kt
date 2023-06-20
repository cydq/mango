package com.cynquil.amethyst.rpg.item

import com.cynquil.amethyst.group.AmItemGroup
import com.cynquil.amethyst.rpg.Rarity
import com.cynquil.amethyst.rpg.item.RpgItem
import com.cynquil.mango.Mango
import dev.emi.trinkets.api.TrinketsApi
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.ItemGroup
import net.minecraft.registry.RegistryKey
import net.minecraft.util.Identifier

open class RpgTrinket(
    key: Identifier,

    rarity: Rarity,
    lore: List<String>? = null,

    group: AmItemGroup? = null,
    settings: Settings = FabricItemSettings()
) : RpgItem(key, rarity, lore, group, settings) {
    val trinket = TrinketsApi.getTrinket(this)

    override fun register() {
        super.register()

        TrinketsApi.registerTrinket(this, trinket)
    }
}