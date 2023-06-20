package com.cynquil.mango.attributes

import com.cynquil.amethyst.id
import net.minecraft.util.Identifier

enum class ModifierType(
    val key: Identifier,
    val base: Double,
    val reduce: (acc: Double, value: Double) -> Double
) {
    Flat          ("mango:flat".id,           0.0, Double::plus),
    Additive      ("mango:additive".id,       0.0, Double::plus),
    Multiplicative("mango:multiplicative".id, 1.0, Double::times),
}