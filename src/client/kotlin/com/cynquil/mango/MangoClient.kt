package com.cynquil.mango

import com.cynquil.amethyst.id
import com.cynquil.mango.payphone.Payphone
import com.cynquil.mango.payphone.PayphoneUtils
import com.cynquil.mango.payphone.networking.PayphoneInventoryNetworking
import com.cynquil.mango.payphone.networking.PayphoneMenuNetworking
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.item.ModelPredicateProviderRegistry

object MangoClient : ClientModInitializer {
	override fun onInitializeClient() {
		ModelPredicateProviderRegistry.register(Payphone, "payphone_upgrades".id) { stack, _, _, _ ->
			PayphoneUtils.totalUpgradeCount(stack).toFloat() / 100f + 0.001f
		}

		PayphoneMenuNetworking.init()
		PayphoneInventoryNetworking.init()
	}
}