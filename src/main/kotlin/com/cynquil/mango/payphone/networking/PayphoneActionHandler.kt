package com.cynquil.mango.payphone.networking

import com.cynquil.amethyst.util.Registerable
import com.cynquil.mango.payphone.Payphone
import com.cynquil.mango.payphone.PayphoneFunction
import com.cynquil.mango.payphone.PayphoneUtils
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.block.StonecutterBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.*
import net.minecraft.text.Text

object PayphoneActionHandler : Registerable {
    override fun register() {
        ServerPlayNetworking.registerGlobalReceiver(PayphonePacket.Action) { server, player, _, buf, response ->
            val action = buf.readEnumConstant(PayphoneFunction::class.java)

            server.execute {
                if (!PayphoneUtils.hasUpgrade(
                        PayphoneUtils.getPayphone(player) ?: return@execute,
                        PayphoneFunction.fromValue(action.value) ?: return@execute
                    )
                ) return@execute

                val context = { inventory: PlayerInventory ->
                    ScreenHandlerContext.create(inventory.player.world, inventory.player.blockPos)
                }

                when (action) {
                    PayphoneFunction.Workbench -> player.openHandledScreen(SimpleNamedScreenHandlerFactory({ syncId, inventory, _ ->
                        object : CraftingScreenHandler(syncId, inventory, context(inventory)) {
                            override fun canUse(player: PlayerEntity?) = true
                        }
                    }, Text.translatable("container.crafting")))

                    PayphoneFunction.Anvil -> player.openHandledScreen(SimpleNamedScreenHandlerFactory({ syncId, inventory, _ ->
                        object : AnvilScreenHandler(syncId, inventory, context(inventory)) {
                            override fun canUse(player: PlayerEntity?) = true
                        }
                    }, Text.translatable("container.repair")))

                    PayphoneFunction.Smithing -> player.openHandledScreen(SimpleNamedScreenHandlerFactory({ syncId, inventory, _ ->
                        object : SmithingScreenHandler(syncId, inventory, context(inventory)) {
                            override fun canUse(player: PlayerEntity?) = true
                        }
                    }, Text.translatable("container.upgrade")))

                    PayphoneFunction.Enchanting -> player.openHandledScreen(SimpleNamedScreenHandlerFactory({ syncId, inventory, _ ->
                        object : EnchantmentScreenHandler(syncId, inventory, context(inventory)) {
                            override fun canUse(player: PlayerEntity?) = true
                        }
                    }, Text.literal("Enchantment")))

                    PayphoneFunction.EnderChest -> player.openHandledScreen(SimpleNamedScreenHandlerFactory({ syncId: Int, inventory: PlayerInventory, player: PlayerEntity ->
                        GenericContainerScreenHandler.createGeneric9x3(syncId, inventory, player.enderChestInventory)
                    }, Text.translatable("container.enderchest")))

                    PayphoneFunction.StoneCutting -> player.openHandledScreen(SimpleNamedScreenHandlerFactory({ syncId, inventory, _ ->
                        object : StonecutterScreenHandler(syncId, inventory, context(inventory)) {
                            override fun canUse(player: PlayerEntity?) = true
                        }
                    }, Text.translatable("container.stonecutter")))

                    PayphoneFunction.Slot1 -> PayphoneUtils.openSlot(player, 0)
                    PayphoneFunction.Slot2 -> PayphoneUtils.openSlot(player, 1)
                    PayphoneFunction.Slot3 -> PayphoneUtils.openSlot(player, 2)
                    PayphoneFunction.Slot4 -> PayphoneUtils.openSlot(player, 3)
                    PayphoneFunction.Slot5 -> PayphoneUtils.openSlot(player, 4)

                    else -> response.sendPacket(PayphonePacket.Action, PacketByteBufs.empty())
                }
            }
        }
    }
}