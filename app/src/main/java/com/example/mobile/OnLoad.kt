package com.example.mobile

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.util.UUID

data class Block (val id: UUID = UUID.randomUUID(), var element: @Composable () -> Unit, var expression: MutableState<String>)
{
    var offset = mutableStateOf(0.dp)
}
var variables = mutableMapOf<String, String>()
var blocks: MutableList<Block> =  mutableStateListOf()
var blocksToAdd: MutableList<Block> =  blocks

@Composable
fun OnLoad() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
            WindowContent(scope, drawerState)
        }
    )
}