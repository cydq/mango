package com.cynquil.amethyst.block

import com.cynquil.amethyst.group.AmItemGroup
import com.cynquil.amethyst.item.AmBlockItem
import com.cynquil.amethyst.util.Keyed
import com.cynquil.amethyst.util.Registerable
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

open class AmBlock(
    override val key: Identifier,

    val group: AmItemGroup? = null,
    settings: Settings? = null,
) : Block(settings ?: FabricBlockSettings.create()), Keyed, Registerable {
    open val item by lazy { AmBlockItem(this) }

    override fun register() {
        Registry.register(Registries.BLOCK, key, this)
        item.register()
    }
}