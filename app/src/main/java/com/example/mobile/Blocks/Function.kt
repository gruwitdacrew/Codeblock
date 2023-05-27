package com.example.mobile.Blocks

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
import com.example.mobile.ui.theme.condition_color_1
import com.example.mobile.ui.theme.condition_color_2
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun getFunctionExpression(
    name:String,
    type: String,
    functionBlocks:MutableList<Block>,
    argumentBlocks:MutableList<Block>,
):String{
    var actions = mutableListOf<String>(); var arguments = mutableListOf<String>()
    for(i in functionBlocks){
        actions.add(i.expression.value)
    }
    for(i in argumentBlocks){
        arguments.add(i.expression.value)
    }
    if(actions.size > 0 ) return "*${type};${name};${Json.encodeToString(arguments)}:${Json.encodeToString(actions)}"
    else return "";
}


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
            blocks[index].expression.value = getFunctionExpression(name,type, functionBlocksToRender, argumentsToRender)
            println(blocks[index].expression.value)
        }
    }
    for(i in argumentsToRender){
        LaunchedEffect(i.expression.value){
            blocks[index].expression.value = getFunctionExpression(name,type, functionBlocksToRender, argumentsToRender)
            println(blocks[index].expression.value)
        }
    }

    BlockSample(view = view, shape = RoundedCornerShape(5), inside =
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
                TextFieldSample(modifier = Modifier.weight(2f), onValueChange = {newText ->
                    type = newText
                    blocks[index].expression.value = getFunctionExpression(name,type, functionBlocksToRender, argumentsToRender)
                })
                Text(
                    modifier = Modifier
                        .padding(end = 20.dp),
                    text = "function",
                    fontSize = 30.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.fedra_sans)),
                    textAlign = TextAlign.Center
                )
                TextFieldSample(modifier = Modifier.weight(2f), onValueChange = {newText ->
                    name = newText
                    blocks[index].expression.value = getFunctionExpression(name,type, functionBlocksToRender, argumentsToRender)
                })
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                argumentsToRender.forEach{block ->
                    block.element()
                }
                Button(
                    modifier = Modifier
                        .size(60.dp, 35.dp),
                    onClick = {
                        chooseNow.value = view.namesOfParentsBlocks + " args"
                        blocksToAdd = argumentsToRender
                        scope.launch{drawerState.open()}
                    },
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                )
                {
                    Image(painter = painterResource(id = R.drawable.add), contentDescription = null, contentScale = ContentScale.Fit)
                }
            }
            Text(
                text = "actions",
                textAlign = TextAlign.Center
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                functionBlocksToRender.forEach{block ->
                    block.element()
                }
                Button(
                    modifier = Modifier
                        .size(60.dp, 35.dp),
                    onClick = {
                        chooseNow.value = view.namesOfParentsBlocks + " function"
                        blocksToAdd = functionBlocksToRender
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
                    handleBlockDelete(view.id, blocks)
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "delete", tint = Color.White)
            }
        }
    }
    )
}