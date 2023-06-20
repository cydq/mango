package com.cynquil.amethyst.module

import com.cynquil.amethyst.datagen.DataGenerator
import com.cynquil.amethyst.util.Registerable
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import kotlin.reflect.full.*

abstract class Container : Registerable, DataGenerator {
    override fun register() {
        if (this::class.hasAnnotation<Disabled>())
            return

        val tRegisterable = Registerable::class.createType()
        this.javaClass.kotlin.memberProperties
            .filter { it.returnType.isSubtypeOf(tRegisterable) }
            .filter { !it.hasAnnotation<Disabled>() }
            .mapNotNull { it.get(this) }
            .mapNotNull { it as? Registerable }
            .forEach(Registerable::register)
    }

    override fun generate(pack: FabricDataGenerator.Pack) {
        if (this::class.hasAnnotation<Disabled>())
            return

        val tDataGenerator = DataGenerator::class.createType()
        this.javaClass.kotlin.memberProperties
            .filter { it.returnType.isSubtypeOf(tDataGenerator) }
            .filter { !it.hasAnnotation<Disabled>() }
            .mapNotNull { it.get(this) }
            .mapNotNull { it as? DataGenerator }
            .forEach { it.generate(pack) }
    }
}