package com.cynquil.amethyst.rpg.enchantment

import com.cynquil.amethyst.enchantment.AmEnchantment
import com.cynquil.amethyst.rpg.util.HasRarity
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot
import net.minecraft.util.Identifier

open class RpgEnchantment(
    key: Identifier,
    override val rarity: com.cynquil.amethyst.rpg.Rarity,
    target: EnchantmentTarget,
    slots: List<EquipmentSlot>,
) : AmEnchantment(key, rarity.enchantmentRarity, target, slots), HasRarity