package com.cynquil.mango.payphone

import dev.emi.trinkets.api.TrinketsApi
import net.minecraft.block.Blocks
import net.minecraft.block.entity.BarrelBlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.screen.SimpleNamedScreenHandlerFactory
import net.minecraft.server.MinecraftServer
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import kotlin.jvm.optionals.getOrNull

object PayphoneUtils {
    fun getPayphone(player: PlayerEntity) =
        TrinketsApi
            .getTrinketComponent(player)
            .getOrNull()
            ?.getEquipped(Payphone)
            ?.firstOrNull { it.left.inventory.slotType.name == "payphone" }
            ?.right

    fun getSlot(server: MinecraftServer, item: ItemStack, slot: Int): Slot? {
        val worldKey = item.getOrCreateSubNbt("payphone_slots").getString("slot_${slot}_world")
        val posKey = item.getOrCreateSubNbt("payphone_slots").getIntArray("slot_${slot}_pos")

        if (worldKey == "" || posKey.size < 3) return null

        val world = server.getWorld(RegistryKey.of(RegistryKeys.WORLD, Identifier(worldKey))) ?: return null
        val x = posKey[0]
        val y = posKey[1]
        val z = posKey[2]

        return Slot(world, BlockPos(x, y, z))
    }

    fun setSlot(world: World, pos: BlockPos, item: ItemStack, slot: Int) {
        val nbt = item.getOrCreateSubNbt("payphone_slots")
        val worldKey = world.registryKey.value.toString()

        nbt.putString("slot_${slot}_world", worldKey)
        nbt.putIntArray("slot_${slot}_pos", listOf(pos.x, pos.y, pos.z))

        item.setSubNbt("payphone_slots", nbt)
    }

    fun resetSlot(item: ItemStack, slot: Int) {
        val nbt = item.getOrCreateSubNbt("payphone_slots")

        nbt.remove("slot_${slot}_world")
        nbt.remove("slot_${slot}_pos")

        item.setSubNbt("payphone_slots", nbt)
    }

    fun getSelectedSlot(item: ItemStack) =
        item.nbt?.getInt("payphone_selected_slot") ?: 0

    fun setSelectedSlot(item: ItemStack, slot: Int? = null) {
        val nbt = item.orCreateNbt
        nbt.putInt("payphone_selected_slot", slot ?: ((getSelectedSlot(item) + 1) % 5))
        item.nbt = nbt
    }

    fun hasUpgrade(item: ItemStack, function: PayphoneFunction): Boolean =
        item.getOrCreateSubNbt("payphone_functions").getByte(function.id.toTranslationKey()) > 0

    fun totalUpgradeCount(item: ItemStack): Int =
        item.getOrCreateSubNbt("payphone_functions").getInt("count")

    fun addFunction(item: ItemStack, function: PayphoneFunction) {
        if (hasUpgrade(item, function)) return

        val nbt = item.getOrCreateSubNbt("payphone_functions")
        nbt.putByte(function.id.toTranslationKey(), 1)
        nbt.putInt("count", nbt.getInt("count") + 1)

        item.setSubNbt("payphone_functions", nbt)
    }

    fun isLoaded(world: World, pos: BlockPos) =
        world.chunkManager.isChunkLoaded(pos.x / 16, pos.z / 16)

    fun openSlot(player: PlayerEntity, s: Int) {
        val payphone = getPayphone(player) ?: return
        val slot = getSlot(player.server ?: return, payphone, s) ?: return

        if (!isLoaded(slot.world, slot.pos))
            return

        if (slot.block?.block != Blocks.BARREL)
            return

        val barrel = slot.barrel ?: return

        player.openHandledScreen(
            SimpleNamedScreenHandlerFactory(
            barrel::createMenu,
            barrel.displayName.copyContentOnly() ?: Text.translatable("container.chest"))
        )
    }

    data class Slot(
        val world: World,
        val pos: BlockPos
    ) {
        val block get() = world.getBlockState(pos)
        val blockEntity get() = world.getBlockEntity(pos)
        val barrel get() = blockEntity as? BarrelBlockEntity
    }
}