package com.example.mobile.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.mobile.*
import com.example.mobile.R
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

    var variable by rememberSaveable { mutableStateOf("") }
    var value by rememberSaveable { mutableStateOf("") }
    var condition by rememberSaveable { mutableStateOf("") }
    var action by rememberSaveable { mutableStateOf("") }

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
//    Card(
//        modifier = Modifier
//            .padding(15.dp)
//            .border(2.dp, Color.Black, shape = RoundedCornerShape(5.dp))
//            .fillMaxWidth()
//            .fillMaxHeight()
//            .defaultMinSize(225.dp, 480.dp)
//            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
//            .onGloballyPositioned { coordinates ->
//                blocks[blockId].offset = with(localDensity) {coordinates.positionInParent().y.toDp()}
//            }
//            .pointerInput(Unit)
//            {
//                detectDragGesturesAfterLongPress(
//                    onDragStart =
//                    {
//                        isDragged = true
//                    },
//                    onDragEnd =
//                    {
//                        isDragged = false
//                        putInPlace(with(localDensity) { offsetY.toDp() }, index, blocks)
//                        offsetY = 0f
//                        offsetX = 0f
//                    }
//                ) { change, dragAmount ->
//                    change.consume()
//                    offsetX += dragAmount.x
//                    offsetY += dragAmount.y
//                }
//            },
//        backgroundColor = Color.Cyan
//    )
    BlockSample(index = index, blocks = blocks, shape = RoundedCornerShape(5), inside =
    {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(condition_color_1, condition_color_2))
                ),
            verticalArrangement = Arrangement.SpaceBetween
        )
        {
            Column(
                modifier = Modifier
                    .border(
                        width = 2.dp, color = Color.Black, shape = RectangleShape
                    ),
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                )
                {
                    Text(
                        text = "For",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.fedra_sans)),
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(horizontal = 10.dp),
                        textAlign = TextAlign.Center
                    )
//                TextField(
//                    value = variable,
//                    onValueChange = {newText ->
//                        variable = newText
//                        blocks[blockId].expression.value = getForExpression(forBlocksToRender, condition,variable,action,value)
//                        println(blockId)
//                    },
//                    textStyle = TextStyle(fontSize = 20.sp),
//                    modifier = Modifier
//                        .background(Color.Transparent)
//                        .weight(1f)
//                )
                    TextFieldSample(size = 100.dp, onValueChange = { newText ->
                        variable = newText
                        blocks[blockId].expression.value =
                            getForExpression(forBlocksToRender, condition, variable, action, value)
                        println(blockId)
                    })
                    Text(
                        text = "=",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.fedra_sans)),
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(horizontal = 10.dp),
                        textAlign = TextAlign.Center
                    )
//                TextField(
//                    value = value,
//                    onValueChange = {newText ->
//                        value = newText
//                        blocks[blockId].expression.value = getForExpression(forBlocksToRender, condition,variable,action,value)
//                        println(blockId)
//                    },
//                    textStyle = TextStyle(fontSize = 20.sp),
//                    modifier = Modifier
//                        .background(Color.Transparent)
//                        .weight(1f)
//                )
                    TextFieldSample(size = 100.dp, onValueChange = { newText ->
                        value = newText
                        blocks[blockId].expression.value =
                            getForExpression(forBlocksToRender, condition, variable, action, value)
                        println(blockId)
                    })
                    Text(
                        text = ";",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.fedra_sans)),
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(horizontal = 10.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 70.dp),
                    verticalAlignment = Alignment.CenterVertically,
                )
                {
                    TextFieldSample(size = 250.dp, onValueChange = { newText ->
                        condition = newText
                        blocks[blockId].expression.value =
                            getForExpression(forBlocksToRender, condition, variable, action, value)
                        println(blockId)
                    })
                    Text(
                        text = ";",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.fedra_sans)),
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(horizontal = 10.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 70.dp),
                    verticalAlignment = Alignment.CenterVertically,
                )
                {
                    TextFieldSample(size = 120.dp, onValueChange = { newText ->
                        variable = newText
                        blocks[blockId].expression.value =
                            getForExpression(forBlocksToRender, condition, variable, action, value)
                        println(blockId)
                    })
                    Text(
                        text = "=",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.fedra_sans)),
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(horizontal = 10.dp),
                        textAlign = TextAlign.Center
                    )
