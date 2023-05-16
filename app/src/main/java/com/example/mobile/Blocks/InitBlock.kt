package com.example.mobile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import java.util.UUID

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InitBlock(index: UUID, blocks: MutableList<Block>) {
    var key by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("") }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val localDensity = LocalDensity.current
    val blockId = blocks.indexOf(blocks.find { it.id == index })

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
            .onGloballyPositioned { coordinates ->
                blocks[blockId].offset.value =
                    with(localDensity) { coordinates.positionInParent().y.toDp() + coordinates.size.height.toDp() / 2 }
            }
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDragEnd = {
                        putInPlace(with(localDensity) { offsetY.toDp() }, index, blocks)
                        offsetY = 0f
                        offsetX = 0f
                    }
                ) { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.LightGray
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .width(80.dp)
            ) {
                var expanded by remember { mutableStateOf(false) }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            selectedType = "Int"
                            expanded = false
                            key = key.trim()
                            blocks[blockId].expression.value = "i;Int;$key"
                        }
                    ) {
                        Text("Int")
                    }
                    DropdownMenuItem(
                        onClick = {
                            selectedType = "String"
                            expanded = false
                            key = key.trim()
                            blocks[blockId].expression.value = "i;String;$key"
                            println("i;$selectedType;$key")
                        }
                    ) {
                        Text("String")
                    }
                    DropdownMenuItem(
                        onClick = {
                            selectedType = "Bool"
                            expanded = false
                            key = key.trim()
                            blocks[blockId].expression.value = "i;Bool;$key"
                        }
                    ) {
                        Text("Bool")
                    }
                    DropdownMenuItem(
                        onClick = {
                            selectedType = "Array<Int>"
                            expanded = false
                            key = key.trim()
                            blocks[blockId].expression.value = "i;Array<Int>;$key"
                        }
                    ) {
                        Text("Array<Int>")
                    }
                    DropdownMenuItem(
                        onClick = {
                            selectedType = "Array<String>"
                            expanded = false
                            key = key.trim()
                            blocks[blockId].expression.value = "i;Array<String>;$key"
                        }
                    ) {
                        Text("Array<String>")
                    }
                    DropdownMenuItem(
                        onClick = {
                            selectedType = "Array<Bool>"
                            expanded = false
                            key = key.trim()
                            blocks[blockId].expression.value = "i;Array<Bool>;$key"
                        }
                    ) {
                        Text("Array<Bool>")
                    }
                }
                    Button(
                        onClick = { expanded = true },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(selectedType.ifBlank { "Select Type" })
                    }
                }
                TextField(
                    value = key,
                    onValueChange = {
//                    key = it.trim()
//                    blocks[blockId].expression.value = "i$key"
                        key = it.trim()
                        blocks[blockId].expression.value = "i;$selectedType;$key"
                        println("i;$selectedType;$key")
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    ),
                    label = { Text("Название переменной") },
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        handleBlockDelete(index, blocks)
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "delete")
                }
            }
        }
    }
