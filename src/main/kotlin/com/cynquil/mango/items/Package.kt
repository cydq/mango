package com.cynquil.mango.items

import com.cynquil.amethyst.datagen.DataProvider
import com.cynquil.amethyst.datagen.itemModel
import com.cynquil.amethyst.id
import com.cynquil.amethyst.rpg.Rarity
import com.cynquil.amethyst.rpg.item.RpgItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.item.TooltipContext
import net.minecraft.data.client.Model
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.FireworkRocketEntity
import net.minecraft.item.FireworkRocketItem
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenTexts
import net.minecraft.stat.Stats
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import java.util.*

object Package : RpgItem(
    key = "mango:package".id,
    rarity = Rarity.Legendary,
    lore = listOf("Zoom!"),
    settings = FabricItemSettings().maxCount(1)
), DataProvider {
    private const val MAX_DURATION = 4

    override fun use(world: World, user: PlayerEntity, hand: Hand?): TypedActionResult<ItemStack?>? {
        val stack = user.getStackInHand(hand)

        if (user.isSneaking) {
            val fd = bumpFlight(stack)

            if (!world.isClient)
                user.sendMessage(
                    Text.literal("[Package] Selected Flight Duration $fd!")
                        .formatted(Formatting.DARK_PURPLE)
                )

            return TypedActionResult.success(stack)
        }

        if (!user.isFallFlying)
            return TypedActionResult.pass(stack)

        if (!world.isClient) {
            world.spawnEntity(FireworkRocketEntity(world, stack, user))
            user.incrementStat(Stats.USED.getOrCreateStat(this))
        }

        return TypedActionResult.success(stack, world.isClient)
    }

    override fun hasGlint(stack: ItemStack?) =
        true

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        val nbt = stack.getOrCreateSubNbt(FireworkRocketItem.FIREWORKS_KEY)

        tooltip.add(
            Text.translatable("item.minecraft.firework_rocket.flight")
                .append(ScreenTexts.SPACE)
                .append(nbt.getByte(FireworkRocketItem.FLIGHT_KEY).toString())
                .formatted(Formatting.GRAY)
        )
    }

    private fun bumpFlight(stack: ItemStack): Int {
        val current = stack.getSubNbt(FireworkRocketItem.FIREWORKS_KEY)?.getByte(FireworkRocketItem.FLIGHT_KEY) ?: 0
        val next = (current % MAX_DURATION) + 1

        FireworkRocketItem.setFlight(stack, next.toByte())
        return next
    }
    override fun getDefaultStack() = ItemStack(this).also {
        FireworkRocketItem.setFlight(it, 1.toByte())
    }

    override fun generate(provide: DataProvider.Provide) {
        provide(
            itemModel {
                it.register(this@Package, Model(Optional.of("minecraft:item/firework_rocket".id), Optional.empty()))
            }
        )
    }
}