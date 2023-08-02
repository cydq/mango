package com.cynquil.mango.automation.loader

import com.cynquil.amethyst.id
import com.cynquil.amethyst.util.Registerable
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.server.command.ForceLoadCommand
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.World

class ChunkLoaderBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(EntityType, pos, state) {
    var duration: Long = 0
        set(value) {
            field = value
            markDirty()
        }

    override fun writeNbt(nbt: NbtCompound) {
        nbt.putLong("duration", duration)

        super.writeNbt(nbt)
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)

        duration = nbt.getLong("duration")
    }

    companion object : Registerable {
        lateinit var EntityType: BlockEntityType<ChunkLoaderBlockEntity>

        override fun register() {
            EntityType = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                "mango:chunk_loader".id,
                FabricBlockEntityTypeBuilder.create(::ChunkLoaderBlockEntity, ChunkLoaderBlock).build()
            )
        }

        fun tick(world: World, pos: BlockPos, state: BlockState, be: ChunkLoaderBlockEntity) {
            if (world !is ServerWorld)
                return

            val chunk = world.getChunk(pos)

            if (be.duration > 0) {
                if (chunk.pos.toLong() !in world.forcedChunks)
                    world.setChunkForced(chunk.pos.x, chunk.pos.z, true)

                be.duration--
                return
            }

            if (chunk.pos.toLong() in world.forcedChunks)
                world.setChunkForced(chunk.pos.x, chunk.pos.z, false)
        }
    }
}