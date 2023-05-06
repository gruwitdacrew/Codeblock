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
        Card(
            onClick = {
                selectedBlock = 1
//                selectedBlocks.add(selectedBlock)
            },
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(),
            backgroundColor = Color(0xFFC48E08),
            elevation = 5.dp,
            shape = CutCornerShape(50)
        )
        {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            )
            {
                Text(text = "IF Block", color = Color(0xFFFFFFFF), fontSize = 24.sp)
            }
        }
        Card(
            onClick = {
                selectedBlock = 2
//                selectedBlocks.add(selectedBlock)
//            countBlocks.value++
                blocksToRender.add { InitBlock(variables = variables) }
            },
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(),
            backgroundColor = Color(0xFFC40869),
            elevation = 5.dp,
            shape = RoundedCornerShape(50)
        )
        {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            )
            {
                Text(text = "My variable", color = Color(0xFFFFFFFF), fontSize = 24.sp)
            }
        }
        Card(
            onClick = {
                selectedBlock = 3
                blocksToRender.add { Assignment(1) }
            },
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(),
            backgroundColor = Color(0xFF09AA03),
            elevation = 5.dp,
            shape = CutCornerShape(
                topStartPercent = 100,
                topEndPercent = 5,
                bottomStartPercent = 5,
                bottomEndPercent = 100
            )
        )
        {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            )
            {
                Text(text = "Assignment", color = Color(0xFFFFFFFF), fontSize = 24.sp)
            }
        }
    }

    println(blocksToRender)

}

