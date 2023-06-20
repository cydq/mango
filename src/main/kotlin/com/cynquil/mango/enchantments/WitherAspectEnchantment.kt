package com.cynquil.mango.enchantments

import com.cynquil.amethyst.id
import com.cynquil.amethyst.rpg.enchantment.RpgEnchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import kotlin.math.min

object WitherAspectEnchantment : RpgEnchantment(
    key = "mango:wither_aspect".id,
    rarity = com.cynquil.amethyst.rpg.Rarity.Epic,
    target = EnchantmentTarget.WEAPON,
    slots = listOf(EquipmentSlot.MAINHAND)
) {
    override fun getMaxLevel() = 5

    override fun onTargetDamaged(user: LivingEntity, target: Entity, level: Int) {
        if (target is LivingEntity)
            target.addStatusEffect(StatusEffectInstance(
                StatusEffects.WITHER,
                20 * (2 * level + 1),
                min(level - 1, 2))
            )
    }

    override fun isAvailableForEnchantedBookOffer() = false
    override fun isAvailableForRandomSelection() = false
}