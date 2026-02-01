package com.example.guesssong.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import com.example.guesssong.R
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

@Composable
fun MainMenu() {
    var showDialog by remember { mutableStateOf(false) }
    val customFont = FontFamily(
        Font(R.font.font, FontWeight.Normal)
    )
    val customFont2 = FontFamily(
        Font(R.font.font2, FontWeight.Normal)
    )
    var songCount by remember { mutableIntStateOf(5) }
    var startGame by remember { mutableStateOf(false) }
    var selectedLevel by remember { mutableStateOf("Средний") }
    var dropdownExpanded by remember { mutableStateOf(false) }

    if (startGame) {
        GuessMelodyApp(songCount) { startGame = false }
    } else {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 150.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Guess Song",
                    fontSize = 45.sp, // Увеличенный размер
                    fontWeight = FontWeight.Normal,
                    fontFamily = customFont,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF07DAFF),
                                Color.White,     // Начало градиента — белый
                                // Конец градиента — неоновый розовый
                            )
                        )
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .wrapContentHeight() // Предотвращает обрезку текста по высоте
                        .graphicsLayer {
                            shadowElevation = 30f // Эффект свечения
                            shape = CircleShape // Форма света
                            clip = false // Отключить обрезку
                        }
                )

                Spacer(modifier = Modifier.height(125.dp))

                Button(
                    onClick = { startGame = true },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .height(60.dp)
                        .border(4.dp, brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF70E9FD), // Начальный цвет градиента
                                Color(0xFF03068F)  // Конечный цвет градиента
                            )
                        ), shape = RoundedCornerShape(12.dp)),

                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(
                        text = "Начать игру",
                        fontSize = 30.sp,
                        fontFamily = customFont2,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(red = 42, green = 65, blue = 116)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Уровень сложности:",
                        fontSize = 25.sp,
                        fontFamily = customFont2,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Box {
                        Button(
                            onClick = { dropdownExpanded = !dropdownExpanded },
                            modifier = Modifier
                                .padding(vertical = 0.dp)
                                .width(200.dp)
                                .height(50.dp)
                                .border(4.dp, brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF70E9FD), // Начальный цвет градиента
                                        Color(0xFF03068F)  // Конечный цвет градиента
                                    )
                                ), shape = RoundedCornerShape(8.dp)),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                        ) {
                            Text(
                                text = selectedLevel,
                                fontSize = 24.sp,
                                fontFamily = customFont2,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(red = 42, green = 65, blue = 116)
                            )
                        }

                        DropdownMenu(
                            expanded = dropdownExpanded,
                            onDismissRequest = { dropdownExpanded = false },
                            modifier = Modifier
                                .background(color = Color.White)
                                .width(200.dp)
                                .border(4.dp, brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF70E9FD), // Начальный цвет градиента
                                        Color(0xFF03068F)  // Конечный цвет градиента
                                    )
                                ), shape = RoundedCornerShape(0.dp))
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Лёгкий",
                                        color = Color(red = 42, green = 65, blue = 116),
                                        textAlign = TextAlign.Center,
                                        fontFamily = customFont2,
                                        fontSize = 22.sp,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                onClick = {
                                    songCount = 3
                                    selectedLevel = "Лёгкий"
                                    dropdownExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Средний",
                                        color = Color(red = 42, green = 65, blue = 116),
                                        textAlign = TextAlign.Center,
                                        fontFamily = customFont2,
                                        fontSize = 22.sp,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                onClick = {
                                    songCount = 5
                                    selectedLevel = "Средний"
                                    dropdownExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Сложный",
                                        color = Color(red = 42, green = 65, blue = 116),
                                        textAlign = TextAlign.Center,
                                        fontFamily = customFont2,
                                        fontSize = 22.sp,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                onClick = {
                                    songCount = 10
                                    selectedLevel = "Сложный"
                                    dropdownExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Невозможный",
                                        color = Color(red = 42, green = 65, blue = 116),
                                        textAlign = TextAlign.Center,
                                        fontFamily = customFont2,
                                        fontSize = 22.sp,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                onClick = {
                                    songCount = 20
                                    selectedLevel = "Невозможный"
                                    dropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            // Кнопка в правом нижнем углу
            Button(
                onClick = { showDialog = true },
                modifier = Modifier
                    .padding(16.dp)
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.7f))
                    .align(Alignment.BottomEnd)
                    .border(4.dp, brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF70E9FD), // Начальный цвет градиента
                            Color(0xFF03068F)  // Конечный цвет градиента
                        )
                    ), shape = CircleShape),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp), // Удаление отступов
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.help),
                    contentDescription = "Help",
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(Color(red = 42, green = 65, blue = 116))
                )
            }
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false }, // Закрытие диалога
                    title = {
                        Text(
                            text = "Информация об игре",
                            fontSize = 25.sp,
                            fontFamily = customFont2,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    },
                    text = {
                        Text(
                            text = "Это игра, где вы должны угадывать названия песен по небольшому фрагменту. Есть 4 уровня сложности - лёгкий, где для победы нужно угадать 3 песни, средний - 5 песен, сложный - 10 песен и невозможный - 20 песен. Для помощь Вам будет предложено 3 подсказки - пропуск вопроса, право на ошибку и 50/50, где убираются 2 неверных ответа. Подсказки можно использовать только 1 раз. Удачи!",
                            fontSize = 15.sp,
                            fontFamily = customFont2,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text(
                                text = "Закрыть",
                                fontSize = 15.sp,
                                fontFamily = customFont2,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,
                            )
                        }
                    }
                )
            }
        }
    }
}
