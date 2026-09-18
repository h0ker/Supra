package com.hoker.supra.presentation.surfaces

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoker.supra.presentation.cards.MaterialStyle
import com.hoker.supra.presentation.cards.SupraTextureCard
import com.hoker.supra.presentation.cards.TextureType
import com.hoker.supra.presentation.shapes.SupraShapes
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.theme.Ink1
import com.hoker.supra.presentation.theme.Ink2
import com.hoker.supra.presentation.theme.Ink3
import com.hoker.supra.presentation.theme.Ink4
import com.hoker.supra.presentation.theme.Ink5
import com.hoker.supra.presentation.theme.Ink7
import com.hoker.supra.presentation.theme.Motion

/**
 * Groups related controls behind one header row. The header is a flat slate row at list weight, so
 * collapsibles and [com.hoker.supra.presentation.list_items.SupraTextureListItem]s stack in one list.
 *
 * - The triangle is the only affordance: right when closed, rotated 90° down when open. No chevrons,
 *   no plus/minus, no "Show more" label.
 * - Don't nest collapsibles; one level of grouping only. A section that needs scrolling wants its own screen.
 * - Keep body content at body scale (16sp) or below, so nothing inside shouts louder than the header.
 * - Uncontrolled by default. Pass [open] with [onToggle] only when something else has to own the state
 *   (accordion behaviour, deep links).
 *
 * @param status Short mono status beside the triangle; turns accent while open. Must be genuine data.
 */
@Composable
fun SupraCollapsibleArea(
    modifier: Modifier = Modifier,
    title: String,
    status: String? = null,
    leftIcon: (@Composable () -> Unit)? = null,
    initiallyOpen: Boolean = false,
    open: Boolean? = null,
    onToggle: ((Boolean) -> Unit)? = null,
    enabled: Boolean = true,
    headerHeight: Dp = Sizes.controlHeight,
    content: @Composable ColumnScope.() -> Unit
) {
    var selfOpen by remember { mutableStateOf(initiallyOpen) }
    val isOpen = open ?: selfOpen
    val accent = MaterialTheme.colorScheme.secondary
    val collapseSpec = tween<Float>(Motion.collapse, easing = Motion.standardEasing)

    val rotation by animateFloatAsState(
        targetValue = if (isOpen) 90f else 0f,
        animationSpec = collapseSpec,
        label = "triangle"
    )
    val markColor by animateColorAsState(
        targetValue = if (isOpen) accent else Ink5,
        animationSpec = tween(Motion.fade, easing = Motion.standardEasing),
        label = "triangle_color"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isOpen) Ink4 else Ink2,
        animationSpec = tween(Motion.fade, easing = Motion.standardEasing),
        label = "border"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (enabled) 1f else .38f)
            .clip(SupraShapes.row)
            .background(Ink1)
            .border(1.dp, borderColor, SupraShapes.row)
    ) {
        SupraTextureCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
                .clickable(enabled = enabled, role = Role.Button) {
                    if (open == null) selfOpen = !selfOpen
                    onToggle?.invoke(!isOpen)
                }
                .semantics { stateDescription = if (isOpen) "Expanded" else "Collapsed" },
            material = MaterialStyle.FLAT,
            textureType = TextureType.SLATE,
            backgroundColor = Ink3,
            shape = SupraShapes.row
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Sizes.small, end = Sizes.medium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Sizes.small)
            ) {
                leftIcon?.invoke()
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = if (leftIcon == null) Sizes.small else 0.dp),
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    color = Ink7,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                status?.let {
                    Text(
                        text = it.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = markColor,
                        maxLines = 1
                    )
                }
                // ▶ 8×10 triangle, rotated down while open
                Canvas(
                    modifier = Modifier
                        .size(width = 8.dp, height = 10.dp)
                        .rotate(rotation)
                ) {
                    drawPath(
                        path = Path().apply {
                            moveTo(0f, 0f)
                            lineTo(size.width, size.height / 2f)
                            lineTo(0f, size.height)
                            close()
                        },
                        color = markColor
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = isOpen,
            enter = expandVertically(tween(Motion.collapse, easing = Motion.standardEasing)),
            exit = shrinkVertically(tween(Motion.collapse, easing = Motion.standardEasing))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        drawLine(Ink2, Offset(0f, 0f), Offset(size.width, 0f), 1.dp.toPx())
                    }
                    .padding(Sizes.small),
                verticalArrangement = Arrangement.spacedBy(Sizes.small),
                content = content
            )
        }
    }
}
