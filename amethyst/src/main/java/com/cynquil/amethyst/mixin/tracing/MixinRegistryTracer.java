package com.cynquil.amethyst.mixin.tracing;

import com.cynquil.amethyst.Amethyst;
import com.cynquil.amethyst.tracing.OriginAware;
import net.minecraft.registry.MutableRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.lang.StackWalker.StackFrame;
import java.util.Iterator;
import java.util.Map;

@Mixin(SimpleRegistry.class)
public abstract class MixinRegistryTracer<T> implements MutableRegistry<T> {
    @Shadow
    @Nullable
    private Map<T, RegistryEntry.Reference<T>> intrusiveValueToEntry;

    @Inject(method = "freeze", at = @At(value = "HEAD"))
    private void freeze(CallbackInfoReturnable<Registry<T>> cir) {
        Amethyst.INSTANCE.getLogger().info("CHECKPOINT");

        if (intrusiveValueToEntry == null)
            return;

        for (RegistryEntry.Reference<?> entry : intrusiveValueToEntry.values()) {
            if (!(entry.value() instanceof OriginAware originAware)) {
                Amethyst.INSTANCE.getLogger().info("No origin trace for " + entry.value());
                continue;
            }

            var origin = new StringBuilder().append("Origin of ").append(originAware).append('\n');

            for (Iterator<StackFrame> iter = originAware.getOrigin().iterator(); iter.hasNext();) {
                StackFrame frame = iter.next();
                origin.append("\tat ").append(frame);

                if (iter.hasNext())
                    origin.append('\n');
            }

            Amethyst.INSTANCE.getLogger().info(origin.toString());
        }

        Amethyst.INSTANCE.getLogger().info("CHECKPOINT END");
    }
}
