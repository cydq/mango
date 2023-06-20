package com.cynquil.mango.payphone.networking

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen

object PayphoneInventoryNetworking {
    fun init() {
        ScreenEvents.BEFORE_INIT.register { client, screen, _, _ ->
            ScreenMouseEvents
                .allowMouseClick(screen)
                .register a@ { sc, x, y, button ->
                    if (sc !is AbstractInventoryScreen<*>)
                        return@a true

                    if (!Screen.hasControlDown())
                        return@a true

                    val slot = sc.getSlotAt(x, y) ?: return@a true

                    if (slot.inventory != client.player?.inventory)
                        return@a true

                    if (button == 0) {
                        ClientPlayNetworking.send(PayphonePacket.Compact, PacketByteBufs.create().apply {
                            writeInt(slot.index)
                        })

                        return@a false
                    }

                    if (button == 1) {
                        ClientPlayNetworking.send(PayphonePacket.Decompact, PacketByteBufs.create().apply {
                            writeInt(slot.index)
                        })

                        return@a false
                    }

                    true
                }
        }
    }
}