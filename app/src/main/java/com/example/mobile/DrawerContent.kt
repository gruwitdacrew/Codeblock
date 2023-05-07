package com.example.mobile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DrawerContent(
    countBlocks: MutableState<Int>,
    blocksToRender: MutableList<@Composable () -> Unit>,
    variables: MutableMap<String, String>
) {
    var selectedBlock by remember { mutableStateOf(0) }

    Column(
        Modifier
            .padding(25.dp)
            .fillMaxSize(),

        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        BlockCard(
            text = "IF Block",
            color = Color(0xFFC48E08),
            shape = CutCornerShape(50),
            onClick = {
                selectedBlock = 1
            }
        )
        BlockCard(
            text = "My variable",
            color = Color(0xFFC40869),
            shape = RoundedCornerShape(50),
            onClick = {
                selectedBlock = 2
                blocksToRender.add { InitBlock(variables = variables) }
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
                selectedBlock = 3
                blocksToRender.add { Assignment(1) }
            }
        )
    }
    println(blocksToRender)

}

