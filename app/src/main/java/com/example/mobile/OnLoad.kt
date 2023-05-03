package com.example.mobile


import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

var tasks = ArrayDeque<String>()
var variables = mutableMapOf<String, String>()

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
            DrawerContent(countBlocks)
        },
        content =
        {
            WindowContent(scope, drawerState, countBlocks)
        }
    )
}