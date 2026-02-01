package com.example.guesssong.view

import android.media.MediaPlayer
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guesssong.R
import com.example.guesssong.generateAnswerOptions
import com.example.guesssong.model.Song
import com.example.guesssong.playSong
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.ColorFilter

@Composable
fun GuessScreen(
    currentSong: Song,
    currentLevel: Int,
    onCorrectAnswer: () -> Unit,
    onWrongAnswer: () -> Unit,
    onSkipQuestion: () -> Unit,
    onBackPress: () -> Unit
) {
    val context = LocalContext.current
    val customFont2 = FontFamily(Font(R.font.font2, FontWeight.Normal))
    var isPlaying by remember { mutableStateOf(false) }
    var hintUsedGlobally by remember { mutableStateOf(false) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var options by remember(currentSong) { mutableStateOf(generateAnswerOptions(context, currentSong)) }
    var hasSkipped by remember { mutableStateOf(false) }
    var hintUsed by remember { mutableStateOf(false) }
    var extraLifeUsed by remember { mutableStateOf(false) }
    var extraLifeUsedGlobally by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) }

    // Сбрасываем подсказку при смене уровня
    LaunchedEffect(currentLevel) {
        hintUsed = false
    }
    val buttonColors = remember { mutableStateMapOf<String, Color>() }
    // Сброс цветов кнопок в начале нового раунда
    LaunchedEffect(currentSong) {
        buttonColors.clear()
    }
    val transition = rememberInfiniteTransition(label = "")
    val scaleY = List(10) { index ->
        transition.animateFloat(
            initialValue = 0f,
            targetValue = if (isPlaying) 1f else 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 500 + (index * 100), easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Кнопка назад в левом верхнем углу
        IconButton(
            onClick = {
                // Показать окно подтверждения
                showExitDialog = true
            },
            modifier = Modifier
                .padding(16.dp)
                .size(56.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.7f))
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Назад",
                tint = Color(red = 42, green = 65, blue = 116),
                modifier = Modifier.size(30.dp)

            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = "Уровень: $currentLevel",
                fontSize = 25.sp,
                fontFamily = customFont2,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        // Секция с эквалайзером
        if (isPlaying) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp) // Отступ от верхней части экрана, чтобы эквалайзер не перекрывал кнопки
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    scaleY.forEach { scale ->
                        Box(
                            modifier = Modifier
                                .width(15.dp)
                                .height((80 * scale.value).dp) // Умножаем на Float и затем конвертируем в Dp
                                .offset(y = ((80 * scale.value - 80) * (-1)).dp) // Умножаем и конвертируем в Dp
                                .background(Color.White)
                        )
                        Spacer(modifier = Modifier.width(4.dp)) // Расстояние между полосами
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            // Кнопка "Play"
            Button(
                onClick = {
                    if (!isPlaying) {
                        mediaPlayer?.release()
                        mediaPlayer = playSong(context, currentSong.resourceId)
                        isPlaying = true
                        mediaPlayer?.setOnCompletionListener {
                            isPlaying = false
                        }
                    } else {
                        mediaPlayer?.pause()
                        mediaPlayer?.release()
                        mediaPlayer = null
                        isPlaying = false
                    }
                },
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(4.dp, brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF70E9FD), // Начальный цвет градиента
                            Color(0xFF03068F)  // Конечный цвет градиента
                        )
                    ), shape = RoundedCornerShape(360.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Image(
                    painter = if (isPlaying) {
                        painterResource(id = R.drawable.pause)
                    } else {
                        painterResource(id = R.drawable.play)
                    },
                    contentDescription = "Start/stop",
                    modifier = Modifier.size(50.dp),
                    colorFilter = ColorFilter.tint(Color(red = 42, green = 65, blue = 116))
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Кнопка 50/50
                Button(
                    onClick = {
                        if (!hintUsedGlobally) {
                            hintUsedGlobally = true
                            hintUsed = true
                            options = applyHint(options, currentSong)
                        }
                    },
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .border(4.dp, brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF70E9FD), // Начальный цвет градиента
                                Color(0xFF03068F)  // Конечный цвет градиента
                            )
                        ), shape = RoundedCornerShape(360.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = if (hintUsedGlobally) Color(245, 83, 110) else Color(105, 245, 156))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hint),
                        contentDescription = "50/50",
                        modifier = Modifier.size(50.dp),
                        colorFilter = ColorFilter.tint(Color(red = 42, green = 65, blue = 116))
                    )
                }

                Spacer(modifier = Modifier.width(26.dp)) // Расстояние между кнопками

                // Кнопка ошибки
                Button(
                    onClick = {
                        if (!extraLifeUsedGlobally){
                            extraLifeUsed = true
                            extraLifeUsedGlobally = true
                        }
                    },
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .border(4.dp, brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF70E9FD), // Начальный цвет градиента
                                Color(0xFF03068F)  // Конечный цвет градиента
                            )
                        ), shape = RoundedCornerShape(360.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = if (extraLifeUsedGlobally) Color(245, 83, 110) else Color(105, 245, 156))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.heart),
                        contentDescription = "extra life",
                        modifier = Modifier.size(50.dp),
                        colorFilter = ColorFilter.tint(Color(red = 42, green = 65, blue = 116))
                    )
                }

                Spacer(modifier = Modifier.width(26.dp)) // Расстояние между кнопками

                // Вторая кнопка
                Button(
                    onClick = {
                        if (!hasSkipped) {
                            mediaPlayer?.stop()
                            mediaPlayer?.release()
                            mediaPlayer = null
                            isPlaying = false
                            hasSkipped = true
                            onSkipQuestion()
                        }
                    },
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .border(4.dp, brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF70E9FD), // Начальный цвет градиента
                                Color(0xFF03068F)  // Конечный цвет градиента
                            )
                        ), shape = RoundedCornerShape(360.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = if (hasSkipped) Color(245, 83, 110) else Color(105, 245, 156))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.skip), // Подставьте имя ресурса
                        contentDescription = "Пропуск вопроса",
                        modifier = Modifier.size(50.dp),
                        colorFilter = ColorFilter.tint(Color(red = 42, green = 65, blue = 116))
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Размещение вариантов ответов в сетке 2x2
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    options.take(2).forEach { option ->
                        val currentColor = buttonColors[option] ?: Color.White
                        Button(
                            onClick = {
                                // Формируем строку с отображаемым названием текущей песни
                                val correctOption = "${currentSong.artist} - ${currentSong.title}"

                                if (option.trim().lowercase() == correctOption.trim().lowercase()) {
                                    mediaPlayer?.let {
                                        if (it.isPlaying) {
                                            it.stop()
                                        }
                                        it.release()
                                    }
                                    mediaPlayer = null
                                    isPlaying = false
                                    onCorrectAnswer()
                                } else {
                                    if (extraLifeUsed) {
                                        // Обновляем цвет кнопки при неправильном ответе
                                        buttonColors[option] = Color(224, 70, 98) // обновляем состояние
                                        extraLifeUsed = false
                                    } else {
                                        mediaPlayer?.let {
                                            if (it.isPlaying) {
                                                it.stop()
                                            }
                                            it.release()
                                        }
                                        mediaPlayer = null
                                        isPlaying = false
                                        onWrongAnswer()
                                    }
                                }
                            },

                            modifier = Modifier
                                .width(160.dp)
                                .height(120.dp)
                                .padding(12.dp)
                                .border(4.dp, brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF70E9FD), // Начальный цвет градиента
                                        Color(0xFF03068F)  // Конечный цвет градиента
                                    )
                                ), shape = RoundedCornerShape(0.dp)),
                            shape = RoundedCornerShape(0.dp),

                            // Используем текущий цвет из состояния
                            colors = ButtonDefaults.buttonColors(containerColor = currentColor)
                        ) {
                            Text(
                                text = option.replace('_', ' '),
                                color = Color(red = 42, green = 65, blue = 116),
                                fontSize = 18.sp,
                                fontFamily = customFont2,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    options.drop(2).take(2).forEach { option ->
                        // Если цвета нет в map, используем цвет по умолчанию (например, белый)
                        val currentColor = buttonColors[option] ?: Color.White

                        Button(
                            onClick = {
                                // Формируем строку с отображаемым названием текущей песни
                                val correctOption = "${currentSong.artist} - ${currentSong.title}"

                                if (option.trim().lowercase() == correctOption.trim().lowercase()) {
                                    mediaPlayer?.let {
                                        if (it.isPlaying) {
                                            it.stop()
                                        }
                                        it.release()
                                    }
                                    mediaPlayer = null
                                    isPlaying = false
                                    extraLifeUsed = false
                                    onCorrectAnswer()
                                } else {
                                    if (extraLifeUsed) {
                                        // Обновляем цвет кнопки при неправильном ответе
                                        buttonColors[option] = Color(224, 70, 98) // обновляем состояние
                                        extraLifeUsed = false
                                    } else {
                                        mediaPlayer?.let {
                                            if (it.isPlaying) {
                                                it.stop()
                                            }
                                            it.release()
                                        }
                                        mediaPlayer = null
                                        isPlaying = false
                                        onWrongAnswer()
                                    }
                                }
                            },

                            modifier = Modifier
                                .width(160.dp)
                                .height(120.dp)
                                .padding(12.dp)
                                .border(4.dp, brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF70E9FD), // Начальный цвет градиента
                                        Color(0xFF03068F)  // Конечный цвет градиента
                                    )
                                ), shape = RoundedCornerShape(0.dp)),
                            shape = RoundedCornerShape(0.dp),

                            // Используем текущий цвет из состояния
                            colors = ButtonDefaults.buttonColors(containerColor = currentColor)
                        ) {
                            Text(
                                text = option.replace('_', ' '),
                                color = Color(red = 42, green = 65, blue = 116),
                                fontSize = 18.sp,
                                fontFamily = customFont2,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }

    // Окно подтверждения выхода
    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Вы уверены, что хотите выйти в меню?", fontFamily = customFont2) },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (isPlaying) {
                            mediaPlayer?.stop()
                            mediaPlayer?.release()
                            mediaPlayer = null
                            isPlaying = false
                        }
                        showExitDialog = false
                        onBackPress() // Выход из экрана
                    }
                ) {
                    Text("Да", fontFamily = customFont2)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showExitDialog = false }
                ) {
                    Text("Нет", fontFamily = customFont2)
                }
            }
        )
    }
}
fun applyHint(options: List<String>, correctSong: Song): List<String> {
    val correctOption = "${correctSong.artist} - ${correctSong.title}"
    val incorrectOptions = options.filterNot { it.trim().lowercase() == correctOption.trim().lowercase() }

    // Выбираем один случайный неправильный ответ
    val randomIncorrectOption = incorrectOptions.random()

    // Возвращаем правильный ответ и случайный неправильный в случайном порядке
    return listOf(correctOption, randomIncorrectOption).shuffled()
}
