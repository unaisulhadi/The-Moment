package com.hadi.themoment.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hadi.themoment.data.enum.Display
import com.hadi.themoment.ui.theme.DinProStyle

@Composable
fun ChooseDisplay(
    selectedDisplay: Display,
    onDisplaySelected: (Display) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 0..2) {
            val display = Display.values()[i]
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(
                    onClick = {
                        onDisplaySelected(display)
                    }
                )
            ) {

                SquareRadioButton(
                    selected = display == selectedDisplay,
                    onClick = {
                        onDisplaySelected(display)
                    },
                    colors = SquareRadioButtonDefaults.colors(
                        selectedColor = Color.Green,
                        unselectedColor = Color.White
                    ),
                    cornerRadius = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = display.toString(), color = Color.White, style = DinProStyle)
            }
        }
    }

}