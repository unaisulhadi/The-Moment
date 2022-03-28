package com.hadi.themoment.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.hadi.themoment.ui.theme.TimeTextRegular
import com.hadi.themoment.ui.theme.TimeTextRegularSkeleton

@Composable
fun DigitalText(
    text:String,
    skeletonText:String,
    fontSize : TextUnit = 20.sp,
    textStyle: TextStyle = TimeTextRegular,
    skeletonStyle:TextStyle = TimeTextRegularSkeleton
) {
    Box() {
        //TIME SKELETON
        Text(
            text = skeletonText,
            style = skeletonStyle,
            fontSize = fontSize,
            modifier = Modifier.alpha(0.03f)
        )

        // Time
        Text(
            text = text,
            style = textStyle,
            fontSize = fontSize
        )
    }

}