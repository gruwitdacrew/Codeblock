package com.example.mobile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.mobile.Blocks.FunctionBlock
import com.example.mobile.Utils.BlockInformation
import com.example.mobile.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

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
        ) {
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
            BlockCard(
                text = "Function",
                color = Color(0xFFC48E08),
                shape = CutCornerShape(50),
                onClick = {
                    val view = BlockInformation(blocksToAdd, UUID.randomUUID())
                    blocksToAdd.add(Block(view.id, { FunctionBlock(scope, drawerState, view) }, mutableStateOf("")))
                }
            )
            BlockCard(
                text = "Return",
                color = Color(0xFFC48E08),
                shape = CutCornerShape(50),
                onClick = {
                    val view = BlockInformation(blocksToAdd, UUID.randomUUID())
                    blocksToAdd.add(Block(view.id, { Return(view) }, mutableStateOf("")))
                }
            )
            BlockCard(
                text = "For",
                color = cycle_color_1,
                shape = CutCornerShape(50),
                onClick = {
                    val view = BlockInformation(blocksToAdd, UUID.randomUUID())
                    blocksToAdd.add(Block(view.id, { For(view, scope, drawerState) }, mutableStateOf("")))
                    scope.launch { drawerState.close() }
                }
            )
            BlockCard(
                text = "While",
                color = cycle_color_2,
                shape = CutCornerShape(50),
                onClick = {
                    val view = BlockInformation(blocksToAdd, UUID.randomUUID())
                    blocksToAdd.add(Block(view.id, { While(view, scope, drawerState) }, mutableStateOf("")))
                    scope.launch { drawerState.close() }
                }
            )
            BlockCard(
                text = "IfBlock",
                color = condition_color_1,
                shape = CutCornerShape(50),
                onClick = {
                    val view = BlockInformation(blocksToAdd, UUID.randomUUID())
                    blocksToAdd.add(Block(view.id, { Condition(view, scope, drawerState) }, mutableStateOf("")))
                    scope.launch { drawerState.close() }
                }
            )
            BlockCard(
                text = "MyVariable",
                color = init_color_2,
                shape = RoundedCornerShape(50),
                onClick = {
                    val view = BlockInformation(blocksToAdd, UUID.randomUUID())
                    blocksToAdd.add(Block(view.id, { InitBlock(view) }, mutableStateOf("")))
                    scope.launch { drawerState.close() }
                }
            )
            BlockCard(
                text = "Assignment",
                color = assignment_color_1,
                shape = CutCornerShape(
                    topStartPercent = 100,
                    topEndPercent = 5,
                    bottomStartPercent = 5,
                    bottomEndPercent = 100
                ),
                onClick = {
                    val view = BlockInformation(blocksToAdd, UUID.randomUUID())
                    blocksToAdd.add(Block(view.id, { Assignment(view) }, mutableStateOf("")))
                    scope.launch { drawerState.close() }
                }
            )
            BlockCard(
                text = "Print",
                color = print_color_1,
                shape = RectangleShape,
                onClick = {
                    val view = BlockInformation(blocksToAdd, UUID.randomUUID())
                    blocksToAdd.add(Block(view.id, { PrintBlock(view) }, mutableStateOf("")))
                    scope.launch { drawerState.close() }
                }
            )
        }
    }
}
