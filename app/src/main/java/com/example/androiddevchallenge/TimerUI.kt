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

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun TimerUI(timeLeftInSeconds: MutableStateFlow<Long>) {
    val totalSeconds = 60F
    val timeLeftInSecondsState = timeLeftInSeconds.collectAsState()
    // alertime.collectAsState().value.toFloat() /  (totalTime.toFloat() * 60)
    Log.v(TAG, "This is percnet ${timeLeftInSecondsState.value / totalSeconds}")
    val timeLeft = timeLeftInSecondsState.value / totalSeconds
    Column() {
        TimerCountdown(timeLeftInSecondsState.value.toLong())

        ProgressDial(timeLeft)
    }
}

@Composable
fun ProgressDial(timeLeft: Float) {
    // val progressTime =
    Log.v(TAG, "progress bar $timeLeft")

    Box(
        modifier = Modifier
            .padding(7.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        CircularProgressIndicator(
            progress = 1.0F,
            strokeWidth = 25.dp,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent),
            color = Color(
                0x77d1d1e0,
            )
        )
        CircularProgressIndicator(
            progress = timeLeft, // 0.0 to 1.0
            strokeWidth = 50.dp,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent),
            color = // if (timeDisplay > .25) Color.Green else Color.Red //if(a < b) c else d
            when {
                timeLeft < .10 -> Color.Red
                timeLeft < .25 -> Color.Yellow
                else ->
                    Color(0x9999e699)
            }
        )
    }
}

@Composable
fun TimerCountdown(timeLeft: Long) {
    // val time = timeLeft.collectAsState().value.toFloat()

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        /*Text(
            "Timer",
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.size(20.dp))*/
        Text(
            getFormattedStopWatchTime(timeLeft),
            style = MaterialTheme.typography.h4
        )
    }
}
