package com.ilushakr.composequalifiersmediumapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ilushakr.composequalifiersmediumapp.ui.theme.ComposeQualifiersMediumAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeQualifiersMediumAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting(rememberScreenConfiguration())
                }
            }
        }
    }
}

@Composable
fun Greeting(configuration: DeviceScreenConfiguration) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "My config is ${configuration.config.name} \n" +
                    "My screen range is ${configuration.size.screenWidthRange} \n" +
                    "My width is ${configuration.size.actualWidth} \n" +
                    "Is my device tablet: ${configuration.isTablet}"
        )
    }
}
