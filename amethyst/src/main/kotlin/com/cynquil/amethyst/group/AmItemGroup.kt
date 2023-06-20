package com.cynquil.amethyst.group

import com.cynquil.amethyst.util.Keyed
import com.cynquil.amethyst.util.Registerable
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class AmItemGroup(
    override val key: Identifier,
    val group: ItemGroup
) : Keyed, Registerable{
    val registryKey = RegistryKey.of(RegistryKeys.ITEM_GROUP, key)

    constructor(key: Identifier, icon: ItemStack, displayName: Text) : this(
        key,
        FabricItemGroup.builder()
            .icon { icon }
            .displayName(displayName)
            .build()
    )

    override fun register() {
        Registry.register(Registries.ITEM_GROUP, key, group)
    }
}