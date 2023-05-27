/*
package com.example.mobile.ui.theme

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DrawerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.example.mobile.Block
import com.example.mobile.BlockSample
import com.example.mobile.R
import com.example.mobile.TextFieldSample
import com.example.mobile.Utils.BlockInformation
import com.example.mobile.blocksToAdd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json



@Composable
fun While(
    view: BlockInformation,
    scope: CoroutineScope,
    drawerState: DrawerState,
) {
    val blocks = view.blocks
    val whileBlocksToRender: MutableList<Block> = remember { mutableStateListOf() }
    var condition by rememberSaveable { mutableStateOf("") }
    var index = blocks.indexOf(blocks.find { it.id == view.id })
    LaunchedEffect(blocks.size) {
        index = blocks.indexOf(blocks.find { it.id == view.id })
    }

    for (i in whileBlocksToRender) {
        LaunchedEffect(i.expression.value) {
            blocks[index].expression.value = getWhileExpression(whileBlocksToRender, condition)
            println(blocks[index].expression.value)
        }
    }
    BlockSample(view = view, shape = RoundedCornerShape(5), inside =
    {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(cycle_color_1, cycle_color_2)
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
                    text = "while",
                    fontSize = 30.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.fedra_sans)),
                    textAlign = TextAlign.Center
                )
                TextFieldSample(modifier = Modifier.weight(2f), onValueChange = { newText ->
                    condition = newText
                    blocks[index].expression.value =
                        getWhileExpression(whileBlocksToRender, condition)
                })
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                whileBlocksToRender.forEach { block ->
                    if (!block.visibleState.currentState && !block.visibleState.targetState) whileBlocksToRender.remove(
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
                }
                Button(
                    modifier = Modifier
                        .size(60.dp, 35.dp),
                    onClick = {
                        blocksToAdd = whileBlocksToRender
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
    }
    )
}*/
