package com.cynquil.mango.items

import com.cynquil.amethyst.id
import com.cynquil.amethyst.rpg.Rarity
import com.cynquil.amethyst.rpg.item.RpgItem
import com.cynquil.mango.Mango
import net.minecraft.block.Blocks
import net.minecraft.entity.EntityType
import net.minecraft.entity.LightningEntity
import net.minecraft.item.ItemUsageContext
import net.minecraft.util.ActionResult

object BedrockBreaker : RpgItem(
    key = "mango:bedrock_breaker".id,
    rarity = Rarity.Epic,
    group = Mango.group,
    lore = listOf(
        "If you break a bedrock,",
        "does the bedrock crash?"
    )
) {
    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val pos = context.blockPos
        val world = context.world
        val block = world.getBlockState(pos).block

        if (block != Blocks.BEDROCK)
            return ActionResult.FAIL

        context.stack.count -= 1

        world.setBlockState(pos, Blocks.OBSIDIAN.defaultState)
        world.spawnEntity(LightningEntity(EntityType.LIGHTNING_BOLT, world).apply {
            setPosition(pos.toCenterPos())
            setCosmetic(true)
        })

        return ActionResult.SUCCESS
    }
}