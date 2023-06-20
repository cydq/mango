package com.cynquil.amethyst.rpg.util

interface HasLore : MaybeHasLore {
    override val lore: List<String>
}