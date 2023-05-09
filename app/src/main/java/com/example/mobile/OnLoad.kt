package com.example.mobile

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

var tasks = ArrayDeque<String>()
var variables = mutableMapOf<String, String>()
//val blocksToRender: MutableList<@Composable () -> Unit> = mutableStateListOf()
data class Block(var id: Int, val content: @Composable () -> Unit)
val blocksToRender: MutableList<Block> = mutableStateListOf()

var idCounter = 0
//val blocksToRender: MutableList<Pair<Int, @Composable () -> Unit>> = mutableListOf()
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
                countBlocks,
                variables = variables
            )

        },
        content = {
            WindowContent(scope, drawerState, countBlocks)
        }
    )

}