package com.example.mobile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mobile.Blocks.FunctionBlock
import com.example.mobile.Blocks.Void
import com.example.mobile.Utils.BlockInformation
import com.example.mobile.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("UnrememberedMutableState")
@Composable
fun DrawerContent(
    scope: CoroutineScope,
    drawerState: DrawerState,
) {
    var offsetX by remember { mutableStateOf(0f) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    )

    {
        Button(
            onClick = {
                if (light.value) {
                    DarkTheme()
                    light.value = false
                } else {
                    LightTheme()
                    light.value = true
                }
            },
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .size(60.dp, 40.dp),
            shape = RoundedCornerShape(30)
        ) {
            if (light.value) {
                Image(
                    painter = painterResource(id = R.drawable.dark),
                    contentDescription = "Change Theme",
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.light),
                    contentDescription = "Change Theme",
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        IconButton(
            onClick = {
                scope.launch { drawerState.close() }
            }, modifier = Modifier.defaultMinSize(minWidth = 50.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "delete", tint = Color.White)
        }
    }
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        items(count = 1) {
            BlockCard(text = stringResource(R.string.function_drawer_text),
                color = Color(0xFFC48E08),
                shape = CutCornerShape(50),
                onClick = {
                    val view = BlockInformation(blocksToAdd, UUID.randomUUID())
                    blocksToAdd.add(
                        Block(
                            view.id, { FunctionBlock(scope, drawerState, view) }, mutableStateOf("")
                        )
                    )
                })
            BlockCard(text = stringResource(R.string.pushback_drawer_text), color = assignment_color_1, shape = CutCornerShape(
                topStartPercent = 100,
                topEndPercent = 5,
                bottomStartPercent = 5,
                bottomEndPercent = 100
            ), onClick = {
                val view = BlockInformation(blocksToAdd, UUID.randomUUID())
                blocksToAdd.add(Block(view.id, { PushBack(view) }, mutableStateOf("")))
                scope.launch { drawerState.close() }
            })
            BlockCard(text = stringResource(R.string.void_drawer_text),
                color = init_color_2,
                shape = RoundedCornerShape(50),
                onClick = {
                    val view = BlockInformation(blocksToAdd, UUID.randomUUID())
                    blocksToAdd.add(Block(view.id, { Void(view) }, mutableStateOf("")))
                    scope.launch { drawerState.close() }
                })
            BlockCard(text = stringResource(R.string.return_drawer_text),
                color = Color(0xFFC48E08),
                shape = CutCornerShape(50),
                onClick = {
                    val view = BlockInformation(blocksToAdd, UUID.randomUUID())
                    blocksToAdd.add(Block(view.id, { Return(view) }, mutableStateOf("")))
                })
            BlockCard(text = stringResource(R.string.for_drawer_text), color = cycle_color_1, shape = CutCornerShape(50), onClick = {
                val view = BlockInformation(blocksToAdd, UUID.randomUUID())
                blocksToAdd.add(
                    Block(
                        view.id, { For(view, scope, drawerState) }, mutableStateOf("")
                    )
                )
                scope.launch { drawerState.close() }
            })
            BlockCard(text = stringResource(R.string.while_drawer_text), color = cycle_color_2, shape = CutCornerShape(50), onClick = {
                val view = BlockInformation(blocksToAdd, UUID.randomUUID())
                blocksToAdd.add(
                    Block(
                        view.id, { While(view, scope, drawerState) }, mutableStateOf("")
                    )
                )
                scope.launch { drawerState.close() }
            })
            BlockCard(text = stringResource(R.string.if_drawer_text),
                color = condition_color_1,
                shape = CutCornerShape(50),
                onClick = {
                    val view = BlockInformation(blocksToAdd, UUID.randomUUID())
                    blocksToAdd.add(
                        Block(
                            view.id, { Condition(view, scope, drawerState) }, mutableStateOf("")
                        )
                    )
                    scope.launch { drawerState.close() }
                })
            BlockCard(text = stringResource(R.string.init_drawer_text),
                color = init_color_2,
                shape = RoundedCornerShape(50),
                onClick = {
                    val view = BlockInformation(blocksToAdd, UUID.randomUUID())
                    blocksToAdd.add(Block(view.id, { InitBlock(view) }, mutableStateOf("")))
                    scope.launch { drawerState.close() }
                })
            BlockCard(text = stringResource(R.string.init_drawer_text), color = assignment_color_1, shape = CutCornerShape(
                topStartPercent = 100,
                topEndPercent = 5,
                bottomStartPercent = 5,
                bottomEndPercent = 100
            ), onClick = {
                val view = BlockInformation(blocksToAdd, UUID.randomUUID())
                blocksToAdd.add(Block(view.id, { Assignment(view) }, mutableStateOf("")))
                scope.launch { drawerState.close() }
            })
            BlockCard(text = stringResource(R.string.print_drawer_text), color = print_color_1, shape = RectangleShape, onClick = {
                val view = BlockInformation(blocksToAdd, UUID.randomUUID())
                blocksToAdd.add(Block(view.id, { PrintBlock(view) }, mutableStateOf("")))
                scope.launch { drawerState.close() }
            })
        }
    }
}
