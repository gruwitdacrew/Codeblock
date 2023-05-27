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
import com.example.mobile.Utils.getExpression
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun For(
    view: BlockInformation,
    scope: CoroutineScope,
    drawerState: DrawerState,
) {
    val forBlocksToRender = view.childs["actions"]!!
    var variable by rememberSaveable { mutableStateOf("") }
    var value by rememberSaveable { mutableStateOf("") }
    var condition by rememberSaveable { mutableStateOf("") }
    var action by rememberSaveable { mutableStateOf("") }

    val blocks = view.blocks

    var index = blocks.indexOf(blocks.find { it.id == view.id })
    LaunchedEffect(blocks.size){
        index = blocks.indexOf(blocks.find { it.id == view.id })
    }

    for (i in forBlocksToRender) {
        LaunchedEffect(i.expression.value) {
            blocks[index].expression.value =
                getExpression(forBlocksToRender, condition, variable, action, value)
        }
    }
    //wa>0:wab>0;action;action;action;action
    //f=i=0;i<5;=i=i+1:["f=j=0;j<5;=j=j+1:[\"/i*j\"]"]
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
                        blocks[index].expression.value =
                            getExpression(
                                forBlocksToRender,
                                condition,
                                variable,
                                action,
                                value
                            )
                        println(index)
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
                        blocks[index].expression.value =
                            getExpression(
                                forBlocksToRender,
                                condition,
                                variable,
                                action,
                                value
                            )
                        println(index)
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
                        blocks[index].expression.value =
                            getExpression(
                                forBlocksToRender,
                                condition,
                                variable,
                                action,
                                value
                            )
                        println(index)
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
                        blocks[index].expression.value =
                            getExpression(
                                forBlocksToRender,
                                condition,
                                variable,
                                action,
                                value
                            )
                        println(index)
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
                        blocks[index].expression.value =
                            getExpression(
                                forBlocksToRender,
                                condition,
                                variable,
                                action,
                                value
                            )
                        println(index)
                    })
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                for (item in forBlocksToRender) {
                    key(item) {
                        if (!item.visibleState.currentState && !item.visibleState.targetState) handleBlockDelete(
                            item.id,
                            forBlocksToRender
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
                        chooseNow.value = "cycle"
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

}