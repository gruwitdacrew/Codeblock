package com.example.mobile.Blocks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.mobile.BlockSample
import com.example.mobile.R
import com.example.mobile.TextFieldSample
import com.example.mobile.Utils.BlockInformation
import com.example.mobile.ui.theme.init_color_1
import com.example.mobile.ui.theme.init_color_2

@Composable
fun VoidBlock(view: BlockInformation) {
    var key by rememberSaveable { mutableStateOf("") }
    val blocks = view.blocks

    var index = blocks.indexOf(blocks.find { it.id == view.id })
    LaunchedEffect(blocks.size) {
        index = blocks.indexOf(blocks.find { it.id == view.id })
    }

    BlockSample(view = view, shape = RoundedCornerShape(50.dp))
    {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(init_color_1, init_color_2)
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            Box(
                modifier = Modifier

                    .width(110.dp)
                    .fillMaxHeight()
            )
            {

                Text(
                    text = "VoidFunc",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.fedra_sans)),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .width(130.dp),
                    textAlign = TextAlign.Center
                )

            }
            TextFieldSample(modifier = Modifier.weight(2f), onValueChange = {
                key = it.trim()
                blocks[index].expression.value = "v$key"
            })

            IconButton(
                onClick = {
                    blocks[index].visibleState.targetState = false
                },
                modifier = Modifier
                    .defaultMinSize(minWidth = 60.dp)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "delete", tint = Color.White)
            }
        }
    }
}