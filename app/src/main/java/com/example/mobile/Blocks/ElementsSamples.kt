package com.example.mobile

import android.annotation.SuppressLint
import androidx.compose.animation.*
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.Utils.BlockInformation
import java.util.*

@SuppressLint("UnrememberedMutableState", "SuspiciousIndentation")
@Composable
fun BlockSample(
    view: BlockInformation,
    shape: Shape,
    inside: @Composable () -> Unit,
) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    val localFocusManager = LocalFocusManager.current
    var elevation by remember { mutableStateOf(0f) }
    var index = blocks.indexOf(blocks.find { it.id == view.id })

    LaunchedEffect(blocks.size){
        index = blocks.indexOf(blocks.find { it.id == view.id })
    }

    Card(
        modifier = Modifier
            .background(color = if (blocks[index].onDebug.value) Color(0x8AFF3014) else Color.Transparent)
            .padding(10.dp)
            .defaultMinSize(400.dp, 70.dp)
            .fillMaxWidth(0.5f)
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
            .border(
                width = 2.dp, color = Color.Black, shape = shape
            )
            .onGloballyPositioned { coordinates ->
                blocks[index].offset.value =
                    with(localDensity) { coordinates.positionInWindow().y.toDp() + coordinates.size.height.toDp() / 2 }
            }
            .shadow(elevation = elevation.dp, spotColor = Color.Black, shape = shape)
            .pointerInput(Unit)
            {
                detectDragGesturesAfterLongPress(
                    onDragStart =
                    {
                        localFocusManager.clearFocus()
                        alpha.value = 0.7f
                        elevation = 20f
                    },
                    onDragEnd =
                    {
                        alpha.value = 1f
                        elevation = 1f
                        putInPlace(with(localDensity) { offsetY.toDp() }, view.id, blocks)
                        offsetY = 0f
                        offsetX = 0f
                    },
                    onDragCancel =
                    {
                        alpha.value = 1f
                        elevation = 1f
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
//private fun String.letters() = filter { it.isLetter() }

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldSample(
    modifier: Modifier,
    onValueChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var text by remember {
        mutableStateOf("")
    }

    TextField(
        value = text,
        onValueChange = { newText ->
            run {
                onValueChange(newText)
                if (newText.matches(Regex("[a-zA-z0-9.,+\\-/*<>=!()]*"))) {
                    text = newText
                }
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController.hide(); focusManager.clearFocus() }),
        singleLine = true,
        modifier = modifier
//            .width(size)
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
