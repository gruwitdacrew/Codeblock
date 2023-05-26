package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ui.theme.print_color_1
import com.example.mobile.ui.theme.print_color_2
import java.util.UUID


@Composable
fun PrintBlock(
    index: UUID,
    blocks: MutableList<Block>
) {
    var text by rememberSaveable { mutableStateOf("") }

    val blockId = blocks.indexOf(blocks.find { it.id == index })

    BlockSample(index = index, blocks = blocks, shape = RoundedCornerShape(50), inside =
    {
        Row(
            modifier = Modifier.background(
                brush = Brush.linearGradient(
                    colors = listOf(print_color_1, print_color_2)
                )
            ),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                "Print",
                modifier = Modifier
                    .padding(14.dp)
                    .width(60.dp),
                fontSize = 20.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.fedra_sans)),
                textAlign = TextAlign.Center
            )

            TextFieldSample(
                modifier = Modifier.weight(2f),
                onValueChange = { newText ->
                    text = newText
                    blocks[blockId].expression.value = "/$text"
                    println(blocks[blockId].expression.value)
                },
            )
            IconButton(
                onClick = {
                    blocks[blockId].visibleState.targetState = false
                },
                modifier = Modifier
                    .defaultMinSize(minWidth = 60.dp)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "delete", tint = Color.White)
            }
        }
    })
}