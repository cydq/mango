package com.cynquil.mango.compact

import com.cynquil.amethyst.datagen.DataProvider
import com.cynquil.amethyst.datagen.itemModel
import com.cynquil.amethyst.id
import com.cynquil.amethyst.rpg.Rarity
import com.cynquil.amethyst.rpg.item.RpgItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.data.client.Model
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import java.util.*

class CompactItem(
    key: Identifier,

    override val rarity: Rarity,
    override val lore: List<String>? = null,

    private val glint: Boolean = true,
    private val model: String? = null,

    settings: Settings = FabricItemSettings()
) : RpgItem(key, rarity, settings = settings), DataProvider {
    override fun hasGlint(stack: ItemStack) =
        glint || super.hasGlint(stack)

    override fun generate(provide: DataProvider.Provide) {
        if (model == null)
            return

        provide(
            itemModel {
                it.register(this@CompactItem, Model(Optional.of("minecraft:item/$model".id), Optional.empty()))
            }
        )
    }
}