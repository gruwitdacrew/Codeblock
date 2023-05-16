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

fun getForExpression(
    forBlocks:MutableList<Block>,
    condition: String,
    variable: String,
    action: String,
    value:String,
):String{
    var actions = mutableListOf<String>()
    for(i in forBlocks){
        actions.add(i.expression.value)
    }
    if(actions.size > 0) return "f=${variable}=${value};${condition};=${variable}=${action}:${Json.encodeToString(actions)}"
    else return "";
}

@Composable
fun For(
    index: UUID,
    scope: CoroutineScope,
    drawerState: DrawerState,
    blocks:MutableList<Block>
) {
    val forBlocksToRender: MutableList<Block> = remember { mutableStateListOf() }

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var variable by rememberSaveable { mutableStateOf("") }
    var value by rememberSaveable { mutableStateOf("") }
    var condition by rememberSaveable { mutableStateOf("") }
    var action by rememberSaveable { mutableStateOf("") }

    var isDragged = false
    val localDensity = LocalDensity.current

    val blockId = remember {
        blocks.indexOf(blocks.find { it.id == index })
    }
    for(i in forBlocksToRender){
        LaunchedEffect(i.expression.value){
            blocks[blockId].expression.value = getForExpression(forBlocksToRender, condition,variable,action,value)
            println(blockId)
        }
    }
    //wa>0:wab>0;action;action;action;action
    //f=i=0;i<5;=i=i+1:["f=j=0;j<5;=j=j+1:[\"/i*j\"]"]
    Card(
        modifier = Modifier
            .padding(15.dp)
            .border(2.dp, Color.Black, shape = RoundedCornerShape(5.dp))
            .fillMaxWidth()
            .fillMaxHeight()
            .defaultMinSize(225.dp, 480.dp)
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
//            .onGloballyPositioned { coordinates ->
////                blocks[blockId].offset = with(localDensity) {coordinates.positionInParent().y.toDp()}
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
                    text = "For",
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                TextField(
                    value = variable,
                    onValueChange = {newText ->
                        variable = newText
                        blocks[blockId].expression.value = getForExpression(forBlocksToRender, condition,variable,action,value)
                        println(blockId)
                    },
                    textStyle = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .background(Color.Transparent)
                        .weight(1f)
                )
                Text(
                    text = "=",
                    fontSize = 20.sp,
                    modifier = Modifier.weight(0.5f),
                    textAlign = TextAlign.Center
                )
                TextField(
                    value = value,
                    onValueChange = {newText ->
                        value = newText
                        blocks[blockId].expression.value = getForExpression(forBlocksToRender, condition,variable,action,value)
                        println(blockId)
                    },
                    textStyle = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .background(Color.Transparent)
                        .weight(1f)
                )
                Text(
                    text = ";",
                    fontSize = 20.sp,
                    modifier = Modifier.weight(0.5f),
                    textAlign = TextAlign.Center
                )
                TextField(
                    value = condition,
                    onValueChange = {newText ->
                        condition = newText
                        blocks[blockId].expression.value = getForExpression(forBlocksToRender, condition,variable,action,value)
                        println(blockId)
                    },
                    textStyle = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .background(Color.Transparent)
                        .weight(2f)
                )
                Text(
                    text = ";",
                    fontSize = 20.sp,
                    modifier = Modifier.weight(0.5f),
                    textAlign = TextAlign.Center
                )
                TextField(
                    value = variable,
                    onValueChange = {newText ->
                        variable = newText
                        blocks[blockId].expression.value = getForExpression(forBlocksToRender, condition,variable,action,value)
                        println(blockId)
                    },
                    textStyle = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .background(Color.Transparent)
                        .weight(1f)
                )
                Text(
                    text = "=",
                    fontSize = 20.sp,
                    modifier = Modifier.weight(0.5f),
                    textAlign = TextAlign.Center
                )
                TextField(
                    action,
                    onValueChange = {newText ->
                        action = newText
                        blocks[blockId].expression.value = getForExpression(forBlocksToRender, condition,variable,action,value)
                        println(blockId)
                    },
                    textStyle = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .background(Color.Transparent)
                        .weight(1f)
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                forBlocksToRender.forEach{block ->
                    block.element()
                }
                Button(
                    onClick = {
                        blocksToAdd = forBlocksToRender
                        scope.launch{drawerState.open()}
                    }
                ) {
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
