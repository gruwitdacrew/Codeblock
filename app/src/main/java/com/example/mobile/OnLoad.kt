package com.example.mobile

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
data class Block (var id: Int, var element: @Composable () -> Unit, var expression: MutableState<String>)
var variables = mutableMapOf<String, String>()
var blocksToRender: MutableList<Block> =  mutableStateListOf()
var blocksToAdd: MutableList<Block> =  blocksToRender
var offsetsY = mutableListOf<Float>(0f)

@Composable
fun OnLoad() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var countBlocks = remember {
        mutableStateOf(0)
    }

    ModalDrawer(
        drawerState = drawerState,
        drawerBackgroundColor = Color(0x80FFFFFF),
        drawerContentColor = Color(0xFF000000),
        gesturesEnabled = true,
        modifier = Modifier,
        drawerContent = {
            DrawerContent(
                scope,
                drawerState
            )
        },
        content =
        {
            WindowContent(scope, drawerState, countBlocks)
        }
    )
}