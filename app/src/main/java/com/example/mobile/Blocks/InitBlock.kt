package com.example.mobile

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import java.util.UUID

@Composable
fun InitBlock(index: UUID, blocks:MutableList<Block>,
) {
    var key by remember { mutableStateOf("") }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val localDensity = LocalDensity.current
    var isDragged = false
    val blockIndex = blocks.find { it.id == index }
    var blockId by remember {
        mutableStateOf(blocks.indexOf(blocks.find { it.id == index }))
    }

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
            .onGloballyPositioned { coordinates ->
                blocks[blockId].offset = with(localDensity) {coordinates.positionInParent().y.toDp()}
            }
            .pointerInput(Unit)
            {
                detectDragGesturesAfterLongPress(
                    onDragStart =
                    {
                        isDragged = true
                    },
                    onDragEnd =
                    {
                        isDragged = false
                        putInPlace(with(localDensity) {offsetY.toDp()}, blockId, blocks)
                        offsetY = 0f
                        offsetX = 0f
                    }
                ) {change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.LightGray
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) { // Используем Row для размещения в одной строке
                TextField(
                    value = key,
                    onValueChange = {
                        key = it.trim() // Удаляем лишние пробелы в начале и конце
                        blocks[blockId].expression.value = "=$key=0"
                    },
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
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}