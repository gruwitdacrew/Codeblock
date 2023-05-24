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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
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
)
{
    Card(
        onClick = onClick,
        modifier = Modifier
            .padding(vertical = 20.dp)
            .defaultMinSize(400.dp, 60.dp)
            .fillMaxWidth(0.5f),
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
            Text(
                text = text,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.fedra_sans)),
                fontSize = 30.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

fun putInPlace(offsetY: Dp, blockId: UUID, blocks: MutableList<Block>)
{
    val index = blocks.indexOf(blocks.find { it.id == blockId })
    if (offsetY > 0.dp)
    {
        for (i in index+1 until blocks.size)
        {
            if (blocks[index].offset.value <= blocks[i].offset.value)
            {
                blocks.add(i, blocks[index])
                blocks.removeAt(index)
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
                return
            }
        }
        blocks.add(0, blocks[index])
        blocks.removeAt(index+1)
    }
    return
}