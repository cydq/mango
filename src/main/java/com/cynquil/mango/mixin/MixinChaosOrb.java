package com.cynquil.mango.mixin;

import com.cynquil.mango.currency.ExperienceOrbs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoneyBottleItem.class)
public abstract class MixinChaosOrb {
    @Unique
    private static final int CHAOS_ORB_AMOUNT = ExperienceOrbs.INSTANCE.getChaosOrb().getValue();

    @Inject(
        at = @At(value = "RETURN", ordinal = 0),
        method = "finishUsing",
        cancellable = true
    )
    private void finishUsingFirst(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (!(user instanceof PlayerEntity player))
            return;

        if (player.experienceLevel < 49)
            return;

        player.addExperience(-CHAOS_ORB_AMOUNT);
        cir.setReturnValue(new ItemStack(ExperienceOrbs.INSTANCE.getChaosOrb().asItem()));
    }

    @Inject(
        at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;<init>(Lnet/minecraft/item/ItemConvertible;)V"),
        method = "finishUsing",
        cancellable = true
    )
    private void finishUsingSecond(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (!(user instanceof PlayerEntity player))
            return;

        if (player.experienceLevel < 49)
            return;

        player.addExperience(-CHAOS_ORB_AMOUNT);
        var item = new ItemStack(ExperienceOrbs.INSTANCE.getChaosOrb().asItem());

        if (!player.getInventory().insertStack(item))
            player.dropItem(item, false);

        cir.setReturnValue(stack);
    }
}