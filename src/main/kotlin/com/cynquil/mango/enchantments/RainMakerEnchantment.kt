package com.cynquil.mango.enchantments

import com.cynquil.amethyst.id
import com.cynquil.amethyst.rpg.enchantment.RpgEnchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot

object RainMakerEnchantment : RpgEnchantment(
    key = "mango:rainmaker".id,
    rarity = com.cynquil.amethyst.rpg.Rarity.Legendary,
    target = EnchantmentTarget.TRIDENT,
    slots = listOf(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND)
) {
    override fun getMaxLevel() = 1

    override fun isAvailableForEnchantedBookOffer() = false
    override fun isAvailableForRandomSelection() = false
}