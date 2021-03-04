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

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

// Start building your app here!
@Composable
fun MyApp() {
    var currenTimer: Job? = null
    Surface(color = MaterialTheme.colors.background) {
        Column() {
            Row() {
                Button(
                    onClick = {
                        currenTimer = myCountdownTimer()
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
            TimerUI(timeLeftInSeconds)
        }
    }
}
private val timeLeftInSeconds = MutableStateFlow<Long>(0L)

private fun myCountdownTimer(): Job? {
    /**
     * Start Timer
     * TODO:  make this an alarm
     */
    var mainTimer: Job? = null
    val totalSeconds = 60L

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
            Log.d(TAG, "This is the time $time")
            delay(1000) // sec
        }
        Log.d(TAG, "Done")
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
