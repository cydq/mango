package com.cynquil.amethyst.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

interface DataGenerator {
    fun generate(pack: FabricDataGenerator.Pack)
}