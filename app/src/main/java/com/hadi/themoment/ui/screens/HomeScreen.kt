package com.hadi.themoment.ui.screens

import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hadi.themoment.data.enum.Display
import com.hadi.themoment.data.model.Time
import com.hadi.themoment.ui.components.ChooseDisplay
import com.hadi.themoment.ui.components.DigitalText
import com.hadi.themoment.ui.components.BatteryStat
import com.hadi.themoment.ui.components.WeatherStat
import com.hadi.themoment.ui.theme.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen() {
    val scope = rememberCoroutineScope()
    val displayState = remember {
        mutableStateOf(Display.Green)
    }

    val hour = remember { mutableStateOf("") }
    val minute = remember { mutableStateOf("") }
    val second = remember { mutableStateOf("") }
    val amPm = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }

    val batteryPercentage = remember { mutableStateOf(0) }
    val batteryStatus = remember { mutableStateOf(false) }

    setTimes(hour, minute, second, amPm, date)

    val context = LocalContext.current
    LaunchedEffect(key1 = Unit, block = {
        while (true) {
            delay(1000L)
            setTimes(hour, minute, second, amPm, date)
            batteryPercentage.value = getBatteryPercentage(context)
            batteryStatus.value = getBatteryStatus(context)
        }
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(4.dp)
            ) {
                Text(
                    text = "Display Style",
                    style = DinProStyle,
                    color = Color.White,
                    modifier = Modifier.padding(top = 4.dp, start = 16.dp, end = 16.dp)
                )
                ChooseDisplay(selectedDisplay = displayState.value, onDisplaySelected = { display ->
                    displayState.value = display
                })
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
            ) {

                CurrentTime(
                    time = Time(
                        hours = hour.value,
                        minutes = minute.value,
                        seconds = second.value,
                        amPm = amPm.value,
                        date = date.value
                    ),
                    displayColor = getDisplayColor(displayState)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BatteryStat(
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp),
                        displayColor = getDisplayColor(displayState),
                        skeleton = "88",
                        isCharging = batteryStatus.value,
                        value = "${batteryPercentage.value}"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    WeatherStat(
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp),
                        skeleton = "88",
                        displayColor = getDisplayColor(displayState),
                        value = "27"
                    )
                }

            }

        }


    }

}

fun getDisplayColor(displayState: MutableState<Display>): Color {
    return when (displayState.value) {
        Display.Green -> DisplayGreen
        Display.Grey -> DisplayGrey
        Display.Orange -> DisplayOrange
    }
}

@Composable
fun CurrentTime(
    time: Time,
    displayColor: Color = DisplayGreen
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                displayColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {

                DigitalText(
                    text = "${time.hours}:${time.minutes}",
                    skeletonText = "88:88",
                    fontSize = 52.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                DigitalText(
                    text = "${time.seconds}",
                    skeletonText = "88",
                    fontSize = 28.sp,
                )
                Spacer(modifier = Modifier.width(8.dp))
                AmPm(amPm = time.amPm.uppercase())
            }
            DisplayDivider()
            Text(
                text = time.date.uppercase(Locale.getDefault()),
                style = SpaceGroteskMedium,
                fontSize = 22.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

        }

    }
}

@Composable
fun AmPm(amPm: String) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "AM",
            style = SpaceGroteskMedium,
            modifier = Modifier.alpha(
                alpha = if (amPm == "AM") 1f else 0.15F
            )
        )
        Text(
            text = "PM", style = SpaceGroteskMedium,
            modifier = Modifier
                .alpha(
                    alpha = if (amPm == "PM") 1f else 0.15F
                )
        )

    }
}


@Composable
fun DisplayDivider(
    modifier: Modifier = Modifier,
    dividerHeight: Dp = 2.dp
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .height(dividerHeight)
            .background(
                color = Color.Black,
            )
            .shadow(elevation = 5.dp)
    )

}

private fun setTimes(
    hour: MutableState<String>,
    minute: MutableState<String>,
    second: MutableState<String>,
    amPm: MutableState<String>,
    date: MutableState<String>
) {
    val calendar = Calendar.getInstance()
    val currentDate = Date(calendar.timeInMillis)

    hour.value = SimpleDateFormat("hh", Locale.getDefault()).format(currentDate)
    minute.value = SimpleDateFormat("mm", Locale.getDefault()).format(currentDate)
    second.value = SimpleDateFormat("ss", Locale.getDefault()).format(currentDate)
    amPm.value = SimpleDateFormat("aa", Locale.getDefault()).format(currentDate)
    date.value = SimpleDateFormat("EEE - dd  MMM", Locale.getDefault()).format(currentDate)

}

fun getBatteryPercentage(context: Context): Int {
    val bm = context.getSystemService(BATTERY_SERVICE) as BatteryManager
    return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
}

fun getBatteryStatus(context: Context): Boolean {
    val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
        context.registerReceiver(null, ifilter)
    }

    val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
    val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
            || status == BatteryManager.BATTERY_STATUS_FULL
    return isCharging

}