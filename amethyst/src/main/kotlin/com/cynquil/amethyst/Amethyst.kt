package com.cynquil.amethyst

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object Amethyst : ModInitializer {
	val logger = LoggerFactory.getLogger("amethyst")

	override fun onInitialize() {
		logger.info("Amethyst initialized!")
	}
}