//                TextField(
//                    action,
//                    onValueChange = {newText ->
//                        action = newText
//                        blocks[blockId].expression.value = getForExpression(forBlocksToRender, condition,variable,action,value)
//                        println(blockId)
//                    },
//                    textStyle = TextStyle(fontSize = 20.sp),
//                    modifier = Modifier
//                        .background(Color.Transparent)
//                        .weight(1f)
//                )
                    TextFieldSample(size = 120.dp, onValueChange = { newText ->
                        action = newText
                        blocks[blockId].expression.value =
                            getForExpression(forBlocksToRender, condition, variable, action, value)
                        println(blockId)
                    })
                }
            }
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .border(
//                        width = 2.dp, color = Color.Black, shape = RectangleShape
//                    ),
//                verticalAlignment = Alignment.CenterVertically
//            )
//            {
//                Text(
//                    text = "For",
//                    color = Color.White,
//                    fontFamily = FontFamily(Font(R.font.fedra_sans)),
//                    fontSize = 20.sp,
//                    modifier = Modifier
//                        .padding(horizontal = 10.dp),
//                    textAlign = TextAlign.Center
//                )
////                TextField(
////                    value = variable,
////                    onValueChange = {newText ->
////                        variable = newText
////                        blocks[blockId].expression.value = getForExpression(forBlocksToRender, condition,variable,action,value)
////                        println(blockId)
////                    },
////                    textStyle = TextStyle(fontSize = 20.sp),
////                    modifier = Modifier
////                        .background(Color.Transparent)
////                        .weight(1f)
////                )
//                TextFieldSample(size = 50.dp, onValueChange = {newText ->
//                    variable = newText
//                    blocks[blockId].expression.value = getForExpression(forBlocksToRender, condition,variable,action,value)
//                    println(blockId)
//                })
//                Text(
//                    text = "=",
//                    color = Color.White,
//                    fontFamily = FontFamily(Font(R.font.fedra_sans)),
//                    fontSize = 30.sp,
//                    modifier = Modifier
//                        .padding(horizontal = 10.dp),
//                    textAlign = TextAlign.Center
//                )
////                TextField(
////                    value = value,
////                    onValueChange = {newText ->
////                        value = newText
////                        blocks[blockId].expression.value = getForExpression(forBlocksToRender, condition,variable,action,value)
////                        println(blockId)
////                    },
////                    textStyle = TextStyle(fontSize = 20.sp),
////                    modifier = Modifier
////                        .background(Color.Transparent)
////                        .weight(1f)
////                )
//                TextFieldSample(size = 50.dp, onValueChange = {newText ->
//                    value = newText
//                    blocks[blockId].expression.value = getForExpression(forBlocksToRender, condition,variable,action,value)
//                    println(blockId)
//                })
//                Text(
//                    text = ";",
//                    color = Color.White,
//                    fontFamily = FontFamily(Font(R.font.fedra_sans)),
//                    fontSize = 30.sp,
//                    modifier = Modifier
//                        .padding(horizontal = 10.dp),
//                    textAlign = TextAlign.Center
//                )
////                TextField(
////                    value = condition,
////                    onValueChange = {newText ->
////                        condition = newText
////                        blocks[blockId].expression.value = getForExpression(forBlocksToRender, condition,variable,action,value)
////                        println(blockId)
////                    },
////                    textStyle = TextStyle(fontSize = 20.sp),
////                    modifier = Modifier
////                        .background(Color.Transparent)
////                        .weight(2f)
////                )
//                TextFieldSample(size = 50.dp, onValueChange = {newText ->
//                    condition = newText
//                    blocks[blockId].expression.value = getForExpression(forBlocksToRender, condition,variable,action,value)
//                    println(blockId)
//                })
//                Text(
//                    text = ";",
//                    color = Color.White,
//                    fontFamily = FontFamily(Font(R.font.fedra_sans)),
//                    fontSize = 30.sp,
//                    modifier = Modifier
//                        .padding(horizontal = 10.dp),
//                    textAlign = TextAlign.Center
//                )
////                TextField(
////                    value = variable,
////                    onValueChange = {newText ->
////                        variable = newText
////                        blocks[blockId].expression.value = getForExpression(forBlocksToRender, condition,variable,action,value)
////                        println(blockId)
////                    },
////                    textStyle = TextStyle(fontSize = 20.sp),
////                    modifier = Modifier
////                        .background(Color.Transparent)
////                        .weight(1f)
////                )
//                TextFieldSample(size = 50.dp, onValueChange = {newText ->
//                    variable = newText
//                    blocks[blockId].expression.value = getForExpression(forBlocksToRender, condition,variable,action,value)
//                    println(blockId)
//                })
//                Text(
//                    text = "=",
//                    color = Color.White,
//                    fontFamily = FontFamily(Font(R.font.fedra_sans)),
//                    fontSize = 30.sp,
//                    modifier = Modifier
//                        .padding(horizontal = 10.dp),
//                    textAlign = TextAlign.Center
//                )
////                TextField(
////                    action,
////                    onValueChange = {newText ->
////                        action = newText
////                        blocks[blockId].expression.value = getForExpression(forBlocksToRender, condition,variable,action,value)
////                        println(blockId)
////                    },
////                    textStyle = TextStyle(fontSize = 20.sp),
////                    modifier = Modifier
////                        .background(Color.Transparent)
////                        .weight(1f)
////                )
//                TextFieldSample(size = 50.dp, onValueChange = {newText ->
//                    action = newText
//                    blocks[blockId].expression.value = getForExpression(forBlocksToRender, condition,variable,action,value)
//                    println(blockId)
//                })
//            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                forBlocksToRender.forEach{block ->
                    block.element()
                }
//                Button(
//                    onClick = {
//                        blocksToAdd = forBlocksToRender
//                        scope.launch{drawerState.open()}
//                    }
//                ) {
//                    Text(text = "+")
//                }
                Button(
                    modifier = Modifier
                        .size(60.dp, 35.dp),
                    onClick = {
                        blocksToAdd = forBlocksToRender
                        scope.launch{drawerState.open()}
                    },
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
    }
    )

}