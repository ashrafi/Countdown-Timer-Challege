/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}
private val timeLeftInSeconds = MutableStateFlow<Long>(0L)
private var currenTimer: Job? = null
// Start building your app here!
@Composable
fun MyApp() {
    val cxt = LocalContext.current
    var totalTimeTxt = "0"
    // var amountOfTime = remember { mutableStateOf(1L) }
    var startingTime = remember { mutableStateOf(1L) }
    var timeLeftSec = timeLeftInSeconds.collectAsState()
    val timeLeft = timeLeftSec.value / (startingTime.value * 60.0)

    Surface(color = MaterialTheme.colors.background) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            // Create references for the composables to constrain
            val (topRow, timer, progress, bottomRow) = createRefs()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(topRow) {
                        top.linkTo(parent.top, margin = 20.dp)
                        // bottom.linkTo(progress.top)
                    },
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                Button(
                    onClick = {
                        Toast.makeText(cxt, "Timer Started", Toast.LENGTH_LONG)
                            .show()
                        // kill any running timers
                        currenTimer?.cancel()
                        currenTimer = myCountdownTimer(cxt, startingTime.value * 60)
                    }
                ) {
                    Text("Start")
                }
                Spacer(modifier = Modifier.size(20.dp))
                Button(
                    onClick = {
                        currenTimer?.cancel()
                        timeLeftInSeconds.value = 0
                    }
                ) {
                    Text("Cancel")
                }
            }
            Box(
                modifier = Modifier.constrainAs(progress) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                contentAlignment = Alignment.Center
                // verticalArrangement = Arrangement.Center,
            ) {
                ProgressDial(timeLeft.toFloat())
            }
            Text(
                getFormattedStopWatchTime(timeLeftSec.value), // timeLeft.value),
                modifier = Modifier.constrainAs(timer) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                style = MaterialTheme.typography.h2
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(bottomRow) {
                        bottom.linkTo(parent.bottom, margin = 30.dp)
                    },
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        if (startingTime.value > 1)
                            startingTime.value--
                    }
                ) {
                    Text("Dec (-)")
                }
                Text(
                    "${startingTime.value} minute(s)",
                    style = MaterialTheme.typography.h4
                    // modifier = Modifier.padding(16.dp)
                )
                Button(onClick = { startingTime.value++ }) {
                    Text("(+) Inc")
                }
            }
        }

        // Log.v(TAG, "This is percnet ${timeLeftInSecondsState.value / totalSeconds}")
    }
    // SimpleOutlinedTextFieldSample(amountOfTime)
}

@Composable
fun SimpleOutlinedTextFieldSample(val_text: MutableState<String>) {
    var text by remember { mutableStateOf("1") }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            val_text.value = it
        },
        label = { Text("Label") }
    )
}

private fun myCountdownTimer(cnt: Context, totalSeconds: Long): Job? {
    /**
     * Start Timer
     * TODO:  make this an alarm
     */
    var mainTimer: Job? = null
    Log.v(TAG, "Counter called")

    mainTimer = CoroutineScope(Dispatchers.Main).launch {
        for (seconds in totalSeconds downTo 0) {
            val time = String.format(
                "%02d:%02d",
                TimeUnit.SECONDS.toMinutes(seconds),
                seconds - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.SECONDS.toMinutes(
                        seconds
                    )
                )
            )
            timeLeftInSeconds.value = seconds
            Log.d(TAG, "This is the time $time with sec $seconds tt ${timeLeftInSeconds.value}")
            delay(1000) // sec
        }
        Log.d(TAG, "Done")
        Toast.makeText(cnt, "Timer Done", Toast.LENGTH_LONG).show()
    }
    return mainTimer
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        // MyApp(timeLeftInSeconds)
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        // MyApp(timeLeftInSeconds)
    }
}

public const val TAG = "CDTimer"
