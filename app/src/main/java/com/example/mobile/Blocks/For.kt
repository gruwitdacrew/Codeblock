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
import com.example.mobile.blocksToAdd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID

fun getForExpression(
    forBlocks: MutableList<Block>,
    condition: String,
    variable: String,
    action: String,
    value: String,
): String {
    var actions = mutableListOf<String>()
    for (i in forBlocks) {
        actions.add(i.expression.value)
    }
    if (actions.size > 0) return "f=${variable}=${value};${condition};=${variable}=${action}:${
        Json.encodeToString(
            actions
        )
    }"
    else return ""
}

@Composable
fun For(
    index: UUID,
    scope: CoroutineScope,
    drawerState: DrawerState,
    blocks: MutableList<Block>
) {
    val forBlocksToRender: MutableList<Block> = remember { mutableStateListOf() }

    var variable by rememberSaveable { mutableStateOf("") }
    var value by rememberSaveable { mutableStateOf("") }
    var condition by rememberSaveable { mutableStateOf("") }
    var action by rememberSaveable { mutableStateOf("") }

    val blockId = blocks.indexOf(blocks.find { it.id == index })

    for (i in forBlocksToRender) {
        LaunchedEffect(i.expression.value) {
            blocks[blockId].expression.value =
                getForExpression(forBlocksToRender, condition, variable, action, value)
            println(blockId)
        }
    }
    //wa>0:wab>0;action;action;action;action
    //f=i=0;i<5;=i=i+1:["f=j=0;j<5;=j=j+1:[\"/i*j\"]"]
    BlockSample(index = index, blocks = blocks, shape = RoundedCornerShape(5), inside =
    {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(cycle_color_1, cycle_color_2)
                    )
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
                    TextFieldSample(modifier = Modifier.weight(2f), onValueChange = { newText ->
                        variable = newText
                        blocks[blockId].expression.value =
                            getForExpression(
                                forBlocksToRender,
                                condition,
                                variable,
                                action,
                                value
                            )
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
                    TextFieldSample(modifier = Modifier.weight(2f), onValueChange = { newText ->
                        value = newText
                        blocks[blockId].expression.value =
                            getForExpression(
                                forBlocksToRender,
                                condition,
                                variable,
                                action,
                                value
                            )
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
                    TextFieldSample(modifier = Modifier.weight(2f), onValueChange = { newText ->
                        condition = newText
                        blocks[blockId].expression.value =
                            getForExpression(
                                forBlocksToRender,
                                condition,
                                variable,
                                action,
                                value
                            )
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
                    TextFieldSample(modifier = Modifier.weight(2f), onValueChange = { newText ->
                        variable = newText
                        blocks[blockId].expression.value =
                            getForExpression(
                                forBlocksToRender,
                                condition,
                                variable,
                                action,
                                value
                            )
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
                    TextFieldSample(modifier = Modifier.weight(2f), onValueChange = { newText ->
                        action = newText
                        blocks[blockId].expression.value =
                            getForExpression(
                                forBlocksToRender,
                                condition,
                                variable,
                                action,
                                value
                            )
                        println(blockId)
                    })
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                forBlocksToRender.forEach { block ->
                    if (!block.visibleState.currentState && !block.visibleState.targetState) forBlocksToRender.remove(
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