package com.hadi.themoment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hadi.themoment.ui.screens.AmPm
import com.hadi.themoment.ui.theme.DisplayGreen
import com.hadi.themoment.ui.theme.SpaceGroteskMedium
import com.hadi.themoment.ui.theme.WeatherStyle

@Composable
fun WeatherStat(
    modifier : Modifier = Modifier,
    displayColor : Color = DisplayGreen,
    value : String = "",
    skeleton :String = ""
) {

    Box(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(
                displayColor,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        Row(
            verticalAlignment = Alignment.Top,
        ) {

            DigitalText(
                text = value,
                skeletonText = skeleton,
                fontSize = 26.sp
            )
            Text(
                text = " °C",
                style = SpaceGroteskMedium,
                fontSize = 20.sp
            )
        }
    }

}