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
    cornerRadius : Dp = 0.dp
) {
    val dotRadius by animateDpAsState(
        targetValue = if (selected) RadioButtonDotSize / 2 else 0.dp,
        animationSpec = tween(durationMillis = RadioAnimationDuration)
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
                    radius = RadioButtonRippleRadius
                )
            )
        } else {
            Modifier
        }

    Canvas(
        modifier
            .then(selectableModifier)
            .wrapContentSize(Alignment.Center)
            .padding(RadioButtonPadding)
            .requiredSize(RadioButtonSize)
    ) {
        drawRadio(radioColor, dotRadius,cornerRadius)
    }
}


private fun DrawScope.drawRadio(color: Color, dotRadius: Dp,cornerRadius: Dp) {
    val strokeWidth = RadioStrokeWidth.toPx()
    drawRect(
        color,
        topLeft = Offset(0f, 0f),
        size = Size(RadioRadius.toPx() * 2, RadioRadius.toPx() * 2),
        style = Stroke(
            width = 2.dp.toPx(),
            pathEffect = PathEffect.cornerPathEffect(cornerRadius.toPx())
        )
    )
    if (dotRadius > 0.dp) {
            drawRoundRect(
                color,
                topLeft = Offset(RadioStrokeWidth.toPx(),RadioStrokeWidth.toPx()),
                size = Size(
                    (RadioRadius.toPx() - strokeWidth) * 2,
                    (RadioRadius.toPx() - strokeWidth) * 2
                ),
                cornerRadius = CornerRadius(
                    x =cornerRadius.toPx(),
                    y = cornerRadius.toPx()
                )
            )
    }
}

@Stable
interface RadioButtonColors {
    /**
     * Represents the main color used to draw the outer and inner circles, depending on whether
     * the [RadioButton] is [enabled] / [selected].
     *
     * @param enabled whether the [RadioButton] is enabled
     * @param selected whether the [RadioButton] is selected
     */
    @Composable
    fun radioColor(enabled: Boolean, selected: Boolean): State<Color>
}


object RadioButtonDefaults {
    /**
     * Creates a [RadioButtonColors] that will animate between the provided colors according to
     * the Material specification.
     *
     * @param selectedColor the color to use for the RadioButton when selected and enabled.
     * @param unselectedColor the color to use for the RadioButton when unselected and enabled.
     * @param disabledColor the color to use for the RadioButton when disabled.
     * @return the resulting [Color] used for the RadioButton
     */
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
            DefaultRadioButtonColors(
                selectedColor,
                unselectedColor,
                disabledColor
            )
        }
    }
}


@Stable
private class DefaultRadioButtonColors(
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
            animateColorAsState(target, tween(durationMillis = RadioAnimationDuration))
        } else {
            rememberUpdatedState(target)
        }
    }
}


private const val RadioAnimationDuration = 100

private val RadioButtonRippleRadius = 24.dp
private val RadioButtonPadding = 2.dp
private val RadioButtonSize = 20.dp
private val RadioRadius = RadioButtonSize / 2
private val RadioButtonDotSize = 12.dp
private val RadioStrokeWidth = 4.dp