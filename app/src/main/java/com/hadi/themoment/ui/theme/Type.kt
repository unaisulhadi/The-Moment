package com.hadi.themoment.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hadi.themoment.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val shadow = Shadow(
    color = Color.Black.copy(alpha = 0.3F),
    offset = Offset(4f, 4f),
    blurRadius = 8f
)

val TimeTextRegularSkeleton = TextStyle(
    color = Color.Black,
    fontFamily = FontFamily(Font(R.font.dseg_7_classic_regular)),
    fontSize = 18.sp,
)

val TimeTextRegular = TextStyle(
    color = Color.Black,
    fontFamily = FontFamily(Font(R.font.dseg_7_classic_regular)),
    fontSize = 18.sp,
    shadow = shadow
)

val WeatherStyle = TextStyle(
    color = Color.Black,
    fontFamily = FontFamily(Font(R.font.dseg_weather)),
    fontSize = 18.sp,
    shadow = shadow
)


val DinProStyle = TextStyle(
    color = Color.Black,
    fontFamily = FontFamily(Font(R.font.din_pro)),
    fontSize = 18.sp,
    shadow = shadow
)


val EurostileTextBold = TextStyle(
    color = Color.Black,
    fontFamily = FontFamily(Font(R.font.eurostile_bold)),
    fontSize = 28.sp,
    shadow = shadow
)

val SpaceGroteskMedium = TextStyle(
    color = Color.Black,
    fontFamily = FontFamily(Font(R.font.space_grotesk_medium)),
    fontSize = 18.sp,
    shadow = shadow
)