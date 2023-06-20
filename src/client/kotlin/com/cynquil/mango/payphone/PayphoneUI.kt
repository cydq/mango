package com.cynquil.mango.payphone

import com.cynquil.amethyst.id
import com.cynquil.mango.payphone.networking.PayphonePacket
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription
import io.github.cottonmc.cotton.gui.widget.*
import io.github.cottonmc.cotton.gui.widget.data.InputResult
import io.github.cottonmc.cotton.gui.widget.data.Insets
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.sound.PositionedSoundInstance
import net.minecraft.screen.ScreenTexts
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier

class PayphoneUI(player: ClientPlayerEntity, functions: Set<PayphoneFunction>, names: List<String>) : LightweightGuiDescription() {
    private fun act(function: PayphoneFunction) =
        ClientPlayNetworking.send(PayphonePacket.Action, PacketByteBufs.create().apply {
            writeEnumConstant(function)
        })

    init {
        val root = WGridPanel().apply {
            setRootPanel(this)
            setSize(12 * 18, 11 * 18)

            insets = Insets.ROOT_PANEL
        }

        // Title
        root.add(WLabel(
            Text.empty()
                .append(ScreenTexts.SPACE)
                .append(player.displayName)
                .append(Text.literal("'s Payphone"))
        ).apply {
            verticalAlignment = VerticalAlignment.CENTER
        }, 0, 0, 12, 1)

        // Toolbar
        listOf(
            PayphoneFunction.Workbench,
            PayphoneFunction.Anvil,
            PayphoneFunction.Enchanting,
            PayphoneFunction.Smithing,
            PayphoneFunction.StoneCutting
        ).forEachIndexed { index, action ->
            root.add(IconButton(action.uiIcon, action in functions) {
                act(action)
            }, index, 1, 1, 1)
        }

        // Slots
        val slots = listOf(
            PayphoneFunction.EnderChest to "Ender Chest",
            PayphoneFunction.Slot1 to names[0],
            PayphoneFunction.Slot2 to names[1],
            PayphoneFunction.Slot3 to names[2],
            PayphoneFunction.Slot4 to names[3],
            PayphoneFunction.Slot5 to names[4],
        )

        root.add(WListPanel(slots, PayphoneUI::Action) { (action, name), panel ->
            val unlocked = action in functions
            val enabled = unlocked && name != "None" && name != "Unloaded"
            panel.sprite.setImage(action.uiIcon)

            panel.label.setText(
                if (!unlocked)
                    Text.literal("Locked").formatted(Formatting.RED)
                else if (name == "Unloaded")
                    Text.literal(name).formatted(Formatting.DARK_PURPLE)
                else if (!enabled)
                    Text.literal("Available").formatted(Formatting.DARK_GREEN)
                else
                    Text.literal(name)
            )

            panel.button.isEnabled = enabled
            panel.button.setOnClick { act(action) }
        }.apply {
            setListItemHeight(18)
        }, 0, 3, 12, 8)

        root.validate(this)
    }

    class IconButton(
        id: Identifier,
        private val enabled: Boolean,
        private val onClick: (() -> Unit)? = null
    ) : WSprite(id) {
        override fun onClick(x: Int, y: Int, button: Int): InputResult {
            super.onClick(x, y, button)

            if (enabled && isWithinBounds(x, y)) {
                MinecraftClient.getInstance().soundManager.play(
                    PositionedSoundInstance.master(
                        SoundEvents.UI_BUTTON_CLICK,
                        1.0f
                    )
                )
                onClick?.invoke()
                return InputResult.PROCESSED
            }

            return InputResult.IGNORED
        }
    }

    class Action : WGridPanel() {
        val sprite: WSprite
        val label: WLabel
        val button: WButton

        init {
            insets = Insets.NONE

            sprite = WSprite("mango:sprite".id)
            add(sprite, 0, 0, 1, 1)

            label = WLabel(Text.empty())
            label.verticalAlignment = VerticalAlignment.CENTER
            add(label, 2, 0, 7, 1)

            button = WButton(Text.literal("Open"))
            add(button, 9, 0, 2, 1)

            setSize(18 * 11, 18)
        }
    }
}