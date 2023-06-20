package com.cynquil.mango.payphone.networking

import com.cynquil.amethyst.util.Registerable
import com.cynquil.mango.compact.CompressionChain
import com.cynquil.mango.payphone.PayphoneFunction
import com.cynquil.mango.payphone.PayphoneUtils
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.item.ItemStack

object PayphoneCompactHandler : Registerable {
    override fun register() {
        ServerPlayNetworking.registerGlobalReceiver(PayphonePacket.Compact) { server, player, _, buf, _ ->
            val slot = buf.readInt()

            server.execute {
                val payphone = PayphoneUtils.getPayphone(player) ?: return@execute

                if (!PayphoneUtils.hasUpgrade(payphone, PayphoneFunction.Compact))
                    return@execute

                val stack = player.inventory.getStack(slot)
                if (stack.count < 64) return@execute

                val into = CompressionChain.compressesInto[stack.item] ?: return@execute
                val newStack = ItemStack(into)

                stack.count -= 64
                player.inventory.offerOrDrop(newStack)
            }
        }

        ServerPlayNetworking.registerGlobalReceiver(PayphonePacket.Decompact) { server, player, _, buf, _ ->
            val slot = buf.readInt()

            server.execute {
                val payphone = PayphoneUtils.getPayphone(player) ?: return@execute

                if (!PayphoneUtils.hasUpgrade(payphone, PayphoneFunction.Decompact))
                    return@execute

                val stack = player.inventory.getStack(slot)
                if (stack.count < 1) return@execute

                val into = CompressionChain.expandsInto[stack.item] ?: return@execute
                val newStack = ItemStack(into, 64)

                stack.count -= 1
                player.inventory.offerOrDrop(newStack)
            }
        }
    }
}