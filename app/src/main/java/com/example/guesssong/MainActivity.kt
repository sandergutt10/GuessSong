package com.example.guesssong

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.guesssong.ui.theme.GuessSongTheme
import com.example.guesssong.view.MainMenu

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuessSongTheme {
                MainMenu()
            }
        }
    }
}


