package com.example.mobile.ui.theme


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

@Composable
fun PushBack(view: BlockInformation) {
    var variable by rememberSaveable { mutableStateOf("") }
    var expression by rememberSaveable { mutableStateOf("") }
    val blocks = view.blocks

    var index = blocks.indexOf(blocks.find { it.id == view.id })
    LaunchedEffect(blocks.size) {
        index = blocks.indexOf(blocks.find { it.id == view.id })
    }

    BlockSample(view = view, shape = RoundedCornerShape(50), inside =
    {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(assignment_color_1, assignment_color_2)
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            Text(
                text = "PushBack",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.fedra_sans)),
                fontSize = 26.sp,
                modifier = Modifier
                    .wrapContentWidth(),
                textAlign = TextAlign.Center
            )
            TextFieldSample(modifier = Modifier.weight(3f), onValueChange = { newText ->
                variable = newText
                blocks[index].expression.value = "a$variable;$expression"
            })
            Text(
                text = ".",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.fedra_sans)),
                fontSize = 30.sp,
                modifier = Modifier
                    .width(10.dp),
                textAlign = TextAlign.Center
            )
            TextFieldSample(modifier = Modifier.weight(3f), onValueChange = { newText ->
                expression = newText
                blocks[index].expression.value = "a$variable;$expression"
            })
            IconButton(
                onClick = {
                    blocks[index].visibleState.targetState = false
                },
                modifier = Modifier
                    .defaultMinSize(minWidth = 60.dp)
            )
            {
                Icon(Icons.Default.Delete, contentDescription = "delete", tint = Color.White)
            }
        }
    }
    )
}