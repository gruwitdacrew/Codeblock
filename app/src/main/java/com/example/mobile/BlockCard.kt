package com.example.mobile
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BlockCard(
    text: String,
    color: Color,
    shape: Shape,
    onClick: () -> Unit,
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
    if (offsetY>0.dp)
    {
        for (i in index+1 until offsetsY.size)
        {
            if (offsetsY[index] <= offsetsY[i])
            {
                blocksToRender.add(i, blocksToRender[index])
                blocksToRender.removeAt(index)
                for (j in 0 until blocksToRender.size)
                {
                    println(blocksToRender[j].id)
                    blocksToRender[j].serial = j
                }
                println(offsetsY)
                return
            }
        }
        blocksToRender.add(blocksToRender[index])
        blocksToRender.removeAt(index)
        for (j in 0 until blocksToRender.size)
        {
            println(blocksToRender[j].id)
            blocksToRender[j].serial = j
        }
        println(offsetsY)
    }
    else
    {
        for (i in index - 1 downTo 0)
        {
            if (offsetsY[index] >= offsetsY[i])
            {
                blocksToRender.add(i+1, blocksToRender[index])
                blocksToRender.removeAt(index)
                for (j in 0 until blocksToRender.size)
                {
                    println(blocksToRender[j].id)
                    blocksToRender[j].serial = j
                }
                println(offsetsY)
                return
            }

        }
        blocksToRender.add(0, blocksToRender[index])
        blocksToRender.removeAt(index)
        for (j in 0 until blocksToRender.size)
        {
            println(blocksToRender[j].id)
            blocksToRender[j].serial = j
        }
        println(offsetsY)
    }
    return
}