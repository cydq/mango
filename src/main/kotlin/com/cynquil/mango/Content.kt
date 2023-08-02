package com.cynquil.mango

import com.cynquil.amethyst.module.Module
import com.cynquil.mango.automation.AutomationModule
import com.cynquil.mango.compact.CompactModule
import com.cynquil.mango.currency.ExperienceOrbs
import com.cynquil.mango.currency.SapphireItems
import com.cynquil.mango.enchantments.RainMakerEnchantment
import com.cynquil.mango.enchantments.RecoverEnchantment
import com.cynquil.mango.enchantments.WitherAspectEnchantment
import com.cynquil.mango.items.AugmentedMechanicalCore
import com.cynquil.mango.items.BedrockBreaker
import com.cynquil.mango.items.BlazingNetherStar
import com.cynquil.mango.items.Package
import com.cynquil.mango.payphone.PayphoneModule
import com.cynquil.mango.transportation.AsphaltBlock

object Content : Module(
    CompactModule,
    PayphoneModule,
    AutomationModule,

    // Items,
    BedrockBreaker,
    Package,

    // Experience Items
    ExperienceOrbs,
    SapphireItems,

    RainMakerEnchantment,
    WitherAspectEnchantment,
    RecoverEnchantment,

    AsphaltBlock,
    AugmentedMechanicalCore,
    BlazingNetherStar
)