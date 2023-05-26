package com.example.mobile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.mobile.ui.theme.Condition
import com.example.mobile.ui.theme.For
import com.example.mobile.ui.theme.While
import com.example.mobile.ui.theme.assignment_color_1
import com.example.mobile.ui.theme.condition_color_1
import com.example.mobile.ui.theme.cycle_color_1
import com.example.mobile.ui.theme.cycle_color_2
import com.example.mobile.ui.theme.init_color_2
import com.example.mobile.ui.theme.print_color_1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID

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
                text = "For",
                color = cycle_color_1,
                shape = CutCornerShape(50),
                onClick = {
                    val index = UUID.randomUUID()
                    blocksToAdd.add(
                        Block(
                            index,
                            { For(index, scope, drawerState, blocksToAdd) },
                            mutableStateOf("")
                        )
                    )
                    scope.launch { drawerState.close() }
                }
            )
            BlockCard(
                text = "While",
                color = cycle_color_2,
                shape = CutCornerShape(50),
                onClick = {
                    val index = UUID.randomUUID()
                    blocksToAdd.add(
                        Block(
                            index,
                            { While(index, scope, drawerState, blocksToAdd) },
                            mutableStateOf("")
                        )
                    )
                    scope.launch { drawerState.close() }
                }
            )
            BlockCard(
                text = "IfBlock",
                color = condition_color_1,
                shape = CutCornerShape(50),
                onClick = {
                    val index = UUID.randomUUID()
                    blocksToAdd.add(
                        Block(
                            index,
                            { Condition(index, scope, drawerState, blocksToAdd) },
                            mutableStateOf("")
                        )
                    )
                    scope.launch { drawerState.close() }
                }
            )
            BlockCard(
                text = "MyVariable",
                color = init_color_2,
                shape = RoundedCornerShape(50),
                onClick = {
                    val index = UUID.randomUUID()
                    blocksToAdd.add(
                        Block(
                            index,
                            { InitBlock(index, blocksToAdd) },
                            mutableStateOf("")
                        )
                    )
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
                    val index = UUID.randomUUID()
                    blocksToAdd.add(
                        Block(
                            index,
                            { Assignment(index, blocksToAdd) },
                            mutableStateOf("")
                        )
                    )
                    scope.launch { drawerState.close() }
                }
            )
            BlockCard(
                text = "Print",
                color = print_color_1,
                shape = RectangleShape,
                onClick = {
                    val index = UUID.randomUUID()
                    blocksToAdd.add(
                        Block(
                            index,
                            { PrintBlock(index, blocksToAdd) },
                            mutableStateOf("")
                        )
                    )
                    scope.launch { drawerState.close() }
                }
            )
        }
    }
}
