package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.Utils.BlockInformation
import com.example.mobile.ui.theme.init_color_1
import com.example.mobile.ui.theme.init_color_2
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InitBlock(view: BlockInformation,) {
    var key by rememberSaveable { mutableStateOf("") }
    var dimension by rememberSaveable { mutableStateOf(1) }
    var selectedType by remember { mutableStateOf("") }
    val blocks = view.blocks
    var index = blocks.indexOf(blocks.find { it.id == view.id })

    LaunchedEffect(blocks.size){
        index = blocks.indexOf(blocks.find { it.id == view.id })
    }

    BlockSample(view = view, shape = RoundedCornerShape(50.dp))
    {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(init_color_1, init_color_2)
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            Box(
                modifier = Modifier
                    .background(color = Color.Red)
                    .width(110.dp)
                    .fillMaxHeight()
            )
            {
                var expanded by remember { mutableStateOf(false) }
                DropdownMenu(
                    modifier = Modifier
                        .background(color = Color.Black),
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                )
                {
                    DropdownMenuItem(
                        onClick = {
                            selectedType = "Int"
                            expanded = false
                            key = key.trim()
                            blocks[index].expression.value = "iInt;$key"
                        }
                    )
                    {
                        Text(
                            text = "Int",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.fedra_sans)),
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    DropdownMenuItem(
                        onClick = {
                            selectedType = "String"
                            expanded = false
                            key = key.trim()
                            blocks[index].expression.value = "iString;$key"
                        }
                    ) {
                        Text(
                            text = "String",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.fedra_sans)),
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    DropdownMenuItem(
                        onClick = {
                            selectedType = "Bool"
                            expanded = false
                            key = key.trim()
                            blocks[index].expression.value = "iBool;$key"
                        }
                    ) {
                        Text(
                            "Bool",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.fedra_sans)),
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    DropdownMenuItem(
                        onClick = {
                            selectedType = "Array\n<Int>"
                            expanded = false
                            key = key.trim()
                            blocks[index].expression.value = "i$selectedType^$dimension;$key"
                        }
                    ) {
                        Text(
                            "Array\n<Int>",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.fedra_sans)),
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = dimension.toString(),
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.fedra_sans)),
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center
                        )
                        Button(onClick = {
                            dimension++
                        },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
                        )
                        {
                            Text(text = "+", color = Color.White)
                        }
                        Button(onClick = {
                            if (dimension>1)dimension--
                        },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
                        )
                        {
                            Text(text = "-", color = Color.White)
                        }
                    }
                    DropdownMenuItem(
                        onClick = {
                            selectedType = "Array\n<String>"
                            expanded = false
                            key = key.trim()
                            blocks[index].expression.value = "i$selectedType^$dimension;$key"
                        }
                    ) {
                        Text(
                            "Array\n<String>",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.fedra_sans)),
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = dimension.toString(),
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.fedra_sans)),
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center
                        )
                        Button(onClick = {
                            dimension++
                        },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
                        )
                        {
                            Text(text = "+", color = Color.White)
                        }
                        Button(onClick = {
                            if (dimension>1)dimension--
                        },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
                        )
                        {
                            Text(text = "-", color = Color.White)
                        }
                    }
                    DropdownMenuItem(
                        onClick = {
                            selectedType = "Array\n<Bool>"
                            expanded = false
                            key = key.trim()
                            blocks[index].expression.value = "i$selectedType^$dimension;$key"
                        }
                    ) {
                        Text(
                            "Array\n<Bool>",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.fedra_sans)),
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = dimension.toString(),
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.fedra_sans)),
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center
                        )
                        Button(onClick = {
                            dimension++
                        },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
                        )
                        {
                            Text(text = "+", color = Color.White)
                        }
                        Button(onClick = {
                            if (dimension>1)dimension--
                        },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
                        )
                        {
                            Text(text = "-", color = Color.White)
                        }
                    }
                }
                Button(
                    onClick = { expanded = true },
                    colors = ButtonDefaults.buttonColors(init_color_2),
                    modifier = Modifier
                        .defaultMinSize(minHeight = 70.dp)
                        .fillMaxSize()
                )
                {
                    Text(
                        text = selectedType.ifBlank { "Select\nType" },
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.fedra_sans)),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
            TextFieldSample(modifier = Modifier.weight(2f), onValueChange = {
                key = it.trim()
                blocks[index].expression.value = "i$selectedType^$dimension;$key"
            })

            IconButton(
                onClick = {
                    blocks[index].visibleState.targetState = false
                },
                modifier = Modifier
                    .defaultMinSize(minWidth = 60.dp)
            )
            {
                Icon(Icons.Default.Delete, contentDescription = "delete", tint = Color.White)
            }
        }
    }
}