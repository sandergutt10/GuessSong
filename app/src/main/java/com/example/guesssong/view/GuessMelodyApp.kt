package com.example.guesssong.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.guesssong.loadSongs


@Composable
fun GuessMelodyApp(songCount: Int, onBackToMenu: () -> Unit) {
    var score by remember { mutableIntStateOf(0) }
    var currentSongIndex by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val songs = remember { loadSongs(context, songCount) }
    var showResult by remember { mutableStateOf(false) }
    var isLosed by remember { mutableStateOf(false) }
    var hasSkipped by remember { mutableStateOf(false) }

    if (showResult) {
        WinnerScreen(onRestart = onBackToMenu)
    } else if (isLosed) {
        LooserScreen(onRestart = onBackToMenu)
    } else {
        val currentSong = songs[currentSongIndex]
        val currentLevel = currentSongIndex + 1

        GuessScreen(
            currentSong = currentSong,
            currentLevel = currentLevel,
            onCorrectAnswer = {
                score++
                if (currentSongIndex < songs.size - 1) {
                    currentSongIndex++
                } else {
                    showResult = true
                }
            },
            onWrongAnswer = {
                isLosed = true
            },
            onSkipQuestion = {
                if (!hasSkipped) {
                    hasSkipped = true
                    if (currentSongIndex < songs.size - 1) {
                        currentSongIndex++
                    } else {
                        showResult = true
                    }
                }
            },
            onBackPress = {
                onBackToMenu()
            }
        )
    }
}
