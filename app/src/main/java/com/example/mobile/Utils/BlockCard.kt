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
fun putInPlace(offsetY: Dp, index: Int, blocks: MutableList<Block>)
{
    if (offsetY>0.dp)
    {
        for (i in index+1 until blocks.size)
        {
            if (blocks[index].offset <= blocks[i].offset)
            {
                blocks.add(i, blocks[index])
                blocks.removeAt(index)
                println(offsetsY)
                return
            }
        }
        blocks.add(blocks[index])
        blocks.removeAt(index)
    }
    else
    {
        for (i in index - 1 downTo 0)
        {
            if (blocks[index].offset >= blocks[i].offset)
            {
                blocks.add(i, blocks[index])
                blocks.removeAt(index+1)
                return
            }

        }
        blocks.add(0, blocks[index])
        blocks.removeAt(index)
    }
    return
}