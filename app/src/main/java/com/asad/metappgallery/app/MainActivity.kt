package com.asad.metappgallery.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

import com.asad.metappgallery.app.theme.MetAppGalleryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colors = lightThemeColors){
                MetGalleryFinderApp()
            }
        }
    }
}

val lightThemeColors = lightColors(
    primary = Color(0xff365479),
    primaryVariant = Color(0xff2f4858),
    surface = Color(0xff5a5a95),
    background = Color.White,
    secondary = Color(0xffc6509e), // Orange
    secondaryVariant = Color(0xfff64685), // Vibrant Orange
    error = Color.Red,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.White,
)
