package com.cynquil.mango.automation.loader

import com.cynquil.amethyst.id
import com.cynquil.amethyst.rpg.Rarity
import com.cynquil.amethyst.rpg.util.HasRarity
import com.cynquil.amethyst.util.Registerable
import com.cynquil.mango.items.BlazingNetherStar
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.screen.ScreenTexts
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.text.Text
import net.minecraft.util.*
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object ChunkLoaderBlock : BlockWithEntity(
    FabricBlockSettings
        .create()
        .hardness(4f)
        .requiresTool()
        .pistonBehavior(PistonBehavior.BLOCK)
), HasRarity, Registerable, BlockEntityProvider {
    override val rarity = Rarity.Rare

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        if (world.isClient)
            return ActionResult.PASS

        val entity = world.getBlockEntity(pos) as? ChunkLoaderBlockEntity ?: return ActionResult.PASS

        val item = player.getStackInHand(hand)
        val count = item.count

        if (item.item === BlazingNetherStar) {
            entity.duration += count * 72_000
            item.count = 0

            player.sendMessage(Text.literal("Successfully added $count fuel!").formatted(Formatting.GREEN))
            return ActionResult.SUCCESS
        }

        val enabled = entity.duration > 0

        player.sendMessage(
            Text.empty()
                .append(Text.literal("Chunk Loader:").formatted(Formatting.DARK_PURPLE))
                .append(ScreenTexts.space())
                .append(
                    if (enabled)
                        Text.empty()
                            .append(Text.literal("Active").formatted(Formatting.GREEN))
                            .append(ScreenTexts.space())
                            .append(
                                Text.literal(
                                    "(${(entity.duration / 20L).toDuration(DurationUnit.SECONDS)})"
                                ).formatted(Formatting.GRAY)
                            )
                    else
                        Text.literal("Inactive").formatted(Formatting.RED)
                ),
            true
        )

        return ActionResult.SUCCESS
    }

    override fun onBroken(world: WorldAccess, pos: BlockPos, state: BlockState) {
        if (world !is ServerWorld)
            return

        val chunk = world.getChunk(pos)
        world.setChunkForced(chunk.pos.x, chunk.pos.z, false)
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState) =
        ChunkLoaderBlockEntity(pos, state)

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>) =
        checkType(type, ChunkLoaderBlockEntity.EntityType, ChunkLoaderBlockEntity::tick)

    override fun register() {
        Registry.register(Registries.BLOCK, "mango:chunk_loader".id, this)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(HorizontalFacingBlock.FACING)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? =
        super
            .getPlacementState(ctx)
            ?.with(HorizontalFacingBlock.FACING, ctx.horizontalPlayerFacing.opposite)

    override fun getRenderType(state: BlockState) =
        BlockRenderType.MODEL

    override fun rotate(state: BlockState, rotation: BlockRotation) =
        state.with(HorizontalFacingBlock.FACING, rotation.rotate(state.get(HorizontalFacingBlock.FACING))) as BlockState

    override fun mirror(state: BlockState, mirror: BlockMirror) =
        state.rotate(mirror.getRotation(state.get(HorizontalFacingBlock.FACING)))
}