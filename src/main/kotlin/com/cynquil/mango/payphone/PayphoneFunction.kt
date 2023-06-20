package com.cynquil.mango.payphone

import com.cynquil.amethyst.id
import net.minecraft.util.Identifier

enum class PayphoneFunction(
    val id: Identifier,
    val value: Int,
    val uiIcon: Identifier = "mango:unknown".id
) {
    Workbench("mango:payphone_workbench".id, 1, "mango:textures/gui/payphone/workbench.png".id),
    Anvil("mango:payphone_anvil".id, 2, "mango:textures/gui/payphone/anvil.png".id),
    Enchanting("mango:payphone_enchanting".id, 4, "mango:textures/gui/payphone/enchantment_table.png".id),
    Smithing("mango:payphone_smithing".id, 8, "mango:textures/gui/payphone/smithing_table.png".id),
    StoneCutting("mango:payphone_stone_cutting".id, 4096, "mango:textures/gui/payphone/stone_cutter.png".id),

    Compact("mango:payphone_compact".id, 16),
    Decompact("mango:payphone_decompact".id, 32),

    EnderChest("mango:payphone_ender_chest".id, 64, "mango:textures/gui/payphone/ender_chest.png".id),
    Slot1("mango:payphone_slot_1".id, 128, "mango:textures/gui/payphone/community_chest.png".id),
    Slot2("mango:payphone_slot_2".id, 256, "mango:textures/gui/payphone/community_chest.png".id),
    Slot3("mango:payphone_slot_3".id, 512, "mango:textures/gui/payphone/community_chest.png".id),
    Slot4("mango:payphone_slot_4".id, 1024, "mango:textures/gui/payphone/community_chest.png".id),
    Slot5("mango:payphone_slot_5".id, 2048, "mango:textures/gui/payphone/community_chest.png".id);

    companion object {
        private val values = mapOf(*PayphoneFunction.values().map { it.value to it }.toTypedArray())

        fun fromValue(value: Int) =
            values[value]
    }
}