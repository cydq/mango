package com.cynquil.amethyst.util

import java.util.function.Predicate

class Before<T>(private val matcher: Predicate<T>) : Predicate<T> {
    private var found = false

    override fun test(t: T): Boolean {
        if (!found && matcher.test(t))
            found = true

        return !found
    }
}