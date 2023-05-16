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
import java.util.UUID

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
fun putInPlace(offsetY: Dp, blockId: UUID, blocks: MutableList<Block>)
{
    val index = blocks.indexOf(blocks.find { it.id == blockId })
    println("**********")
    for (j in 0 until blocks.size)
    {
        println(blocks[j].offset.value)
    }
    println("**********")

    if (offsetY>0.dp)
    {
        for (i in index+1 until blocks.size)
        {
            if (blocks[index].offset.value <= blocks[i].offset.value)
            {
                blocks.add(i, blocks[index])
                blocks.removeAt(index)
                println("**********")
                for (j in 0 until blocks.size)
                {
                    println(blocks[j].offset.value)
                }
                println("**********")
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
            if (blocks[index].offset.value >= blocks[i].offset.value)
            {
                blocks.add(i+1, blocks[index])
                blocks.removeAt(index+1)
                println("**********")
                for (j in 0 until blocks.size)
                {
                    println(blocks[j].offset.value)
                }
                println("**********")
                return
            }

        }
        blocks.add(0, blocks[index])
        blocks.removeAt(index+1)
    }
    println("**********")
    for (j in 0 until blocks.size)
    {
        println(blocks[j].offset.value)
    }
    println("**********")
    return
}