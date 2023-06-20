package com.cynquil.amethyst.item

import com.cynquil.amethyst.block.AmBlock
import com.cynquil.amethyst.group.AmItemGroup
import com.cynquil.amethyst.util.Keyed
import com.cynquil.amethyst.util.Registerable
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.BlockItem
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

open class AmBlockItem(open val block: AmBlock, settings: Settings = FabricItemSettings())
    : BlockItem(block, settings), Keyed, Registerable
{
    override val key: Identifier
        get() = block.key

    val group: AmItemGroup?
        get() = block.group

    override fun register() {
        Registry.register(Registries.ITEM, key, this)

        // Register item in item group if specified
        ItemGroupEvents.modifyEntriesEvent(group?.registryKey ?: return).register { it.add(this) }
    }
}