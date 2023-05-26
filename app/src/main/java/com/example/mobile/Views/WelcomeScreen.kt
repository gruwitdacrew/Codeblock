package com.example.mobile.Views

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ui.theme.*

class WelcomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Button(
                    onClick = {
                        startActivity(Intent(this@WelcomeScreen, MainActivity::class.java))
                    },
                    modifier = Modifier.border(3.dp,
                        brush = Brush.linearGradient(listOf(assignment_color_1, assignment_color_2)),
                        shape = RoundedCornerShape(50)),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = bottom_bar_color,
                    ),
                    shape = RoundedCornerShape(50)
                )
                {
                    Text(
                        text = "+ iBlockFile",
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        color = Color.White,
                        fontFamily = FontFamily(Font(com.example.mobile.R.font.fedra_sans))
                    )
                }
            }
        }
    }
}