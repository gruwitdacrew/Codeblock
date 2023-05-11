package com.example.mobile
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BlockCard(
    text: String,
    color: Color,
    shape: Shape,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth(),
        backgroundColor = color,
        elevation = 5.dp,
        shape = shape
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        )
        {
            Text(text = text, color = Color(0xFFFFFFFF), fontSize = 24.sp)
        }
    }
}
fun putInPlace(offsetY: Dp, index: Int)
{
    println(blocksToRender)
    if (offsetY.value>0)
    {
        for (i in index+1 until offsetsY.size)
        {
            if (offsetsY[index] + offsetY.value <= offsetsY[i])
            {
                blocksToRender = arrayListOf(
                    *blocksToRender.slice(0 until index).toTypedArray(),
                    *blocksToRender.slice(index+1 until i).toTypedArray(),
                    blocksToRender[index],
                    *blocksToRender.slice(i until blocksToRender.size).toTypedArray())
                println(blocksToRender)
                return
            }
        }
        blocksToRender = arrayListOf(
            *blocksToRender.slice(0 until index).toTypedArray(),
            *blocksToRender.slice(index+1 until blocksToRender.size).toTypedArray(),
            blocksToRender[index])
        println(blocksToRender)
    }
    else
    {
        for (i in index - 1 downTo 0)
        {
            if (offsetsY[index] + offsetY.value >= offsetsY[i])
            {
                blocksToRender = arrayListOf(
                    *blocksToRender.slice(0 until i+1).toTypedArray(),
                    blocksToRender[index],
                    *blocksToRender.slice(i+1 until index).toTypedArray(),
                    *blocksToRender.slice(index+1 until blocksToRender.size).toTypedArray())
                println(blocksToRender)
                return
            }

        }
        blocksToRender = arrayListOf(
            blocksToRender[index],
            *blocksToRender.slice(0 until index).toTypedArray(),
            *blocksToRender.slice(index+1 until blocksToRender.size).toTypedArray())
        println(blocksToRender)
    }
    return
}