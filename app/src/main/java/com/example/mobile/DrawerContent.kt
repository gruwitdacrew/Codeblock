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
            text = "IfBlock(in dev)",
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
                val blockIndex = blocksToRender.size
//                blocksToRender.add { InitBlock(variables = variables)}
                blocksToRender.add(Block(blocksToRender.size) {
                    InitBlock(variables = variables, blockIndex)
                })
//                blocksToRender.forEachIndexed { index, block ->
//                    block.id = index
//                }
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
//                blocksToRender.add { Assignment(1) }
            }
        )
        BlockCard(
            text = "Print",
            color = Color(0xFF03A9F4),
            shape = CutCornerShape(
                topStartPercent = 0,
                topEndPercent = 0,
                bottomStartPercent = 0,
                bottomEndPercent = 0
            ),
            onClick = {
                selectedBlock = 4
                val blockIndex = blocksToRender.size // Get the current size as the index
                blocksToRender.add(Block(blockIndex) {
                    PrintBlock("Hello", blockIndex)
                })
//                blocksToRender.forEachIndexed { index, block ->
//                    block.id = index
//                }
//                println(blockIndex)
//                println(blocksToRender.size)
//                println(blocksToRender)
            }
        )
    }
}

