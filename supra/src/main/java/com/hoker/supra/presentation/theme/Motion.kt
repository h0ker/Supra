package com.hoker.supra.presentation.theme

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing

/**
 * Durations (ms) and easings shared with the web design system's motion tokens.
 */
object Motion {
    /** --duration-fade */
    const val fade = 200

    /** --duration-collapse: disclosure body and triangle rotation */
    const val collapse = 240

    /** --ease-standard, cubic-bezier(.4, 0, .2, 1) */
    val standardEasing: Easing = FastOutSlowInEasing
}
