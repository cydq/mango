package com.cynquil.mango.payphone

import com.cynquil.amethyst.id
import com.cynquil.amethyst.rpg.Rarity
import com.cynquil.amethyst.rpg.item.RpgTrinket
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Blocks
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.screen.*
import net.minecraft.text.Text
import net.minecraft.util.*
import net.minecraft.world.World

object Payphone : RpgTrinket(
    key = "mango:payphone".id,
    rarity = Rarity.Special,
    lore = listOf("I'm at a payphone", "trying to call home"),
    settings = FabricItemSettings().maxCount(1)
) {
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        val n = PayphoneUtils.totalUpgradeCount(stack)
        tooltip.add(Text.empty().append(Text.literal("$n/13 Upgrades").formatted(Formatting.BLUE)))
    }

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val item = user.getStackInHand(hand)

        if (item?.item != this)
            return TypedActionResult.pass(item)

        if (user.isSneaking)
            return TypedActionResult.pass(item)

        PayphoneUtils.setSelectedSlot(item)

        if (!world.isClient)
            user.sendMessage(
                Text.literal("[Payphone] Selected Slot ${PayphoneUtils.getSelectedSlot(item) + 1}!")
                    .formatted(Formatting.DARK_PURPLE)
            )

        return TypedActionResult.success(item)
    }

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val user = context.player ?: return ActionResult.PASS
        val item = user.getStackInHand(context.hand)

        if (item?.item != this)
            return ActionResult.PASS

        if (!user.isSneaking)
            return ActionResult.PASS

        val slot = PayphoneUtils.getSelectedSlot(item)

        val state = context.world.getBlockState(context.blockPos)

        if (state.block != Blocks.BARREL)
            return ActionResult.PASS

        PayphoneUtils.setSlot(context.world, context.blockPos, item, slot)

        if (!context.world.isClient)
            user.sendMessage(
                Text.literal("[Payphone] Linked Slot ${slot + 1} to (${context.blockPos.x}, ${context.blockPos.y}, ${context.blockPos.z})!")
                    .formatted(Formatting.DARK_PURPLE)
            )

        return ActionResult.SUCCESS
    }
}