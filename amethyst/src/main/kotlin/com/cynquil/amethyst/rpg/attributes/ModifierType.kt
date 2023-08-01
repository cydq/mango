package com.cynquil.amethyst.rpg.attributes

import com.cynquil.amethyst.id
import net.minecraft.util.Identifier

enum class ModifierType(val key: Identifier) {
    Flat          ("mango:flat".id),
    Additive      ("mango:additive".id),
    Multiplicative("mango:multiplicative".id)
}