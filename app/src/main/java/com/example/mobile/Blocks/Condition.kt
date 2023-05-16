package com.example.mobile.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import com.example.mobile.*
import com.example.mobile.R
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID

fun getIfExpression(
    ifBlocks:MutableList<Block>,
    elseBlocks:MutableList<Block>,
    condition: String
):String{
    var ifActions = mutableListOf<String>(); var elseActions = mutableListOf<String>()
    var indexOfElse = 0;
    for(i in ifBlocks){
        ifActions.add(i.expression.value)
        indexOfElse+=i.expression.value.length
    }
    for(i in elseBlocks){
        elseActions.add(i.expression.value)
    }
    if(ifActions.size > 0 && elseActions.size>0){
        return "?${indexOfElse};${condition}:${Json.encodeToString(ifActions)}:${Json.encodeToString(ifActions)}"
    }
    else if(ifActions.size > 0){
        return "?${-1};${condition}:${Json.encodeToString(ifActions)}"
    }
    else return ""
}

@Composable
fun Condition(
    index: UUID,
    scope: CoroutineScope,
    drawerState: DrawerState,
    blocks:MutableList<Block>
) {
    val ifBlocksToRender: MutableList<Block> = remember { mutableStateListOf() }
    val elseBlocksToRender: MutableList<Block> =  remember { mutableStateListOf() }
    var condition by rememberSaveable { mutableStateOf("") }

    val blockId by remember {
        mutableStateOf(blocks.indexOf(blocks.find { it.id == index }))
    }

    LaunchedEffect(blockId){
        println(index.toString() + " " + blocks.size)
    }

    for(i in ifBlocksToRender){
        LaunchedEffect(i.expression.value){
            blocks[blockId].expression.value = getIfExpression(ifBlocksToRender,elseBlocksToRender, condition)
        }
    }
    for(i in elseBlocksToRender){
        LaunchedEffect(i.expression.value){
            blocks[blockId].expression.value = getIfExpression(ifBlocksToRender,elseBlocksToRender, condition) }
    }


    BlockSample(index = index, blocks = blocks, shape = RoundedCornerShape(5), inside =
    @Composable
    {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(condition_color_1, condition_color_2))
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp, color = Color.Black, shape = RectangleShape
                    )
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    modifier = Modifier
                        .padding(end = 20.dp),
                    text = "if",
                    fontSize = 30.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.fedra_sans)),
                    textAlign = TextAlign.Center
                )
                TextFieldSample(size = 400.dp, onValueChange = {newText ->
                    condition = newText
                    blocks[blockId].expression.value = getIfExpression(ifBlocksToRender,elseBlocksToRender, condition)
                })
//                TextField(
//                    value = condition,
//                    onValueChange = {newText ->
//                        condition = newText
//                    },
//                    textStyle = TextStyle(fontSize = 20.sp),
//                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//                    keyboardActions = KeyboardActions(
//                        onDone = {keyboardController?.hide(); focusManager.clearFocus()}),
//                    modifier = Modifier
//                        .background(Color.Transparent)
//                        .weight(2f)
//                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                ifBlocksToRender.forEach{block ->
                    block.element()
                    println("${block.id} ${block.offset.value}")
                }
                Button(
                    modifier = Modifier
                        .size(60.dp, 35.dp),
                    onClick = {
                        blocksToAdd = ifBlocksToRender
                        scope.launch{drawerState.open()}
                    },
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                )
                {
                    Image(painter = painterResource(id = R.drawable.add), contentDescription = null, contentScale = ContentScale.Fit)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp, color = Color.Black, shape = RectangleShape
                    )
                    .padding(horizontal = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    text = "else",
                    fontSize = 30.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.fedra_sans)),
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                elseBlocksToRender.forEach{block ->
                    block.element()
                    println("${block.id} ${block.offset}")
                }
                Button(
                    modifier = Modifier
                        .size(60.dp, 35.dp),
                    onClick = {
                    blocksToAdd = elseBlocksToRender
                    scope.launch{drawerState.open()}},
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                )
                {
                    Image(painter = painterResource(id = R.drawable.add), contentDescription = null, contentScale = ContentScale.Fit)
                }

            }
            IconButton(
                onClick = {
                    handleBlockDelete(index, blocks)
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "delete", tint = Color.White)
            }
        }
    })
}
