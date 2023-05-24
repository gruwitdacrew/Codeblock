package com.example.mobile.ui.theme

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID

fun getWhileExpression(
    whileBlocks:MutableList<Block>,
    condition: String
):String{
    var actions = mutableListOf<String>()
    for(i in whileBlocks){
        actions.add(i.expression.value)
    }
    if(actions.size > 0) return "w${condition}:${Json.encodeToString(actions)}"
    else return "";
}

@Composable
fun While(
    index: UUID,
    scope: CoroutineScope,
    drawerState: DrawerState,
    blocks:MutableList<Block>
) {
    val whileBlocksToRender: MutableList<Block> = remember { mutableStateListOf() }
    var condition by rememberSaveable { mutableStateOf("") }

    val blockId = blocks.indexOf(blocks.find { it.id == index })

    for(i in whileBlocksToRender){
        LaunchedEffect(i.expression.value){
            blocks[blockId].expression.value = getWhileExpression(whileBlocksToRender, condition)
            println(blocks[blockId].expression.value)
        }
    }
    BlockSample(index = index, blocks = blocks, shape = RoundedCornerShape(5), inside =
    {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(cycle_color_1, cycle_color_2))
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
                    text = "while",
                    fontSize = 30.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.fedra_sans)),
                    textAlign = TextAlign.Center
                )
                TextFieldSample(modifier = Modifier.weight(2f), onValueChange = {newText ->
                    condition = newText
                    blocks[blockId].expression.value = getWhileExpression(whileBlocksToRender, condition)
                })
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                whileBlocksToRender.forEach{block ->
                    if (!block.visibleState.currentState && !block.visibleState.targetState) whileBlocksToRender.remove(block)
                    AnimatedVisibility(
                        visibleState = block.visibleState,
                        enter = scaleIn(animationSpec = tween(durationMillis = 100)),
                        exit = scaleOut(animationSpec = tween(durationMillis = 100)),
                    )
                    {
                        block.element()
                    }
                }
                Button(
                    modifier = Modifier
                        .size(60.dp, 35.dp),
                    onClick = {
                        blocksToAdd = whileBlocksToRender
                        scope.launch{drawerState.open()}
                    },
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    shape = RoundedCornerShape(50)
                )
                {
                    Image(painter = painterResource(id = R.drawable.add), contentDescription = null, contentScale = ContentScale.Fit)
                }
            }
            IconButton(
                onClick = {
                    blocks[blockId].visibleState.targetState = false
                },
                modifier = Modifier
                    .defaultMinSize(minWidth = 60.dp)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "delete", tint = Color.White)
            }
        }
    }
    )
}