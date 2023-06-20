package com.cynquil.mango.payphone

import io.github.cottonmc.cotton.gui.client.CottonClientScreen
import net.minecraft.client.network.ClientPlayerEntity

class PayphoneScreen(player: ClientPlayerEntity, actions: Set<PayphoneFunction>, names: List<String>) : CottonClientScreen(
    PayphoneUI(player, actions, names)
)