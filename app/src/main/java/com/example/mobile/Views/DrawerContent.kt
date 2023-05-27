package com.example.mobile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.key.Key.Companion.Break
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.mobile.Blocks.FunctionBlock
import com.example.mobile.Blocks.VoidBlock
import com.example.mobile.Utils.BlockInformation
import com.example.mobile.Utils.start
import com.example.mobile.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@Composable
fun DrawerContent(
    scope: CoroutineScope,
    drawerState: DrawerState,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.End
    )
    {
        IconButton(
            onClick = {
                scope.launch { drawerState.close() }
            },
            modifier = Modifier
                .defaultMinSize(minWidth = 50.dp)
        )
        {
            Icon(Icons.Default.ArrowBack, contentDescription = "delete", tint = Color.White)
        }
    }
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    )
    {
        items(count = 1)
        {
            if(chooseNow.value.contains("global")){
                BlockCard(
                    text = stringResource(R.string.function_drawer_text),
                    color = func_color_1,
                    shape = CutCornerShape(50),
                    onClick = {
                        val childs = mutableMapOf<String, MutableList<Block>>("arguments" to mutableStateListOf(), "actions" to mutableStateListOf())
                        val view = BlockInformation(blocksToAdd, UUID.randomUUID(),"",childs)
                        blocksToAdd.add(Block(view.id, { FunctionBlock(scope, drawerState, view) }, mutableStateOf("")))
                        scope.launch { drawerState.close() }
                    }
                )
            }
            if(chooseNow.value.contains("function")){
                BlockCard(
                    text = stringResource(R.string.return_drawer_text),
                    color = func_color_2,
                    shape = CutCornerShape(50),
                    onClick = {
                        val view = BlockInformation(blocksToAdd, UUID.randomUUID(),chooseNow.value)
                        blocksToAdd.add(Block(view.id, { Return(view) }, mutableStateOf("")))
                        scope.launch { drawerState.close() }
                    }
                )
            }
            if(!chooseNow.value.contains("args")){
                BlockCard(
                    text = stringResource(R.string.pushback_drawer_text),
                    color = assignment_color_1,
                    shape = CutCornerShape(
                        topStartPercent = 100,
                        topEndPercent = 5,
                        bottomStartPercent = 5,
                        bottomEndPercent = 100
                    ), onClick = {
                        val view = BlockInformation(blocksToAdd, UUID.randomUUID(),chooseNow.value)
                        blocksToAdd.add(Block(view.id, { PushBack(view) }, mutableStateOf("")))
                        scope.launch { drawerState.close() }
                    })
                BlockCard(
                    text = stringResource(R.string.pop_drawer_text),
                    color = assignment_color_1,
                    shape = CutCornerShape(
                        topStartPercent = 100,
                        topEndPercent = 5,
                        bottomStartPercent = 5,
                        bottomEndPercent = 100
                    ), onClick = {
                        val view = BlockInformation(blocksToAdd, UUID.randomUUID(),chooseNow.value)
                        blocksToAdd.add(Block(view.id, { Pop(view) }, mutableStateOf("")))
                        scope.launch { drawerState.close() }
                    }
                )
                BlockCard(
                    text = stringResource(R.string.void_drawer_text),
                    color = assignment_color_1,
                    shape = CutCornerShape(
                        topStartPercent = 100,
                        topEndPercent = 5,
                        bottomStartPercent = 5,
                        bottomEndPercent = 100
                    ),
                    onClick = {
                        val view = BlockInformation(blocksToAdd, UUID.randomUUID(),chooseNow.value)
                        blocksToAdd.add(Block(view.id, { VoidBlock(view) }, mutableStateOf("")))
                        scope.launch { drawerState.close() }
                    }
                )
                BlockCard(
                    text = stringResource(R.string.for_drawer_text),
                    color = cycle_color_1,
                    shape = CutCornerShape(50),
                    onClick = {
                        val childs = mutableMapOf<String, MutableList<Block>>("actions" to mutableStateListOf())
                        val view = BlockInformation(blocksToAdd, UUID.randomUUID(), chooseNow.value,childs)
                        blocksToAdd.add(Block(view.id, { For(view, scope, drawerState) }, mutableStateOf("")))
                        scope.launch { drawerState.close() }
                    }
                )
                BlockCard(
                    text = stringResource(R.string.while_drawer_text),
                    color = cycle_color_2,
                    shape = CutCornerShape(50),
                    onClick = {
                        val childs = mutableMapOf<String, MutableList<Block>>("actions" to mutableStateListOf())
                        val view = BlockInformation(blocksToAdd, UUID.randomUUID(),chooseNow.value, childs)
                        blocksToAdd.add(Block(view.id, { While(view, scope, drawerState) }, mutableStateOf("")))
                        scope.launch { drawerState.close() }
                    }
                )
                BlockCard(
                    text = stringResource(R.string.if_drawer_text),
                    color = condition_color_1,
                    shape = CutCornerShape(50),
                    onClick = {
                        val childs = mutableMapOf<String, MutableList<Block>>("ifActions" to mutableStateListOf(), "elseActions" to mutableStateListOf())
                        val view = BlockInformation(blocksToAdd, UUID.randomUUID(),chooseNow.value, childs)
                        blocksToAdd.add(Block(view.id, { Condition(view, scope, drawerState) }, mutableStateOf("")))
                        scope.launch { drawerState.close() }
                    }
                )
                BlockCard(
                    text = stringResource(R.string.print_drawer_text),
                    color = print_color_1,
                    shape = RectangleShape,
                    onClick = {
                        val view = BlockInformation(blocksToAdd, UUID.randomUUID(),chooseNow.value)
                        blocksToAdd.add(Block(view.id, { PrintBlock(view) }, mutableStateOf("")))
                        scope.launch { drawerState.close() }
                    }
                )
            }
            if(chooseNow.value.contains("cycle")){
                BlockCard(
                    text = stringResource(R.string.break_drawer_text),
                    color = print_color_1,
                    shape = RectangleShape,
                    onClick = {
                        val view = BlockInformation(blocksToAdd, UUID.randomUUID(),chooseNow.value)
                        blocksToAdd.add(Block(view.id, { Break(view) }, mutableStateOf("")))
                        scope.launch { drawerState.close() }
                    }
                )
                BlockCard(
                    text = stringResource(R.string.continue_drawer_text),
                    color = print_color_1,
                    shape = RectangleShape,
                    onClick = {
                        val view = BlockInformation(blocksToAdd, UUID.randomUUID(),chooseNow.value)
                        blocksToAdd.add(Block(view.id, { Continue(view) }, mutableStateOf("")))
                        scope.launch { drawerState.close() }
                    }
                )
            }
            BlockCard(
                text = stringResource(R.string.init_drawer_text),
                color = init_color_2,
                shape = RoundedCornerShape(50),
                onClick = {
                    val view = BlockInformation(blocksToAdd, UUID.randomUUID(),chooseNow.value)
                    blocksToAdd.add(Block(view.id, { InitBlock(view) }, mutableStateOf("")))
                    scope.launch { drawerState.close() }
                }
            )
            BlockCard(
                text = stringResource(R.string.assignment_drawer_text),
                color = assignment_color_1,
                shape = CutCornerShape(
                    topStartPercent = 100,
                    topEndPercent = 5,
                    bottomStartPercent = 5,
                    bottomEndPercent = 100
                ),
                onClick = {
                    val view = BlockInformation(blocksToAdd, UUID.randomUUID(),chooseNow.value)
                    blocksToAdd.add(Block(view.id, { Assignment(view) }, mutableStateOf("")))
                    scope.launch { drawerState.close() }
                }
            )
        }
    }
}
