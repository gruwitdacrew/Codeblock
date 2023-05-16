package com.example.mobile

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import java.util.UUID

data class Block (val id: UUID, var element: @Composable () -> Unit, var expression: MutableState<String>)
{
    var offset = mutableStateOf(0.dp)
}
data class Variable(var value: String, val type: String)
var variables = mutableMapOf<String, Variable>()
var blocks: MutableList<Block> =  mutableStateListOf()
var blocksToAdd: MutableList<Block> =  blocks

lateinit var localDensity: Density
@OptIn(ExperimentalComposeUiApi::class)
lateinit var keyboardController: SoftwareKeyboardController
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OnLoad() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    localDensity = LocalDensity.current
    keyboardController = LocalSoftwareKeyboardController.current!!

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