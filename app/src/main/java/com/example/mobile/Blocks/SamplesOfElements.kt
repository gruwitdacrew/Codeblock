package com.example.mobile
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
import java.util.UUID

@SuppressLint("UnrememberedMutableState")
@Composable
fun BlockSample(
    index: UUID,
    blocks:MutableList<Block>,
    shape: Shape,
    inside: @Composable () -> Unit,
)
{
//    var variable by remember { mutableStateOf("") }
//    var expression by remember { mutableStateOf("") }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val blockId = blocks.indexOf(blocks.find { it.id == index } )

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .defaultMinSize(180.dp, 70.dp)
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
            .border(
                width = 2.dp, color = Color.Black, shape = shape
            )
            .onGloballyPositioned { coordinates ->
                blocks[blockId].offset.value =
                    with(localDensity) { coordinates.positionInParent().y.toDp() + coordinates.size.height.toDp() / 2 }
            }
            .pointerInput(Unit)
            {
                detectDragGesturesAfterLongPress(
                    onDragStart =
                    {

                    },
                    onDragEnd =
                    {
                        putInPlace(with(localDensity) { offsetY.toDp() }, index, blocks)
                        offsetY = 0f
                        offsetX = 0f
                    }
                )
                { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            },
        shape = shape
    )
    {
        inside()
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldSample(
    size: Dp,
    onValueChange: (String) -> Unit,
)
{
    val focusManager = LocalFocusManager.current
    var text by remember {
        mutableStateOf("")
    }

    TextField(
        value = text,
        onValueChange = {newText->
            run {
                onValueChange(newText)
                text = newText
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {keyboardController.hide(); focusManager.clearFocus()}),
        singleLine = true,
        modifier = Modifier
            .width(size)
            .background(Color.Transparent)
            .border(
                width = 2.dp, color = Color.Black
            ),
        textStyle = TextStyle(
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.fedra_sans)),
        ),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.White,
        ),
    )
}
