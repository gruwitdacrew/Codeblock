package com.example.mobile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DrawerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.ui.theme.Condition
import kotlinx.coroutines.CoroutineScope
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
            text = "IfBlock(in dev)",
            color = Color(0xFFC48E08),
            shape = CutCornerShape(50),

            onClick = {
                val index = blocksToAdd.size
                blocksToAdd.add(Block(index, { Condition(index, scope, drawerState, blocksToAdd) }, mutableStateOf("")))
                offsetsY.add(0.dp)
                println(offsetsY)
            }
        )
        BlockCard(
            text = "My variable",
            color = Color(0xFFC40869),
            shape = RoundedCornerShape(50),
            onClick = {
                val index = blocksToAdd.size
                blocksToAdd.add(Block(index, { InitBlock(index, blocksToAdd) }, mutableStateOf("")))
                offsetsY.add(0.dp)
                println(offsetsY)
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
                val index = blocksToAdd.size
                blocksToAdd.add(Block(index, { Assignment(index, blocksToAdd) }, mutableStateOf("")))
                offsetsY.add(0.dp)
                println(offsetsY)
            }
        )
        BlockCard(
            text = "Print",
            color = Color(0xFF03A9F4),
            shape = RectangleShape,
            onClick = {
                val index = blocksToAdd.size
                blocksToAdd.add(Block(index, { PrintBlock(index, blocksToAdd) }, mutableStateOf("")))
                offsetsY.add(0.dp)
                println(offsetsY)
            }
        )
    }
}
