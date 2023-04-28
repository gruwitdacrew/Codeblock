package com.example.mobile

import android.util.DisplayMetrics
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp


val cols = arrayOf(Color.Yellow, Color.Cyan, Color.Red)


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MakeBlock()
{
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var i by remember {
        mutableStateOf(0)
    }
    Card(
        onClick = {
            i++
            println(i)
        },
        modifier = Modifier
            .padding(15.dp)
            .size(100.dp, 60.dp)
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
            .pointerInput(Unit)
            {
                detectDragGestures { change, dragAmount ->
                    if ((offsetX.dp + dragAmount.x.dp < width)&&(offsetX + dragAmount.x > 0)) offsetX += dragAmount.x
                    if ((offsetY.dp + dragAmount.y.dp < height - 10.dp)&&(offsetY + dragAmount.y > 0)) offsetY += dragAmount.y
                }
            },
        backgroundColor = cols[i%3]
    )
    {

    }
}