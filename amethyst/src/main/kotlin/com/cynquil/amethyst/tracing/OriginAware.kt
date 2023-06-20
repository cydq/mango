package com.cynquil.amethyst.tracing

import java.lang.StackWalker.StackFrame

interface OriginAware {
    val origin: List<StackFrame>
}