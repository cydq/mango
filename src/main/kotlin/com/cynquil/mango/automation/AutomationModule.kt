package com.cynquil.mango.automation

import com.cynquil.amethyst.module.Container
import com.cynquil.amethyst.module.Module
import com.cynquil.mango.automation.loader.ChunkLoaderBlock
import com.cynquil.mango.automation.loader.ChunkLoaderBlockEntity
import com.cynquil.mango.automation.loader.ChunkLoaderItem

object AutomationModule : Container() {
    val chunkLoader = Module(
        ChunkLoaderBlock,
        ChunkLoaderBlockEntity,
        ChunkLoaderItem
    )
}