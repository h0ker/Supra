package com.hoker.supra.presentation.scaffolds

import com.hoker.supra.presentation.cards.TextureType
import androidx.compose.ui.graphics.Color

/**
 * Sealed class representing the different background types available for [SupraScaffold].
 */
sealed class SupraBackground {

    /**
     * A static texture background (e.g. Slate or Topographic patterns).
     *
     * @param textureType The type of texture to render.
     * @param tint The tint color applied over the texture image.
     *             If null, defaults to a darkened version of the scaffold's borderColor.
     */
    data class Texture(
        val textureType: TextureType,
        val tint: Color? = null
    ) : SupraBackground()

    /**
     * A gyroscope-reactive background that renders a grid of "+" symbols.
     * The grid shifts in response to device tilt, creating a parallax effect
     * against the content layer.
     *
     * @param symbolColor The color of the "+" symbols. If null, defaults to a
     *                    darkened version of the scaffold's borderColor.
     * @param symbolSize The font size of each "+" symbol in sp.
     * @param gridSpacing The spacing between symbols in dp.
     */
    data class AnimatedGrid(
        val symbolColor: Color? = null,
        val symbolSize: Float = 10f,
        val gridSpacing: Float = 30f
    ) : SupraBackground()

    /**
     * An animated background that renders diagonal stripes that scroll upward.
     *
     * @param stripeColor The color of the stripes. If null, defaults to a
     *                    darkened version of the scaffold's borderColor.
     * @param stripeWidth The width of each stripe in dp.
     * @param gapWidth The width of the gap between stripes in dp.
     * @param angle The angle of the stripes in degrees. 45f = standard diagonal.
     * @param scrollSpeed Duration in milliseconds for one full vertical scroll cycle.
     *                    Higher values = slower scrolling.
     * @param animated Whether the stripes scroll continuously. When false the
     *                stripes are rendered statically with no animation.
     */
    data class DiagonalStripes(
        val stripeColor: Color? = null,
        val stripeWidth: Float = 3f,
        val gapWidth: Float = 30f,
        val angle: Float = 45f,
        val scrollSpeed: Int = 10000,
        val animated: Boolean = true
    ) : SupraBackground()
}
