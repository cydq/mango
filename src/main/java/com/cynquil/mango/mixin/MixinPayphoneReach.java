package com.cynquil.mango.mixin;

import com.cynquil.mango.payphone.PayphoneUtils;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public interface MixinPayphoneReach {
    @Inject(method = "canPlayerUse(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/player/PlayerEntity;)Z", at = @At("HEAD"), cancellable = true)
    private static void canPlayerUse(BlockEntity b, PlayerEntity p, CallbackInfoReturnable<Boolean> cir) {
        if (b instanceof BarrelBlockEntity) {
            World world = b.getWorld();

            if (world == null)
                return;

            cir.setReturnValue(PayphoneUtils.INSTANCE.isLoaded(world, b.getPos()));
        }
    }
}