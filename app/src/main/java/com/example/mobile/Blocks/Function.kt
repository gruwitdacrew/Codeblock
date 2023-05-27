package com.example.mobile.Blocks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.mobile.*
import com.example.mobile.R
import com.example.mobile.Utils.BlockInformation
import com.example.mobile.Utils.getExpression
import com.example.mobile.ui.theme.*

@Composable
fun FunctionBlock(
    scope: CoroutineScope,
    drawerState: DrawerState,
    view: BlockInformation
) {
    val blocks  = view.blocks
    val argumentsToRender = view.childs["arguments"]!!
    val functionBlocksToRender = view.childs["actions"]!!
    var type by rememberSaveable {
        mutableStateOf("Void")
    }
    var name by rememberSaveable {
        mutableStateOf("")
    }
    var index = blocks.indexOf(blocks.find { it.id == view.id })
    LaunchedEffect(blocks.size){
        index = blocks.indexOf(blocks.find { it.id == view.id })
    }

    for(i in functionBlocksToRender){
        LaunchedEffect(i.expression.value){
            blocks[index].expression.value = getExpression(name,type, functionBlocksToRender, argumentsToRender)
            println(blocks[index].expression.value)
        }
    }
    for(i in argumentsToRender){
        LaunchedEffect(i.expression.value){
            blocks[index].expression.value = getExpression(name,type, functionBlocksToRender, argumentsToRender)
            println(blocks[index].expression.value)
        }
    }


    BlockSample(view = view, shape = RoundedCornerShape(5), inside =
    {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(func_color_1, func_color_2))
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp, color = Color.Black, shape = RectangleShape
                    ),
                verticalAlignment = Alignment.CenterVertically,

            )
            {
                Box(
                    modifier = Modifier
                        .width(110.dp)
                        .fillMaxHeight()
                )
                {
                    var expanded by remember { mutableStateOf(false) }
                    DropdownMenu(
                        modifier = Modifier
                            .background(color = Color.Black),
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    )
                    {
                        DropdownMenuItem(
                            onClick = {
                                type = "Void"
                                expanded = false
                                name = name.trim()
                                blocks[index].expression.value = getExpression(name,type, functionBlocksToRender, argumentsToRender)
                            }
                        ) {
                            Text(
                                "Void",
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.fedra_sans)),
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                type = "Int"
                                expanded = false
                                name = name.trim()
                                blocks[index].expression.value = getExpression(name,type, functionBlocksToRender, argumentsToRender)
                            }
                        ) {
                            Text(
                                text = "Int",
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.fedra_sans)),
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                type = "String"
                                expanded = false
                                name = name.trim()
                                blocks[index].expression.value = getExpression(name,type, functionBlocksToRender, argumentsToRender)
                            }
                        ) {
                            Text(
                                text = "String",
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.fedra_sans)),
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                type = "Bool"
                                expanded = false
                                name = name.trim()
                                blocks[index].expression.value = getExpression(name,type, functionBlocksToRender, argumentsToRender)
                            }
                        ) {
                            Text(
                                "Bool",
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.fedra_sans)),
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                type = "Array\n<Int>"
                                expanded = false
                                name = name.trim()
                                blocks[index].expression.value = getExpression(name,type, functionBlocksToRender, argumentsToRender)
                            }
                        ) {
                            Text(
                                "Array\n<Int>",
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.fedra_sans)),
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                type = "Array\n<String>"
                                expanded = false
                                name = name.trim()
                                blocks[index].expression.value = getExpression(name,type, functionBlocksToRender, argumentsToRender)
                            }
                        ) {
                            Text(
                                "Array\n<String>",
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.fedra_sans)),
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                type = "Array\n<Bool>"
                                expanded = false
                                name = name.trim()
                                blocks[index].expression.value = getExpression(name,type, functionBlocksToRender, argumentsToRender)
                            }
                        ) {
                            Text(
                                "Array\n<Bool>",
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.fedra_sans)),
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Button(
                        onClick = { expanded = true },
                        colors = ButtonDefaults.buttonColors(func_color_2),
                        modifier = Modifier
                            .defaultMinSize(minHeight = 70.dp)
                            .fillMaxSize()
                    )
                    {
                        Text(
                            text = type.ifBlank { "Select\nType" },
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.fedra_sans)),
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Text(
                    modifier = Modifier
                        .padding(10.dp),
                    text = "function",
                    fontSize = 30.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.fedra_sans)),
                    textAlign = TextAlign.Center
                )
                TextFieldSample(modifier = Modifier.weight(2f), onValueChange = {newText ->
                    name = newText
                    blocks[index].expression.value = getExpression(name,type, functionBlocksToRender, argumentsToRender)
                })
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Text(
                        text = "on input",
                        fontSize = 30.sp,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.fedra_sans)),
                        textAlign = TextAlign.Center
                    )
                }
                for (item in argumentsToRender) {
                    key(item) {
                        if (!item.visibleState.currentState && !item.visibleState.targetState) handleBlockDelete(
                            item.id,
                            argumentsToRender
                        )
                        AnimatedVisibility(
                            visibleState = item.visibleState,
                            enter = scaleIn(animationSpec = tween(durationMillis = 100)),
                            exit = scaleOut(animationSpec = tween(durationMillis = 100)),
                        )
                        {
                            item.element()
                        }
                        println("${item.id} ${item.offset}")
                    }
                }
                Button(
                    modifier = Modifier
                        .size(60.dp, 35.dp),
                    onClick = {
                        chooseNow.value  = "args"
                        blocksToAdd = argumentsToRender
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
                    text = "do",
                    fontSize = 30.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.fedra_sans)),
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                for (item in functionBlocksToRender) {
                    key(item) {
                        if (!item.visibleState.currentState && !item.visibleState.targetState) handleBlockDelete(
                            item.id,
                            functionBlocksToRender
                        )
                        AnimatedVisibility(
                            visibleState = item.visibleState,
                            enter = scaleIn(animationSpec = tween(durationMillis = 100)),
                            exit = scaleOut(animationSpec = tween(durationMillis = 100)),
                        )
                        {
                            item.element()
                        }
                        println("${item.id} ${item.offset}")
                    }
                }
                Button(
                    modifier = Modifier
                        .size(60.dp, 35.dp),
                    onClick = {
                        chooseNow.value  = "function"
                        blocksToAdd = functionBlocksToRender
                        scope.launch{ drawerState.open() }
                    },
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                )
                {
                    Image(painter = painterResource(id = R.drawable.add), contentDescription = null, contentScale = ContentScale.Fit)
                }
            }
            IconButton(
                onClick = {
                    blocks[index].visibleState.targetState = false
                },
                modifier = Modifier.padding(start = 8.dp)
            )
            {
                Icon(Icons.Default.Delete, contentDescription = "delete", tint = Color.White)
            }
        }
    }
    )
}