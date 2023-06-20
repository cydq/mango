package com.cynquil.amethyst.module

import com.cynquil.amethyst.datagen.DataGenerator
import com.cynquil.amethyst.util.Registerable
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

fun moduleOf(vararg entries: Registerable) = Module(*entries)

open class Module(private vararg val entries: Registerable): Registerable, DataGenerator {
    override fun register() =
        entries.forEach(Registerable::register)

    override fun generate(pack: FabricDataGenerator.Pack) =
        entries.mapNotNull { it as? DataGenerator }.forEach { it.generate(pack) }
}