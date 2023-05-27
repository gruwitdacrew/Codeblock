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
                            text = "Start",
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
//class WelcomeScreen : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            Column(
//                modifier = Modifier.fillMaxSize(),
//
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Button(
//                    onClick = {
//                        startActivity(Intent(this@WelcomeScreen, MainActivity::class.java))
//                    },
//                    modifier = Modifier.border(
//                        3.dp,
//                        brush = Brush.linearGradient(listOf(assignment_color_1, assignment_color_2)),
//                        shape = RoundedCornerShape(50)
//                    ),
//                    colors = ButtonDefaults.buttonColors(
//                        backgroundColor = bottom_bar_color,
//                    ),
//                    shape = RoundedCornerShape(50)
//                ) {
//                    Text(
//                        text = "+ iBlockFile",
//                        textAlign = TextAlign.Center,
//                        fontSize = 30.sp,
//                        color = Color.White,
////                        fontFamily = FontFamily(Font(R.font.fedra_sans))
//                    )
//                }
//
////                Button(
////                    onClick = {
////                        // Сохранить код по ключу
////                        val code = "your_code"
////                        val blocks = mutableListOf<Block>() // Cписок блоков для сохранения
////                        saveBlockSet(code, blocks)
////                    },
////                    modifier = Modifier.padding(16.dp),
////                ) {
////                    Text(text = "Сохранить код")
////                }
//
////                Button(
////                    onClick = {
////                        // Загрузить код по ключу
////                        val code = "your_code"
////                        val blocks = loadBlockSet(code)
////                        // Используйте полученные блоки для отображения или других операций
////                    },
////                    modifier = Modifier.padding(16.dp),
////                ) {
////                    Text(text = "Загрузить код")
////                }
//            }
//        }
//    }

//    private fun saveBlockSet(code: String, blocks: List<Block>) {
//        val sharedPreferences = getSharedPreferences("block_sets_data", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        val gson = Gson()
//        val json = gson.toJson(blocks)
//        editor.putString(code, json)
//        editor.apply()
//    }
//
//    private fun loadBlockSet(code: String): List<Block> {
//        val sharedPreferences = getSharedPreferences("block_sets_data", Context.MODE_PRIVATE)
//        val json = sharedPreferences.getString(code, null)
//        val gson = Gson()
//        val itemType = object : TypeToken<List<Block>>() {}.type
//        return gson.fromJson(json, itemType) ?: emptyList()
//    }
//}
//package com.example.mobile.Views
//
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.Button
//import androidx.compose.material.ButtonColors
//import androidx.compose.material.ButtonDefaults
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.material.Text
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.mobile.Block
//import com.example.mobile.blocksToRender
//import com.example.mobile.ui.theme.*
//
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//data class Block(val id: String, val content: String)
//
//class MainActivity : ComponentActivity() {
//    val context = LocalContext.current
//    val sharedPreferences = remember { context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE) }
//    val editor = remember { sharedPreferences.edit() }
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        sharedPrefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
//
//        setContent {
//            SharedPreferencesAppTheme {
//                val context = LocalContext.current
//                var blocks by remember { mutableStateOf(emptyList<Block>()) }
//
//                Column {
//                    Button(
//                        onClick = {
//                            // Сохранение списка блоков
//                            val block1 = Block("1", "Block 1")
//                            val block2 = Block("2", "Block 2")
//                            val block3 = Block("3", "Block 3")
//                            blocks = listOf(block1, block2, block3)
//                            saveBlocks(context, blocks)
//                        }
//                    ) {
//                        Text(text = "Сохранить")
//                    }
//
//                    Button(
//                        onClick = {
//                            // Загрузка списка блоков
//                            blocks = loadBlocks(context)
//                        }
//                    ) {
//                        Text(text = "Загрузить")
//                    }
//
//                    // Отображение списка блоков
//                    for (block in blocks) {
//                        Text(text = block.content)
//                    }
//                }
//            }
//        }
//    }
//
//    private fun saveBlocks(context: Context, blocks: List<Block>) {
//        val json = blocks.joinToString(",") { "{\"id\":\"${it.id}\",\"content\":\"${it.content}\"}" }
//        sharedPrefs.edit().putString("blocks", json).apply()
//    }
//
//    private fun loadBlocks(context: Context): List<Any> {
//        val json = sharedPrefs.getString("blocks", "") ?: ""
//        return if (json.isNotBlank()) {
//            json.split(",").map {
//                val parts = it.split(":")
//                val id = parts[1].substringAfter("\"").substringBefore("\"")
//                val content = parts[3].substringAfter("\"").substringBefore("\"")
//                Block(id, content)
//            }
//        } else {
//            emptyList()
//        }
//    }
//}
//
//
