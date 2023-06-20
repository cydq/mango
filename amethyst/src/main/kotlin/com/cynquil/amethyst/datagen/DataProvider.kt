package com.cynquil.amethyst.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

interface DataProvider : DataGenerator {
    fun generate(provide: Provide)

    override fun generate(pack: FabricDataGenerator.Pack) =
        Provide().apply(::generate).providers.forEach { pack.addProvider(it) }

    class Provide internal constructor() {
        internal val providers = mutableListOf<FabricDataGenerator.Pack.Factory<*>>()

        operator fun invoke(provider: FabricDataGenerator.Pack.Factory<*>) {
            providers.add(provider)
        }

        operator fun invoke(provider: () -> FabricDataGenerator.Pack.Factory<*>) {
            providers.add(provider())
        }
    }
}