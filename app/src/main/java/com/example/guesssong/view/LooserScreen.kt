package com.example.guesssong.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guesssong.R
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

@Composable
fun LooserScreen(onRestart: () -> Unit) {
    val customFont2 = FontFamily(
        Font(R.font.font2, FontWeight.Normal)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Вы проиграли",
                fontSize = 40.sp,
                fontFamily = customFont2,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = onRestart,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(60.dp)
                    .border(4.dp, brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF70E9FD), // Начальный цвет градиента
                        Color(0xFF03068F)  // Конечный цвет градиента
                    )
                ), shape = RoundedCornerShape(0.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
                Text("Вернуться в меню",
                    color = Color(red = 42, green = 65, blue = 116),
                    fontSize = 30.sp,
                    fontFamily = customFont2,
                    fontWeight = FontWeight.SemiBold)

            }
        }
    }
}
