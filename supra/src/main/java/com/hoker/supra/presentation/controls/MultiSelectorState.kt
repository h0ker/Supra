package com.hoker.supra.presentation.controls

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Stable
interface MultiSelectorState {
    val selectedIndex: Float
    val startCornerPercent: Int
    val endCornerPercent: Int
    val textColors: List<Color>
    fun selectOption(scope: CoroutineScope, index: Int)
}

@Stable
class MultiSelectorStateImpl(
    options: List<String>,
    selectedOption: String,
    private val selectedColor: Color,
    private val unselectedColor: Color
): MultiSelectorState {
    override val selectedIndex: Float
        get() = _selectedIndex.value

    override val startCornerPercent: Int
        get() = _startCornerPercent.value.toInt()

    override val endCornerPercent: Int
        get() = _endCornerPercent.value.toInt()

    override val textColors: List<Color>
        get() = _textColors.value

    private var _selectedIndex = Animatable(options.indexOf(selectedOption).toFloat())

    private var _startCornerPercent = Animatable(
        if(options.first() == selectedOption) {
            50f
        } else {
            15f
        }
    )

    private var _endCornerPercent = Animatable(
        if(options.last() == selectedOption) {
            50f
        } else {
            15f
        }
    )

    private var _textColors: State<List<Color>> = derivedStateOf {
        List(numOptions) { index ->
            lerp(
                start = unselectedColor,
                stop = selectedColor,
                fraction = 1f - (((selectedIndex - index.toFloat()).absoluteValue).coerceAtMost(1f))
            )
        }
    }

    private val numOptions = options.size

    private val animationSpec = tween<Float>(
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )

    override fun selectOption(scope: CoroutineScope, index: Int) {
        scope.launch {
            _selectedIndex.animateTo(
                targetValue = index.toFloat(),
                animationSpec = animationSpec
            )
        }

        scope.launch {
            _startCornerPercent.animateTo(
                targetValue = if(index == 0) 50f else 15f,
                animationSpec = animationSpec
            )
        }

        scope.launch {
            _endCornerPercent.animateTo(
                targetValue = if(index == numOptions - 1) 50f else 15f,
                animationSpec = animationSpec
            )
        }
    }
}

@Composable
fun rememberMultiSelectorState(
    options: List<String>,
    selectedOption: String,
    selectedColor: Color,
    unselectedColor: Color
) = remember {
    MultiSelectorStateImpl(
        options,
        selectedOption,
        selectedColor,
        unselectedColor
    )
}