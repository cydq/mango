package com.cynquil.mango.attributes

import com.cynquil.amethyst.id
import net.minecraft.screen.ScreenTexts
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier

enum class Attribute(
    val key: Identifier,
    icon: String,
    val style: Style,

    val base: Double,
) {
    Health      ("mango:health".id,          "❤", Formatting.RED,     20.0),
    Defence     ("mango:defence".id,         "❈", Formatting.GREEN,   .0),
    Strength    ("mango:strength".id,        "❁", Formatting.RED,     .0),
    Intelligence("mango:intelligence".id,    "✎", Formatting.AQUA,    .0),
    CritChance  ("mango:critical_chance".id, "☠", Formatting.BLUE,    30.0),
    CritDamage  ("mango:critical_damage".id, "☣", Formatting.BLUE,    .0),
    Ferocity    ("mango:ferocity".id,        "⫽", Formatting.RED,     .0),
    AttackSpeed ("mango:attack_speed".id,    "⚔", Formatting.YELLOW,  100.0),
    Speed       ("mango:speed".id,           "✦", Formatting.WHITE,   100.0),
    Vitality    ("mango:vitality".id,        "♨", Formatting.DARK_RED,0.0),
    MagicFind   ("mango:magic_find".id,      "✯", Formatting.AQUA,    0.0),
    Mana        ("mango:mana".id,            "✎", Formatting.AQUA,    100.0);

    val display = Text.translatable("attribute.${key.namespace}.${key.path}")
    val icon = Text.literal(icon).setStyle(style)

    val fullDisplay = Text.empty()
        .append(icon)
        .append(ScreenTexts.SPACE)
        .append(display)
        .setStyle(style)

    constructor(id: Identifier, icon: String, formatting: Formatting, base: Double) :
            this(id, icon, Style.EMPTY.withColor(formatting), base)
}