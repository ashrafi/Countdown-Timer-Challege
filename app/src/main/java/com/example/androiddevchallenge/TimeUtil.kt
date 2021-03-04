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

import java.util.concurrent.TimeUnit

fun getFormattedStopWatchTime(sec: Long): String {
    var seconds = sec
    // Convert to hours
    val hours = TimeUnit.SECONDS.toHours(seconds)
    seconds -= TimeUnit.HOURS.toSeconds(hours)

    // Convert to minutes
    val minutes = TimeUnit.SECONDS.toMinutes(seconds)
    seconds -= TimeUnit.MINUTES.toSeconds(minutes)

    val time = "${if (hours <10) "0" else ""}$hours:" +
        "${if (minutes < 10) "0" else ""}$minutes:" +
        "${if (seconds < 10) "0" else ""}$seconds"

    return time
}
