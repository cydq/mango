package com.cynquil.amethyst.item

import com.cynquil.amethyst.group.AmItemGroup
import com.cynquil.amethyst.util.Keyed
import com.cynquil.amethyst.util.Registerable
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

open class AmItem(
    override val key: Identifier,

    val group: AmItemGroup? = null,
    settings: Settings = FabricItemSettings(),
) : Item(settings), Keyed, Registerable {
    override fun register() {
        Registry.register(Registries.ITEM, key, this)

        // Register item in item group if specified
        ItemGroupEvents.modifyEntriesEvent(group?.registryKey ?: return).register { it.add(this) }
    }
}