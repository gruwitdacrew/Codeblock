package com.example.mobile

import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ui.theme.assignment_color_1
import com.example.mobile.ui.theme.assignment_color_2
import com.example.mobile.ui.theme.init_color_1
import com.example.mobile.ui.theme.init_color_2
import java.util.UUID

@Composable
fun InitBlock(index: UUID, blocks: MutableList<Block>) {
    var key by rememberSaveable { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("") }
    val blockId = blocks.indexOf(blocks.find { it.id == index })

    BlockSample(index = index, blocks = blocks, shape = RoundedCornerShape(50.dp))
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
                            blocks[blockId].expression.value = "iInt;$key"
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
                            blocks[blockId].expression.value = "iString;$key"
                        }
                    ) {
                        Text(text = "String",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.fedra_sans)),
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center)
                    }
                    DropdownMenuItem(
                        onClick = {
                            selectedType = "Bool"
                            expanded = false
                            key = key.trim()
                            blocks[blockId].expression.value = "iBool;$key"
                        }
                    ) {
                        Text("Bool",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.fedra_sans)),
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center)
                    }
                    DropdownMenuItem(
                        onClick = {
                            selectedType = "Array\n<Int>"
                            expanded = false
                            key = key.trim()
                            blocks[blockId].expression.value = "iArray<Int>;$key"
                        }
                    ) {
                        Text("Array\n<Int>",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.fedra_sans)),
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center)
                    }
                    DropdownMenuItem(
                        onClick = {
                            selectedType = "Array\n<String>"
                            expanded = false
                            key = key.trim()
                            blocks[blockId].expression.value = "iArray<String>;$key"
                        }
                    ) {
                        Text("Array\n<String>",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.fedra_sans)),
                            fontSize = 15.sp,

                            textAlign = TextAlign.Center)
                    }
                    DropdownMenuItem(
                        onClick = {
                            selectedType = "Array\n<Bool>"
                            expanded = false
                            key = key.trim()
                            blocks[blockId].expression.value = "iArray<Bool>;$key"
                        }
                    ) {
                        Text("Array\n<Bool>",
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
                blocks[blockId].expression.value = "i$selectedType;$key"
            })

            IconButton(
                onClick = {
                    blocks[blockId].visibleState.targetState = false
                },
                modifier = Modifier
                    .defaultMinSize(minWidth = 60.dp)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "delete", tint = Color.White)
            }
        }
    }
}