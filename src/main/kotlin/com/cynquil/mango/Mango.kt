package com.cynquil.mango

import com.cynquil.amethyst.group.AmItemGroup
import com.cynquil.amethyst.id
import com.cynquil.amethyst.util.Keyed
import com.cynquil.mango.currency.ExperienceOrbs
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.text.Text
import org.slf4j.LoggerFactory

object Mango : ModInitializer {
	val logger = LoggerFactory.getLogger("mango")

	val group = AmItemGroup(
		"mango:items".id,
		ItemStack(ExperienceOrbs.ChaosOrb),
		Text.literal("Mango")
	)

	override fun onInitialize() {
		group.register()
		Content.register()

		ItemGroupEvents.modifyEntriesEvent(group.registryKey).register {
			Registries.ITEM.forEach {  item ->
				if (item is Keyed)
					it.add(item)
			}
		}
	}
}