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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun ProgressDial(
    timeLeft: Float
) {
    // val progressTime =
    Log.v(TAG, "progress bar $timeLeft")
    LocalDensity.current.density
    Box(
        modifier = Modifier.size(430.dp),
       /*.constrainAs(timer) {
       top.linkTo(parent.top)
       bottom.linkTo(parent.bottom)
       start.linkTo(parent.start)
       end.linkTo(parent.end),*/
        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator(
            progress = 1.0F,
            strokeWidth = 25.dp,
            modifier = Modifier
                .padding(16.dp)
                .background(color = Color.Transparent)
                .fillMaxSize(),
            color = Color(
                0x77d1d1e0,
            )
        )
        CircularProgressIndicator(
            progress = timeLeft, // 0.0 to 1.0
            strokeWidth = 50.dp,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(color = Color.Transparent),
            color = // if (timeDisplay > .25) Color.Green else Color.Red //if(a < b) c else d
            when {
                timeLeft < .10 -> Color(0x99EE5951)
                timeLeft < .25 -> Color(0x99EEEEBE)
                else ->
                    Color(0x9999e699)
            }
        )
    }
}
