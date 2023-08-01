package com.cynquil.amethyst.rpg.attributes

data class Modifier(
    val attribute: Attribute,
    val type: ModifierType,
    val value: Double
)