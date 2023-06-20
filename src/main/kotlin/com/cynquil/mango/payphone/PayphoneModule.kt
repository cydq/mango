package com.cynquil.mango.payphone

import com.cynquil.amethyst.module.Container
import com.cynquil.amethyst.module.moduleOf
import com.cynquil.mango.payphone.networking.PayphoneActionHandler
import com.cynquil.mango.payphone.networking.PayphoneCompactHandler
import com.cynquil.mango.payphone.networking.PayphoneQueryHandler

object PayphoneModule : Container() {
    val items = moduleOf(
        Payphone,
        PayphoneUpgrades
    )

    val networking = moduleOf(
        PayphoneActionHandler,
        PayphoneQueryHandler,
        PayphoneCompactHandler
    )
}