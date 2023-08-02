package com.cynquil.mango.enchantments

import com.cynquil.amethyst.id
import com.cynquil.amethyst.rpg.enchantment.RpgEnchantment
import net.minecraft.enchantment.EnchantmentTarget

object RecoverEnchantment : RpgEnchantment(
    key = "mango:recover".id,
    rarity = com.cynquil.amethyst.rpg.Rarity.Epic,
    target = EnchantmentTarget.BREAKABLE,
    slots = listOf()
) {
    override fun getMaxLevel() = 1

    override fun isAvailableForEnchantedBookOffer() = false
    override fun isAvailableForRandomSelection() = false
}