package com.example.mobile.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.DrawerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import com.example.mobile.*
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
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var condition by rememberSaveable { mutableStateOf("") }

    var isDragged = false
    val localDensity = LocalDensity.current

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

    Card(
        modifier = Modifier
            .padding(15.dp)
            .border(2.dp, Color.Black, shape = RoundedCornerShape(5.dp))
            .fillMaxWidth()
            .fillMaxHeight()
            .defaultMinSize(225.dp, 480.dp)
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
//            .onGloballyPositioned { coordinates ->
////                if(blockId != -1){
////                    blocks[blockId].offset =
////                        with(localDensity) { coordinates.positionInParent().y.toDp() }
////                }
//            }
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
                    }
                ) { change, dragAmount ->
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
                        blocks[blockId].expression.value = getIfExpression(ifBlocksToRender,elseBlocksToRender, condition)
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
                }
                Button(
                    onClick = {
                        blocksToAdd = ifBlocksToRender
                        scope.launch{drawerState.open()}
                        println(blocks)
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
                    println(ifBlocksToRender)
                    println(elseBlocksToRender)
                    handleBlockDelete(index, blocks)
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "delete")
            }
        }
    }
}

