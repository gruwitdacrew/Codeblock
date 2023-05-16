package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ui.theme.assignment_color_1
import com.example.mobile.ui.theme.assignment_color_2
import java.util.*

@Composable
fun Assignment(index: UUID, blocks:MutableList<Block>) {
    var variable by rememberSaveable  { mutableStateOf("") }
    var expression by rememberSaveable  { mutableStateOf("") }
//    var offsetX by remember { mutableStateOf(0f) }
//    var offsetY by remember { mutableStateOf(0f) }

    val blockId = blocks.indexOf(blocks.find { it.id == index })

//    Card(
//        modifier = Modifier
//            .padding(10.dp)
//            .fillMaxWidth()
//            .defaultMinSize(180.dp, 70.dp)
//            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
//            .border(
//                width = 2.dp, color = Color.Black, shape = RoundedCornerShape(50)
//            )
//            .onGloballyPositioned { coordinates ->
//                blocks[blockId].offset.value = with(localDensity) { coordinates.positionInParent().y.toDp() + coordinates.size.height.toDp() / 2 }
//            }
//            .pointerInput(Unit)
//            {
//                detectDragGesturesAfterLongPress(
//                    onDragStart =
//                    {
//
//                    },
//                    onDragEnd =
//                    {
//                        putInPlace(with(localDensity) { offsetY.toDp() }, index, blocks)
//                        offsetY = 0f
//                        offsetX = 0f
//                    }
//                )
//                { change, dragAmount ->
//                    change.consume()
//                    offsetX += dragAmount.x
//                    offsetY += dragAmount.y
//                }
//            },
//        shape = RoundedCornerShape(50)
//    )
    BlockSample(index = index, blocks = blocks, shape = RoundedCornerShape(50), inside =
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
            TextFieldSample(size = 150.dp, onValueChange = { newText ->
                variable = newText
                blocks[blockId].expression.value = "=$variable=$expression"
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
            TextFieldSample(size = 150.dp, onValueChange = { newText ->
                expression = newText
                blocks[blockId].expression.value = "=$variable=$expression";
            })
            IconButton(
                onClick = {
                    handleBlockDelete(index, blocks)
                },
                modifier = Modifier
                    .width(60.dp)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "delete", tint = Color.White)
            }
        }
    }
    )
}
