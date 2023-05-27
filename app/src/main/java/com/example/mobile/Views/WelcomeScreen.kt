package com.example.mobile.Views

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ui.theme.MobileTheme
import com.example.mobile.ui.theme.assignment_color_1
import com.example.mobile.ui.theme.assignment_color_2
import com.example.mobile.ui.theme.bottom_bar_color


class WelcomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MobileTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(vertical = 200.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        modifier = Modifier
                            .width(320.dp)
                            .height(180.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .padding(16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 30.dp)
                        ) {
                            Image(
                                painter = painterResource(id = com.example.mobile.R.drawable.puzzle_piece),
                                contentDescription = "Logo",
                                modifier = Modifier.size(76.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "IBlocks",
                                fontSize = 38.sp,
                                color = Color.Black
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            startActivity(Intent(this@WelcomeScreen, MainActivity::class.java))
                        },
                        modifier = Modifier
                            .padding(top = 80.dp)
                            .border(
                                width = 3.dp,
                                brush = Brush.linearGradient(
                                    listOf(
                                        assignment_color_1,
                                        assignment_color_2
                                    )
                                ),
                                shape = RoundedCornerShape(50)
                            )
                            .padding(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = bottom_bar_color,
                        ),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(
                            text = "+ iBlockFile",
                            textAlign = TextAlign.Center,
                            fontSize = 30.sp,
                            color = Color.White,
                        )
                    }
                }
            }
        }
    }
}
