package com.ilushakr.composequalifiersmediumapp

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import com.ilushakr.composequalifiersmediumapp.DeviceScreenConfiguration.*

@Composable
fun rememberScreenConfiguration(): DeviceScreenConfiguration {
    val configuration = LocalConfiguration.current

    val screenWidth by remember(key1 = configuration.screenWidthDp) {
        mutableStateOf(configuration.screenWidthDp)
    }
    val screenHeight by remember(key1 = configuration.screenHeightDp) {
        mutableStateOf(configuration.screenHeightDp)
    }

    val screenOrientation by remember(key1 = configuration.orientation) {
        val orientation = when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                DeviceScreenOrientation.Landscape
            }
            else -> {
                DeviceScreenOrientation.Portrait
            }
        }
        mutableStateOf(orientation)
    }

    return DeviceScreenConfiguration(
        size = DeviceScreenSize.getScreenSize(screenHeight, screenWidth),
        orientation = screenOrientation
    )
}

data class DeviceScreenConfiguration(
    val size: DeviceScreenSize,
    val orientation: DeviceScreenOrientation
) {
    val config: Config
        get() = when (orientation) {
            DeviceScreenOrientation.Landscape -> {
                when (size) {
                    DeviceScreenSize.Big -> Config.BigLandscape
                    DeviceScreenSize.Medium -> Config.MediumLandscape
                    DeviceScreenSize.Small -> Config.SmallLandscape
                }
            }
            DeviceScreenOrientation.Portrait -> {
                when (size) {
                    DeviceScreenSize.Big -> Config.BigPortrait
                    DeviceScreenSize.Medium -> Config.MediumPortrait
                    DeviceScreenSize.Small -> Config.SmallPortrait
                }
            }
        }

    val isMobile: Boolean
        get() = this.size == DeviceScreenSize.Small

    val isTablet: Boolean
        get() = !isMobile

    enum class DeviceScreenOrientation {
        Portrait,
        Landscape
    }

    enum class DeviceScreenSize(val screenWidthRange: IntRange) {
        Small(0 until 600),
        Medium(600 until 720),
        Big(720 until Int.MAX_VALUE);

        var actualWidth: Int = -1
            private set

        companion object {
            fun getScreenSize(height: Int, width: Int): DeviceScreenSize {
                val actualWidth = minOf(height, width)

                return values().firstOrNull {
                    it.screenWidthRange.contains(actualWidth)
                }?.apply DeviceScreenSize@{
                    this@DeviceScreenSize.actualWidth = actualWidth
                } ?: throw IllegalScreenSize()

            }

            private const val illegalScreenSizeMessage = "Screen size can not be less than 0"

            class IllegalScreenSize : Throwable(illegalScreenSizeMessage)

        }
    }

    enum class Config {
        SmallPortrait,
        MediumPortrait,
        BigPortrait,
        SmallLandscape,
        MediumLandscape,
        BigLandscape
    }

}