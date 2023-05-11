package com.example.mobile.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.DrawerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import com.example.mobile.*
import java.util.UUID

fun getExpression(
    ifBlocks:MutableList<Block>,
    elseBlocks:MutableList<Block>,
    condition: String
):String{
    var ifActions = ""; var elseActions = "";
    for(i in ifBlocks){
        ifActions+="${i.expression.value},"
    }
    for(i in elseBlocks){
        elseActions+="${i.expression.value},"
    }
    if(ifActions.length > 0 && elseActions.length>0){
        return "?${ifActions.length}${condition}:${ifActions.substring(0,ifActions.length-1)}:${elseActions.substring(0,elseActions.length-1)}"
    }
    else if(ifActions.length > 0){
        return "?${-1}${condition}:${ifActions.substring(0,ifActions.length-1)}"
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
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var condition by remember { mutableStateOf("") }

    var isDragged = false
    val localDensity = LocalDensity.current

    val blockId = blocks.indexOf(blocks.find { it.id == index })
    for(i in ifBlocksToRender){
        LaunchedEffect(i.expression.value){
            blocks[blockId].expression.value = getExpression(ifBlocksToRender,elseBlocksToRender, condition)
            println(blocks[blockId].expression.value)
        }
    }
    for(i in elseBlocksToRender){
        LaunchedEffect(i.expression.value){
            blocks[blockId].expression.value = getExpression(ifBlocksToRender,elseBlocksToRender, condition)
            println(blocks[blockId].expression.value)        }
    }
    LaunchedEffect(condition){
        blocks[blockId].expression.value = getExpression(ifBlocksToRender,elseBlocksToRender, condition)
        println(blocks[blockId].expression.value)}

    Card(
        modifier = Modifier
            .padding(15.dp)
            .border(2.dp, Color.Black, shape = RoundedCornerShape(5.dp))
            .fillMaxWidth()
            .fillMaxHeight()
            .defaultMinSize( 225.dp, 480.dp)
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
            .onGloballyPositioned { coordinates ->
                com.example.mobile.blocks[blockId].offset = with(localDensity) {coordinates.positionInParent().y.toDp()}
            }
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
                        putInPlace(with(localDensity) {offsetY.toDp()}, blockId, blocks)
                        offsetY = 0f
                        offsetX = 0f
                    }
                ) {change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            },
        backgroundColor = Color.Cyan
    ){
        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ){
                Text(
                    text = "if",
                    fontSize = 25.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                TextField(
                    value = condition,
                    onValueChange = {newText ->
                        condition = newText
                    },
                    textStyle = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .background(Color.Transparent)
                        .weight(2f)
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ifBlocksToRender.forEach{block ->
                    block.element()
                    println("${block.id} ${block.offset}")
                }
                Button(
                    onClick = {
                        blocksToAdd = ifBlocksToRender
                        scope.launch{drawerState.open()}
                    }
                ) {
                    Text(text = "+")
                }
            }
            Text(
                text = "else",
                fontSize = 25.sp,
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                elseBlocksToRender.forEach{block ->
                    block.element()
                    println("${block.id} ${block.offset}")
                }
                Button(onClick = {
                    blocksToAdd = elseBlocksToRender
                    scope.launch{drawerState.open()}
                }) {
                    Text(text = "+")
                }

            }
            IconButton(
                onClick = {
                    handleBlockDelete(index, blocks)
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "delete")
            }
        }
    }
}

