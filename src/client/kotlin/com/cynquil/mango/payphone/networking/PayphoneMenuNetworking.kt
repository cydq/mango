package com.cynquil.mango.payphone.networking

import com.cynquil.mango.payphone.PayphoneFunction
import com.cynquil.mango.payphone.PayphoneScreen
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW

object PayphoneMenuNetworking {
    lateinit var OpenPayphone: KeyBinding
    fun init() {
        OpenPayphone = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "key.mango.open_payphone",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.mango.default"
            )
        )

        ClientTickEvents.END_CLIENT_TICK.register {
            while (OpenPayphone.wasPressed())
                ClientPlayNetworking.send(PayphonePacket.Query, PacketByteBufs.empty())
        }

        ClientPlayNetworking.registerGlobalReceiver(PayphonePacket.Query) { client, _, buf, _ ->
            val set = buf.readEnumSet(PayphoneFunction::class.java)
            val names = listOf(buf.readString(), buf.readString(), buf.readString(), buf.readString(), buf.readString())

            client.execute {
                MinecraftClient
                    .getInstance()
                    .setScreen(PayphoneScreen(client.player ?: return@execute, set.toSet(), names))
            }
        }

        ClientPlayNetworking.registerGlobalReceiver(PayphonePacket.Action) { client, _, _, _ ->
            client.execute { MinecraftClient.getInstance().setScreen(null) }
        }
    }
}