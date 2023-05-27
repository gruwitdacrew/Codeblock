package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.Utils.BlockInformation
import com.example.mobile.ui.theme.init_color_1
import com.example.mobile.ui.theme.init_color_2

@Composable
fun InitBlock(view: BlockInformation) {
    var key by rememberSaveable { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("") }
    val blocks = view.blocks
    var index = blocks.indexOf(blocks.find { it.id == view.id })

    LaunchedEffect(blocks.size) {
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
                    ) {
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
                            blocks[index].expression.value = "iArray<Int>;$key"
                        }
                    ) {
                        Text(
                            "Array\n<Int>",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.fedra_sans)),
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    DropdownMenuItem(
                        onClick = {
                            selectedType = "Array\n<String>"
                            expanded = false
                            key = key.trim()
                            blocks[index].expression.value = "iArray<String>;$key"
                        }
                    ) {
                        Text(
                            "Array\n<String>",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.fedra_sans)),
                            fontSize = 15.sp,

                            textAlign = TextAlign.Center
                        )
                    }
                    DropdownMenuItem(
                        onClick = {
                            selectedType = "Array\n<Bool>"
                            expanded = false
                            key = key.trim()
                            blocks[index].expression.value = "iArray<Bool>;$key"
                        }
                    ) {
                        Text(
                            "Array\n<Bool>",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.fedra_sans)),
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )
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
                blocks[index].expression.value = "i$selectedType;$key"
            })

            IconButton(
                onClick = {
                    blocks[index].visibleState.targetState = false
                },
                modifier = Modifier
                    .defaultMinSize(minWidth = 60.dp)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "delete", tint = Color.White)
            }
        }
    }
}