package com.example.mobile.Views

import android.content.Context
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
import com.example.mobile.Block
import com.example.mobile.ui.theme.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WelcomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        startActivity(Intent(this@WelcomeScreen, MainActivity::class.java))
                    },
                    modifier = Modifier.border(
                        3.dp,
                        brush = Brush.linearGradient(listOf(assignment_color_1, assignment_color_2)),
                        shape = RoundedCornerShape(50)
                    ),
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
//                        fontFamily = FontFamily(Font(R.font.fedra_sans))
                    )
                }

                Button(
                    onClick = {
                        // Сохранить код по ключу
                        val code = "your_code"
                        val blocks = mutableListOf<Block>() // Cписок блоков для сохранения
                        saveBlockSet(code, blocks)
                    },
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(text = "Сохранить код")
                }

                Button(
                    onClick = {
                        // Загрузить код по ключу
                        val code = "your_code"
                        val blocks = loadBlockSet(code)
                        // Используйте полученные блоки для отображения или других операций
                    },
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(text = "Загрузить код")
                }
            }
        }
    }

    private fun saveBlockSet(code: String, blocks: List<Block>) {
        val sharedPreferences = getSharedPreferences("block_sets_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(blocks)
        editor.putString(code, json)
        editor.apply()
    }

    private fun loadBlockSet(code: String): List<Block> {
        val sharedPreferences = getSharedPreferences("block_sets_data", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(code, null)
        val gson = Gson()
        val itemType = object : TypeToken<List<Block>>() {}.type
        return gson.fromJson(json, itemType) ?: emptyList()
    }
}