package com.cynquil.mango.mixin;

import com.cynquil.mango.enchantments.RainMakerEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Objects;

@Mixin(TridentItem.class)
public abstract class MixinRainmakerEnchantment {
    @Redirect(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isTouchingWaterOrRain()Z"),
            method = "use"
    )
    private boolean isTouchingWaterOrRainUse(PlayerEntity player) {
        return isTouchingWaterOrRain(player);
    }

    @Redirect(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isTouchingWaterOrRain()Z"),
            method = "onStoppedUsing"
    )
    private boolean isTouchingWaterOrRainOnStopUsing(PlayerEntity player) {
        return isTouchingWaterOrRain(player);
    }

    @Unique
    private boolean isTouchingWaterOrRain(PlayerEntity player) {
//        ItemStack stack = player.getStackInHand(player.getActiveHand());
//        NbtList list = stack.getEnchantments();
//
//        for (int i = 0; i < list.size(); i++) {
//            NbtCompound compound = list.getCompound(i);
//            Enchantment enchantment = Registries.ENCHANTMENT.get(EnchantmentHelper.getIdFromNbt(compound));
//
//            if (enchantment == null)
//                continue;
//
//            if (Objects.equals(enchantment, RainMakerEnchantment.INSTANCE))
//                return true;
//        }

        return player.isTouchingWaterOrRain();
    }
}