package com.hadi.themoment.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RetroRadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
) {
    val dotRadius by animateDpAsState(
        targetValue = if (selected) RadioButtonDotSize / 2 else 0.dp,
        animationSpec = tween(durationMillis = RadioAnimationDuration)
    )

    Canvas(
        Modifier
            .wrapContentSize(Alignment.Center)
            .padding(RadioButtonPadding)
            .requiredSize(RadioButtonSize)
    ) {
        drawRadio(Color.Red, dotRadius)
    }
}


private fun DrawScope.drawRadio(color: Color, dotRadius: Dp) {
    val strokeWidth = RadioStrokeWidth.toPx()
    drawRect(
        color,
        topLeft = Offset(0f, 0f),
        size = Size(RadioRadius.toPx() * 2, RadioRadius.toPx() * 2),
        style = Stroke(
            width = 2.dp.toPx(),
//            pathEffect = PathEffect.cornerPathEffect(2.dp.toPx())
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
//                cornerRadius = CornerRadius(
//                    x = 1.dp.toPx(),
//                    y = 1.dp.toPx()
//                )
            )
    }
}

private const val RadioAnimationDuration = 100

private val RadioButtonRippleRadius = 24.dp
private val RadioButtonPadding = 2.dp
private val RadioButtonSize = 20.dp
private val RadioRadius = RadioButtonSize / 2
private val RadioButtonDotSize = 12.dp
private val RadioStrokeWidth = 4.dp