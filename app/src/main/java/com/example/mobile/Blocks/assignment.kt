package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.Utils.BlockInformation
import com.example.mobile.ui.theme.assignment_color_1
import com.example.mobile.ui.theme.assignment_color_2
import java.util.*

@Composable
fun Assignment(view: BlockInformation,)
{
    val blocks = view.blocks
    var variable by rememberSaveable { mutableStateOf("") }
    var expression by rememberSaveable { mutableStateOf("") }

    var index = blocks.indexOf(blocks.find { it.id == view.id })
    LaunchedEffect(blocks.size){
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
            TextFieldSample(modifier = Modifier.weight(2f), onValueChange = { newText ->
                variable = newText
                blocks[index].expression.value = "=$variable=$expression"
            })
            Text(
                text = "=",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.fedra_sans)),
                fontSize = 30.sp,
                modifier = Modifier
                    .width(40.dp),
                textAlign = TextAlign.Center
            )
            TextFieldSample(modifier = Modifier.weight(2f), onValueChange = { newText ->
                expression = newText
                blocks[index].expression.value = "=$variable=$expression"
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
