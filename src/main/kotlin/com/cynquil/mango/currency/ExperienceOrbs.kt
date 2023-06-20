package com.cynquil.mango.currency

import com.cynquil.amethyst.id
import com.cynquil.amethyst.module.Container
import com.cynquil.amethyst.rpg.Rarity
import com.cynquil.amethyst.rpg.item.RpgItem
import com.cynquil.mango.Mango
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

object ExperienceOrbs : Container() {
    val ChaosOrb =   Orb("mango:chaos_orb".id,   Rarity.Rare,      value = 5000)
    val ExaltedOrb = Orb("mango:exalted_orb".id, Rarity.Epic,      value = 5000 * 64, usable = false)
    val DivineOrb =  Orb("mango:divine_orb".id,  Rarity.Legendary, value = 5000 * 64 * 64, usable = false)

    class Orb(
        id: Identifier,
        rarity: Rarity,
        private val value: Int,
        private val usable: Boolean = true,
        settings: FabricItemSettings = FabricItemSettings()
    ) : RpgItem(
        id,
        rarity,
        listOf("Would you like a little experience?"),
        settings = settings
    ) {

        override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
            if (!usable)
                return TypedActionResult.pass(user.getStackInHand(hand))

            user.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f)

            user.addExperience(value)
            user.getStackInHand(hand).count--

            return TypedActionResult.success(user.getStackInHand(hand))
        }
    }
}