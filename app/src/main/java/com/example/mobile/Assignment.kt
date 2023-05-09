package com.example.mobile
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Assignment(index: Int, blocks:MutableList<Block>)
{
    var variable by remember { mutableStateOf("") }
    var expression by remember { mutableStateOf("") }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    val finalIndex = index.toString()
    Card(
        modifier = Modifier
            .padding(15.dp)
            .size(280.dp, 70.dp)
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
            .pointerInput(Unit)
            {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    if ((offsetX.dp + dragAmount.x.dp + 225.dp < width) && (offsetX + dragAmount.x > 0)) offsetX += dragAmount.x
                    if ((offsetY.dp + dragAmount.y.dp < height - 10.dp) && (offsetY + dragAmount.y > 0)) offsetY += dragAmount.y
                }
            },
        backgroundColor = Color.Cyan
    )
    {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            TextField(
                value = variable,
                onValueChange = {newText ->
                    println(blocks.size)
                    variable = newText;
                    blocks[index].expression.value = "=$variable=$expression";
                },
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .background(Color.Red)
                    .weight(1f)
            )
            Text(
                text = "=",
                fontSize = 25.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            );
            TextField(
                value = expression,
                onValueChange = {newText ->
                    expression = newText
                    blocks[index].expression.value = "=$variable=$expression";
                },
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .background(Color.Yellow)
                    .weight(2f)
            )
        }
    }
}