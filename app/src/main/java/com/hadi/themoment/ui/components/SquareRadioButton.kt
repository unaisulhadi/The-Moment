package com.hadi.themoment.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.RadioButtonColors
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SquareRadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: RadioButtonColors = RadioButtonDefaults.colors(),
    cornerRadius: Dp = 0.dp
) {
    val dotRadius by animateDpAsState(
        targetValue = if (selected) SquareRadioButtonDotSize / 2 else 0.dp,
        animationSpec = tween(durationMillis = SquareRadioAnimationDuration)
    )
    val radioColor by colors.radioColor(enabled, selected)
    val selectableModifier =
        if (onClick != null) {
            Modifier.selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.RadioButton,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = SquareRadioButtonRippleRadius
                )
            )
        } else {
            Modifier
        }

    Canvas(
        modifier
            .then(selectableModifier)
            .wrapContentSize(Alignment.Center)
            .padding(SquareRadioButtonPadding)
            .requiredSize(SquareRadioButtonSize)
    ) {
        drawRadio(radioColor, dotRadius, cornerRadius)
    }
}


private fun DrawScope.drawRadio(color: Color, dotRadius: Dp, cornerRadius: Dp) {
    val strokeWidth = SquareRadioStrokeWidth.toPx()
    drawRect(
        color,
        topLeft = Offset(0f, 0f),
        size = Size(SquareRadioRadius.toPx() * 2, SquareRadioRadius.toPx() * 2),
        style = Stroke(
            width = 2.dp.toPx(),
            pathEffect = PathEffect.cornerPathEffect(cornerRadius.toPx())
        )
    )
    if (dotRadius > 0.dp) {
        drawRoundRect(
            color,
            topLeft = Offset(SquareRadioStrokeWidth.toPx(), SquareRadioStrokeWidth.toPx()),
            size = Size(
                (SquareRadioRadius.toPx() - strokeWidth) * 2,
                (SquareRadioRadius.toPx() - strokeWidth) * 2
            ),
            cornerRadius = CornerRadius(
                x = cornerRadius.toPx() * 0.5f,
                y = cornerRadius.toPx() * 0.5f
            )
        )
    }
}

@Stable
interface SquareRadioButtonColors {

    @Composable
    fun radioColor(enabled: Boolean, selected: Boolean): State<Color>
}


object SquareRadioButtonDefaults {

    @Composable
    fun colors(
        selectedColor: Color = MaterialTheme.colors.secondary,
        unselectedColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
        disabledColor: Color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
    ): RadioButtonColors {
        return remember(
            selectedColor,
            unselectedColor,
            disabledColor
        ) {
            DefaultSquareRadioButtonColors(
                selectedColor,
                unselectedColor,
                disabledColor
            )
        }
    }
}


@Stable
private class DefaultSquareRadioButtonColors(
    private val selectedColor: Color,
    private val unselectedColor: Color,
    private val disabledColor: Color
) : RadioButtonColors {
    @Composable
    override fun radioColor(enabled: Boolean, selected: Boolean): State<Color> {
        val target = when {
            !enabled -> disabledColor
            !selected -> unselectedColor
            else -> selectedColor
        }

        // If not enabled 'snap' to the disabled state, as there should be no animations between
        // enabled / disabled.
        return if (enabled) {
            animateColorAsState(target, tween(durationMillis = SquareRadioAnimationDuration))
        } else {
            rememberUpdatedState(target)
        }
    }
}


private const val SquareRadioAnimationDuration = 100

private val SquareRadioButtonRippleRadius = 24.dp
private val SquareRadioButtonPadding = 2.dp
private val SquareRadioButtonSize = 20.dp
private val SquareRadioRadius = SquareRadioButtonSize / 2
private val SquareRadioButtonDotSize = 12.dp
private val SquareRadioStrokeWidth = 4.dp