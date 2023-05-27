package com.example.mobile.ui.theme

import android.annotation.SuppressLint
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
import com.example.mobile.Utils.getExpression
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun Condition(
    view: BlockInformation,
    scope: CoroutineScope,
    drawerState: DrawerState,
) {
    var ifBlocksToRender:MutableList<Block> = view.childs["ifActions"]!!
    var elseBlocksToRender = view.childs["elseActions"]!!
    var condition by rememberSaveable { mutableStateOf("") }
    val blocks = view.blocks


    var index = blocks.indexOf(blocks.find { it.id == view.id })
    LaunchedEffect(blocks.size) {
        index = blocks.indexOf(blocks.find { it.id == view.id })
    }

    for (i in ifBlocksToRender) {
        LaunchedEffect(i.expression.value) {
            blocks[index].expression.value =
                getExpression(ifBlocksToRender, elseBlocksToRender, condition)
        }
    }
    for (i in elseBlocksToRender) {
        LaunchedEffect(i.expression.value) {
            blocks[index].expression.value =
                getExpression(ifBlocksToRender, elseBlocksToRender, condition)
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
                        getExpression(ifBlocksToRender, elseBlocksToRender, condition)
                })
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                for (item in ifBlocksToRender) {
                    key(item) {
                        if (!item.visibleState.currentState && !item.visibleState.targetState) handleBlockDelete(
                            item.id,
                            ifBlocksToRender
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
                for (item in elseBlocksToRender) {
                    key(item) {
                        if (!item.visibleState.currentState && !item.visibleState.targetState) handleBlockDelete(
                            item.id,
                            elseBlocksToRender
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
