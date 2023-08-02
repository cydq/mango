package com.cynquil.mango.mixin;

import com.cynquil.mango.enchantments.RecoverEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class MixinRecoverEnchantment {
    @Shadow public abstract NbtList getEnchantments();

    @Shadow public abstract void setDamage(int damage);

    @Shadow public abstract int getDamage();

    @Shadow public abstract int getMaxDamage();

    @Inject(
        at = @At(value = "RETURN", ordinal = 2),
        method = "damage(ILnet/minecraft/util/math/random/Random;Lnet/minecraft/server/network/ServerPlayerEntity;)Z",
        cancellable = true
    )
    private void damage(int amount, Random random, ServerPlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (amount < 1 || amount > 6|| player == null || !qualifies())
            return;

        if (player.experienceLevel < 1 && player.experienceProgress * (float)player.getNextLevelExperience() < amount)
            return;

        player.addExperience(-amount);

        var maxDamage = getMaxDamage();
        var damage = MathHelper.clamp(getDamage() - amount, 0, maxDamage);

        setDamage(damage);
        cir.setReturnValue(damage >= maxDamage);
    }

    @Unique
    private boolean qualifies() {
        NbtList list = getEnchantments();
        boolean recover = false, mending = false;

        for (int i = 0; i < list.size(); i++) {
            NbtCompound compound = list.getCompound(i);
            Enchantment enchantment = Registries.ENCHANTMENT.get(EnchantmentHelper.getIdFromNbt(compound));

            if (enchantment == null)
                continue;

            recover = recover || enchantment.equals(RecoverEnchantment.INSTANCE);
            mending = mending || enchantment.equals(Enchantments.MENDING);

            if (recover && mending)
                return true;
        }

        return false;
    }
}