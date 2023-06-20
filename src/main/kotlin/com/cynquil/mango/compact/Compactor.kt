package com.cynquil.mango.compact

import com.cynquil.amethyst.id
import com.cynquil.amethyst.rpg.Rarity
import com.cynquil.amethyst.rpg.block.RpgBlock
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object Compactor : RpgBlock(
    key = "mango:compactor".id,
    rarity = Rarity.Common,
    settings = FabricBlockSettings.create().hardness(3.5f)
) {
    @Deprecated("Deprecated in Java")
    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        val stack = player.getStackInHand(hand)
        if (stack.count < 64) return ActionResult.FAIL

        val into = CompressionChain.compressesInto[stack.item] ?: return ActionResult.FAIL
        val newStack = ItemStack(into)

        stack.count -= 64
        player.inventory.offerOrDrop(newStack)

        player.playSound(SoundEvents.BLOCK_COMPOSTER_FILL, 2.0f, 2.0f)
        return ActionResult.SUCCESS
    }
}