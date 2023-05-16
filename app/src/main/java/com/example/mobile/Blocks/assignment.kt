package com.example.mobile
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.UUID

@Composable
fun Assignment(index: UUID, blocks:MutableList<Block>) {
    var variable by rememberSaveable  { mutableStateOf("") }
    var expression by rememberSaveable  { mutableStateOf("") }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }


    var blockId by remember {
        mutableStateOf(blocks.indexOf(blocks.find { it.id == index }))
    }

    val localDensity = LocalDensity.current
    var isDragged = false

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(1f)
            .size(180.dp, 70.dp)
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
//           blocks[blockId].offset.value =
//                    with(localDensity) { coordinates.positionInParent().y.toDp() + coordinates.size.height.toDp() / 2 }
            .pointerInput(Unit)
            {
                detectDragGesturesAfterLongPress(
                    onDragStart =
                    {
                        isDragged = true
                    },
                    onDragEnd =
                    {
                        isDragged = false
                        putInPlace(with(localDensity) { offsetY.toDp() }, index, blocks)
                        offsetY = 0f
                        offsetX = 0f
                        for(i in 0 until blocks.size){
                            println(blocks[i].id)
                        }
                    }
                ) { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.Cyan
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            TextField(
                value = variable,
                onValueChange = { newText ->
                    variable = newText

                    blocks[blockId].expression.value = "=$variable=$expression";
                },
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .weight(1f)
                    .background(Color.Transparent)
            )
            Text(
                text = "=",
                fontSize = 25.sp,
                modifier = Modifier.weight(0.5f),
                textAlign = TextAlign.Center
            )
            TextField(
                value = expression,
                onValueChange = { newText ->
                    expression = newText
                    blocks[blockId].expression.value = "=$variable=$expression";
                },
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .weight(2.5f)
                    .background(Color.Transparent)
            )
            IconButton(
                onClick = {
                    handleBlockDelete(index, blocks)
                },
                modifier = Modifier.padding(start = 8.dp) // Изменяем отступ с помощью start
            ) {
                Icon(Icons.Default.Delete, contentDescription = "delete")
            }
        }
    }
}