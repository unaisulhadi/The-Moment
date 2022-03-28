package com.hadi.themoment

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hadi.themoment.data.enum.Display
import com.hadi.themoment.data.model.Time
import com.hadi.themoment.ui.components.ChooseDisplay
import com.hadi.themoment.ui.components.DigitalText
import com.hadi.themoment.ui.theme.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMomentTheme {
                MainScreen()
            }
        }
    }
}


@Composable
fun MainScreen() {
    val scope = rememberCoroutineScope()
    val displayState = remember {
        mutableStateOf(Display.Green)
    }

    val hour = remember { mutableStateOf("") }
    val minute = remember { mutableStateOf("") }
    val second = remember { mutableStateOf("") }
    val amPm = remember { mutableStateOf("") }
    setTimes(hour, minute, second, amPm)

    LaunchedEffect(key1 = Unit, block = {
        while (true) {
            delay(1000L)
            setTimes(hour, minute, second, amPm)
        }
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Display Style",
                style = DinProStyle,
                color = Color.White,
                modifier = Modifier.padding(top = 4.dp, start = 16.dp, end = 16.dp)
            )
            ChooseDisplay(selectedDisplay = displayState.value, onDisplaySelected = { display ->
                displayState.value = display
            })
            Text(
                text = "Time",
                style = DinProStyle,
                color = Color.White,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
            )
            CurrentTime(
                time = Time(
                    hours = hour.value,
                    minutes = minute.value,
                    seconds = second.value,
                    amPm = amPm.value
                ),
                displayColor = when (displayState.value) {
                    Display.Green -> DisplayGreen
                    Display.Grey -> DisplayGrey
                    Display.Orange -> DisplayOrange
                }
            )
        }


    }

}

@Composable
fun CurrentTime(
    modifier: Modifier = Modifier,
    time: Time,
    displayColor: Color = DisplayGreen
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(
                displayColor,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        Row(
            verticalAlignment = Alignment.Bottom,
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

private fun setTimes(
    hour: MutableState<String>,
    minute: MutableState<String>,
    second: MutableState<String>,
    amPm: MutableState<String>
) {
    val calendar = Calendar.getInstance()
    val date = Date(calendar.timeInMillis)

    hour.value = SimpleDateFormat("hh",Locale.getDefault()).format(date)
    minute.value = SimpleDateFormat("mm",Locale.getDefault()).format(date)
    second.value = SimpleDateFormat("ss",Locale.getDefault()).format(date)
    amPm.value = SimpleDateFormat("aa",Locale.getDefault()).format(date)
}