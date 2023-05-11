package com.example.mobile

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PrintBlock(
    index: Int,
    blocks:MutableList<Block>
) {
    var text by remember { mutableStateOf("") }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    val localDensity = LocalDensity.current

    blocksToRender[index].serial = index
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
            .onGloballyPositioned { coordinates ->
                offsetsY[index] = with(localDensity) {coordinates.positionInParent().y.toDp()}

            }
            .pointerInput(Unit)
            {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    },
                    onDragEnd = {
                        putInPlace(with(LocalDensity) { offsetY.toDp() }, blocksToRender[index].serial)
                        offsetY = 0f
                        offsetX = 0f
                    }
                )
            },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.LightGray
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Print", modifier = Modifier.padding(14.dp))
                TextField(
                    value = text,
                    onValueChange = { newText ->
                        text = newText
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp)
                        .background(Color.Transparent)
                )
                IconButton(
                    onClick = {
                        print("-------------------------------------------------------\n")
                        println("$index= blockId $text")
                        val deleteBlock =  blocksToRender.find{ it.id == index }
                        println(blocksToRender[deleteBlock!!.id].element.toString())
                        blocksToRender.removeAt(deleteBlock.id)
                        println(blocksToRender.count())

                        blocksToRender.forEachIndexed { index, block ->
                            block.id = index
                        }
                    },
                    modifier = Modifier.padding(end = 8.dp)
                )
                {
                    Icon(Icons.Default.Delete, contentDescription = "delete")
                }
            }
        }
    }
}