package dev.antonius.android

import android.os.Build
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/*
 * The code in this file is based on existing work,
 * which can be found [here](https://github.com/android/compose-samples/blob/main/Jetchat/app/src/main/java/com/example/compose/jetchat/theme/Themes.kt#L116)
 * and was obtained under the following license:
 *
 * Copyright 2020 The Android Open Source Project
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

private val PianistDarkColorTheme = darkColorScheme()
private val PianistLightColorTheme = lightColorScheme()

@Composable
fun PianistTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    isDynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val dynamicColor = isDynamicColor
    val myColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && isDarkTheme -> {
            dynamicDarkColorScheme(LocalContext.current)
        }
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !isDarkTheme -> {
            dynamicLightColorScheme(LocalContext.current)
        }
        isDarkTheme -> PianistDarkColorTheme
        else -> PianistLightColorTheme
    }

    MaterialTheme(
        colorScheme = quickFixTertiary(brokenScheme = myColorScheme, isDynamic = dynamicColor, isDarkTheme = isDarkTheme),
        // TODO: typography = ...
    ) {
        // TODO (M3): MaterialTheme doesn't provide LocalIndication, remove when it does
        val rippleIndication = rememberRipple()
        CompositionLocalProvider(
            LocalIndication provides rippleIndication,
            content = content
        )
    }
}


@Deprecated("This is a quick fix, because dynamic color schemes currently do not provide a tertiary color. Remove this once this is fixed.")
@Composable
private fun quickFixTertiary(brokenScheme: ColorScheme, isDynamic: Boolean, isDarkTheme: Boolean) =
    if (isDynamic && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val res = LocalContext.current.resources
        val theme = LocalContext.current.theme

        val (tertiaryId, onTertiaryId) = if (isDarkTheme) {
            // Dark: Tertiary 80, OnTertiary 20
            android.R.color.system_accent3_200 to android.R.color.system_accent3_800
        } else {
            // Light: Tertiary 40, OnTertiary 100
            android.R.color.system_accent3_600 to android.R.color.system_accent3_0
        }

        val tertiary = Color(res.getColor(tertiaryId, theme))
        val onTertiary = Color(res.getColor(onTertiaryId, theme))

        brokenScheme.copy(tertiary = tertiary, onTertiary = onTertiary)
    } else brokenScheme