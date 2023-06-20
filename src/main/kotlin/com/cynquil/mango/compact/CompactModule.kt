package com.cynquil.mango.compact

import com.cynquil.amethyst.module.Container
import com.cynquil.amethyst.module.moduleOf
import com.cynquil.mango.compact.items.CompactOres

object CompactModule : Container() {
    val blocks = moduleOf(
        Compactor,
        Decompactor
    )

    val items = moduleOf(
        CompactOres
    )
}