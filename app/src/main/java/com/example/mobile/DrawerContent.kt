package com.example.mobile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.mobile.ui.theme.Condition
import com.example.mobile.ui.theme.For
import com.example.mobile.ui.theme.While
import kotlinx.coroutines.CoroutineScope
import java.util.UUID

@Composable
fun DrawerContent(
    scope: CoroutineScope,
    drawerState: DrawerState,
) {
    Column(
        Modifier
            .padding(25.dp)
            .fillMaxSize(),

        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        BlockCard(
            text = "If Else",
            color = Color(0xFFC48E08),
            shape = CutCornerShape(50),
            onClick = {
                val index = UUID.randomUUID()
                blocksToAdd.add(Block(index, { Condition(index, scope, drawerState, blocksToAdd) }, mutableStateOf("")))
            }
        )
        BlockCard(
            text = "For",
            color = Color(0xFFC48E08),
            shape = CutCornerShape(50),
            onClick = {
                val index = UUID.randomUUID()
                blocksToAdd.add(Block(index, { For(index, scope, drawerState, blocksToAdd) }, mutableStateOf("")))
            }
        )
        BlockCard(
            text = "While",
            color = Color(0xFFC48E08),
            shape = CutCornerShape(50),
            onClick = {
                val index = UUID.randomUUID()
                blocksToAdd.add(Block(index, { While(index, scope, drawerState, blocksToAdd) }, mutableStateOf("")))
            }
        )
        BlockCard(
            text = "MyVariable",
            color = Color(0xFFC40869),
            shape = RoundedCornerShape(50),
            onClick = {
                val index = UUID.randomUUID()
                blocksToAdd.add(Block(index, { InitBlock(index, blocksToAdd) }, mutableStateOf("")))
            }
        )
        BlockCard(
            text = "Assignment",
            color = Color(0xFF09AA03),
            shape = CutCornerShape(
                topStartPercent = 100,
                topEndPercent = 5,
                bottomStartPercent = 5,
                bottomEndPercent = 100
            ),
            onClick = {
                val index = UUID.randomUUID()
                blocksToAdd.add(Block(index, { Assignment(index, blocksToAdd) }, mutableStateOf("")))
            }
        )
        BlockCard(
            text = "Print",
            color = Color(0xFF03A9F4),
            shape = RectangleShape,
            onClick = {
                val index = UUID.randomUUID()
                blocksToAdd.add(Block(index, { PrintBlock(index, blocksToAdd) }, mutableStateOf("")))
            }
        )
    }
}
