package com.cynquil.amethyst.enchantment

import com.cynquil.amethyst.util.Registerable
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

open class AmEnchantment(
    val key: Identifier,
    rarity: Rarity,
    target: EnchantmentTarget,
    slots: List<EquipmentSlot>,
) : Enchantment(rarity, target, slots.toTypedArray()), Registerable {
    override fun register() {
        Registry.register(Registries.ENCHANTMENT, key, this)
    }
}