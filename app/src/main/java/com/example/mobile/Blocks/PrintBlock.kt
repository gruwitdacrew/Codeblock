package com.example.mobile
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import java.util.UUID


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PrintBlock(
    index: UUID,
    blocks: MutableList<Block>
) {
    var text by remember { mutableStateOf("") }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val blockId = blocks.indexOf(blocks.find { it.id == index })
    val localDensity = LocalDensity.current

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
            .onGloballyPositioned { coordinates ->
                blocks[blockId].offset.value = with(localDensity) { coordinates.positionInParent().y.toDp() + coordinates.size.height.toDp()/2 }
            }
            .pointerInput(Unit)
            {
                detectDragGesturesAfterLongPress(
                    onDragEnd =
                    {
                        putInPlace(with(localDensity) { offsetY.toDp() }, index, blocks)
                        offsetY = 0f
                        offsetX = 0f
                    }
                ) { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.LightGray
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Print", modifier = Modifier.padding(14.dp))
                TextField(
                    value = text,
                    onValueChange = { newText ->
                        text = newText
                        blocks[blockId].expression.value = "/$text"
                        println(blocks[blockId].expression.value)
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {keyboardController?.hide(); focusManager.clearFocus()}),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp)
                        .background(Color.Transparent)
                )
                IconButton(
                    onClick = {
                        handleBlockDelete(index, blocks)
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "delete")
                }
            }
        }
    }
}