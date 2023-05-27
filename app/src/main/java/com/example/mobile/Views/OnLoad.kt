package com.example.mobile

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.example.mobile.ui.theme.drawer_content_color
import java.util.*

data class Block(
    val id: UUID,
    var element: @Composable () -> Unit,
    var expression: MutableState<String>
) {
    var offset = mutableStateOf(0.dp)
    val visibleState = MutableTransitionState(false).apply { targetState = true }
    var onDebug = mutableStateOf(false)
}

data class Variable(var value: String, val type: String)

var variables = mutableMapOf<String, Variable>()
var blocksToRender: MutableList<Block> = mutableStateListOf()
var blocksToAdd = blocksToRender
var alpha: MutableState<Float> = mutableStateOf(1f)
lateinit var localDensity: Density
val lines =  mutableStateListOf<String>()
val chooseNow = mutableStateOf("")

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
        drawerBackgroundColor = drawer_content_color,
        gesturesEnabled = false,
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