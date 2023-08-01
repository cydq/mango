package com.cynquil.amethyst.rpg.attributes

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
    Health      ("amethyst:health".id,          "❤", Formatting.RED,     20.0),
    Defence     ("amethyst:defence".id,         "❈", Formatting.GREEN,   .0),
    Strength    ("amethyst:strength".id,        "❁", Formatting.RED,     .0),
    Intelligence("amethyst:intelligence".id,    "✎", Formatting.AQUA,    .0),
    CritChance  ("amethyst:critical_chance".id, "☠", Formatting.BLUE,    30.0),
    CritDamage  ("amethyst:critical_damage".id, "☣", Formatting.BLUE,    .0),
    Ferocity    ("amethyst:ferocity".id,        "⫽", Formatting.RED,     .0),
    AttackSpeed ("amethyst:attack_speed".id,    "⚔", Formatting.YELLOW,  100.0),
    Speed       ("amethyst:speed".id,           "✦", Formatting.WHITE,   100.0),
    Vitality    ("amethyst:vitality".id,        "♨", Formatting.DARK_RED,0.0),
    MagicFind   ("amethyst:magic_find".id,      "✯", Formatting.AQUA,    0.0),
    Mana        ("amethyst:mana".id,            "✎", Formatting.AQUA,    100.0);

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