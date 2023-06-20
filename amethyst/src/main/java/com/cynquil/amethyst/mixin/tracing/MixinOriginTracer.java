package com.cynquil.amethyst.mixin.tracing;

import com.cynquil.amethyst.tracing.OriginAware;
import com.cynquil.amethyst.util.Before;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.StackWalker.StackFrame;
import java.util.List;
import java.util.stream.Stream;

@Mixin({ Item.class })
public class MixinOriginTracer implements OriginAware {
    @Mutable @Unique @Final
    private List<StackFrame> origin;

    @Inject(at = @At("TAIL"), method = "<init>")
    private void constructor(CallbackInfo info) {
        origin = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).walk(this::walk);
    }

    @Unique
    private List<StackFrame> walk(Stream<StackFrame> trace) {
        return trace
            .dropWhile(this::trimHead)
            .takeWhile(new Before<>(frame -> frame.getClassName().equals("net.fabricmc.loader.impl.entrypoint.EntrypointUtils")))
            .toList();
    }

    @Unique
    private boolean trimHead(StackFrame frame) {
        return frame.getMethodName().contains("constructor")
            || frame.getMethodName().equals("<init>")
            && frame.getDeclaringClass().isAssignableFrom(getClass());
    }

    @Unique
    @NotNull
    public List<StackFrame> getOrigin() {
        return origin;
    }
}