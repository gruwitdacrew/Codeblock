package com.example.mobile.ui.theme

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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.*
import com.example.mobile.R
import com.example.mobile.Utils.BlockInformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

fun getIfExpression(
    ifBlocks: MutableList<Block>,
    elseBlocks: MutableList<Block>,
    condition: String
): String {
    var ifActions = mutableListOf<String>()
    var elseActions = mutableListOf<String>()
    var indexOfElse = 0
    for (i in ifBlocks) {
        ifActions.add(i.expression.value)
        indexOfElse += i.expression.value.length
    }
    for (i in elseBlocks) {
        elseActions.add(i.expression.value)
    }
    if (ifActions.size > 0 && elseActions.size > 0) {
        return "?${indexOfElse};${condition}:${Json.encodeToString(ifActions)}:${
            Json.encodeToString(
                ifActions
            )
        }"
    } else if (ifActions.size > 0) {
        return "?${-1};${condition}:${Json.encodeToString(ifActions)}"
    } else return ""
}

@Composable
fun Condition(
    view: BlockInformation,
    scope: CoroutineScope,
    drawerState: DrawerState,
) {
    val blocks = view.blocks
    val ifBlocksToRender = view.childs["ifActions"]!!
    val elseBlocksToRender = view.childs["elseActions"]!!
    var condition by rememberSaveable { mutableStateOf("") }

    var index = blocks.indexOf(blocks.find { it.id == view.id })
    LaunchedEffect(blocks.size){
        index = blocks.indexOf(blocks.find { it.id == view.id })
    }

    for (i in ifBlocksToRender) {
        LaunchedEffect(i.expression.value) {
            blocks[index].expression.value =
                getIfExpression(ifBlocksToRender, elseBlocksToRender, condition)
        }
    }
    for (i in elseBlocksToRender) {
        LaunchedEffect(i.expression.value) {
            blocks[index].expression.value =
                getIfExpression(ifBlocksToRender, elseBlocksToRender, condition)
        }
    }

    BlockSample(view = view, shape = RoundedCornerShape(5), inside =
    @Composable
    {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(condition_color_1, condition_color_2)
                    )
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
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
                TextFieldSample(modifier = Modifier.weight(2f), onValueChange = { newText ->
                    condition = newText
                    blocks[index].expression.value =
                        getIfExpression(ifBlocksToRender, elseBlocksToRender, condition)
                })
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                ifBlocksToRender.forEach { block ->
                    if (!block.visibleState.currentState && !block.visibleState.targetState) ifBlocksToRender.remove(
                        block
                    )
                    AnimatedVisibility(
                        visibleState = block.visibleState,
                        enter = scaleIn(animationSpec = tween(durationMillis = 100)),
                        exit = scaleOut(animationSpec = tween(durationMillis = 100)),
                    )
                    {
                        block.element()
                    }
                    println("${block.id} ${block.offset.value}")
                }
                Button(
                    modifier = Modifier
                        .size(60.dp, 35.dp),
                    onClick = {
                        blocksToAdd = ifBlocksToRender
                        scope.launch { drawerState.open() }
                    },
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    shape = RoundedCornerShape(50)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
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
                elseBlocksToRender.forEach { block ->
                    if (!block.visibleState.currentState && !block.visibleState.targetState) elseBlocksToRender.remove(
                        block
                    )
                    AnimatedVisibility(
                        visibleState = block.visibleState,
                        enter = scaleIn(animationSpec = tween(durationMillis = 100)),
                        exit = scaleOut(animationSpec = tween(durationMillis = 100)),
                    )
                    {
                        block.element()
                    }
                    println("${block.id} ${block.offset}")
                }
                Button(
                    modifier = Modifier
                        .size(60.dp, 35.dp),
                    onClick = {
                        blocksToAdd = elseBlocksToRender
                        scope.launch { drawerState.open() }
                    },
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    shape = RoundedCornerShape(50)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }

            }
            IconButton(
                onClick = {
                    blocks[index].visibleState.targetState = false
                },
                modifier = Modifier
                    .defaultMinSize(minWidth = 60.dp)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "delete", tint = Color.White)
            }
        }
    })
}
