package com.cynquil.mango.payphone.networking

import com.cynquil.amethyst.util.Registerable
import com.cynquil.mango.payphone.Payphone
import com.cynquil.mango.payphone.PayphoneFunction
import com.cynquil.mango.payphone.PayphoneUtils
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import java.util.*

object PayphoneQueryHandler : Registerable {
    override fun register() {
        ServerPlayNetworking.registerGlobalReceiver(PayphonePacket.Query) { server, player, _, _, response ->
            server.execute {
                val payphone = PayphoneUtils.getPayphone(player) ?: return@execute

                val set = EnumSet.copyOf(
                    PayphoneFunction
                        .values()
                        .filter { PayphoneUtils.hasUpgrade(payphone, it) }
                )

                if (set.isEmpty())
                    return@execute

                response.sendPacket(PayphonePacket.Query, PacketByteBufs.create().apply {
                    writeEnumSet(set, PayphoneFunction::class.java)

                    for (slot in 0 until 5) {
                        val block = PayphoneUtils.getSlot(server, payphone, slot)

                        if (block == null) {
                            writeString("Available")
                            continue
                        }

                        if (!PayphoneUtils.isLoaded(block.world, block.pos)) {
                            writeString("Unloaded")
                            continue
                        }

                        val name = block.barrel
                            ?.displayName
                            ?.string

                        if (name == null) {
                            PayphoneUtils.resetSlot(payphone, slot)
                            writeString("Available")
                            continue
                        }

                        writeString(name)
                    }
                })
            }
        }
    }
}