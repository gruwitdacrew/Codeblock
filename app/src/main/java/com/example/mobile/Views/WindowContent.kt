package com.example.mobile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.Utils.start
import com.example.mobile.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

var light = mutableStateOf(true)

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun WindowContent(
    scope: CoroutineScope,
    drawerState: DrawerState,
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    val localFocusManager = LocalFocusManager.current
    var offsetX by remember { mutableStateOf(0f) }
    var debugBlockNumber = 0

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { ModalContent(lines) },
        sheetShape = RoundedCornerShape(
            topStartPercent = 20,
            topEndPercent = 20
        ),
        sheetElevation = 30.dp,
        sheetBackgroundColor = Color.Black,
        sheetContentColor = Color.Green,
        scrimColor = Color(0x7A8EAFF1)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .border(width = 3.dp, color = Color.Black),
        )
        {
            LazyColumn(
                modifier = Modifier
                    .weight(0.9f)
                    .fillMaxWidth()
                    .paint(
                        painter = painterResource(screen.value),
                        contentScale = ContentScale.Crop,
                        alpha = alpha.value,
                    )
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { localFocusManager.clearFocus() }
                        )
                    }
            )
            {
                items(items = blocksToRender, key = { it.id })
                { block ->
                    if (!block.visibleState.currentState && !block.visibleState.targetState) blocksToRender.remove(block)
                    AnimatedVisibility(
                        modifier = Modifier
                            .animateItemPlacement(),
                        visibleState = block.visibleState,
                        enter = scaleIn(animationSpec = tween(durationMillis = 100)),
                        exit = scaleOut(animationSpec = tween(durationMillis = 100)),
                    )
                    {
                        block.element()
                    }
                }
                println(blocksToRender.size)
//                items(
//                    count = blocksToRender.size,
//                    key = {
//                        blocksToRender[it].id
//                    },
//                    itemContent = { index ->
//                        AnimatedVisibility(
//                            modifier = Modifier.animateItemPlacement(),
//                            visibleState = blocksToRender[index].visibleState,
//                            enter = scaleIn(animationSpec = tween(durationMillis = 100, easing = LinearEasing)),
//                            exit = scaleOut(animationSpec = tween(durationMillis = 100)),
//                        )
//                        {
//                            blocksToRender[index].element()
//                        }
//                    }
//                )
//                for (i in blocksToRender.withIndex())
//                {
//                    i.value.element()
//                }

            }

            Row(
                modifier = Modifier
                    .heightIn(min = 90.dp)
                    .fillMaxWidth()
                    .background(
                        color = bottom_bar_color,
                    )
                    .border(width = 3.dp, color = Color.Black),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                FloatingActionButton(
                    onClick = {
                        val finalTasks = blocksToRender.toList()
                        lines.clear()
                        variables.clear()
                        start("*Array<Int>;bubbleSort;[\"iArray<Int>;arr\",\"iInt;size\"]:[\"f=i=0;i<size-1;=i=i+1:[\\\"f=j=0;j<size-1;=j=j+1:[\\\\\\\"?-1;arr[j]>arr[j+1]:[\\\\\\\\\\\\\\\"=b=arr[j]\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"=arr[j]=arr[j+1]\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"=arr[j+1]=b\\\\\\\\\\\\\\\"]\\\\\\\"]\\\"]\",\"rarr\"]")
                        if (light.value)
                        {
                            for ((index, element, item) in finalTasks) {
                                if (item.value.length > 1) {
                                    start(item.value)
                                }
                            }
                        }
                        else if (blocksToRender.size>=1){
                            for (j in 0..debugBlockNumber) {
                                if (finalTasks[j].expression.value.length > 1) {
                                    start(finalTasks[j].expression.value)
                                }
                            }
                            blocksToRender[debugBlockNumber].onDebug.value = false
                            if (debugBlockNumber < blocksToRender.size - 1) {
                                debugBlockNumber++
                                blocksToRender[debugBlockNumber].onDebug.value = true
                            } else {
                                blocksToRender[debugBlockNumber].onDebug.value = false
                                debugBlockNumber = 0
                                blocksToRender[debugBlockNumber].onDebug.value = true
                            }
                        }
                        // Отобразить модальное окно
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 50.dp)
                        .size(80.dp, 60.dp)
                        .background(
                            brush = Brush.linearGradient(
                                listOf(
                                    color_on_change_theme1.value,
                                    color_on_change_theme2.value
                                )
                            ),
                            shape = RoundedCornerShape(55)
                        )
                        .offset { IntOffset(offsetX.toInt(), 0) }
                        .pointerInput(Unit)
                        {
                            detectDragGesturesAfterLongPress(
                                onDragStart =
                                {
                                    localFocusManager.clearFocus()
                                },
                                onDragEnd =
                                {
                                    if (offsetX >= 80f) {
                                        if (light.value) {
                                            DarkTheme()
                                            light.value = false
                                            if (blocksToRender.size >= 1) blocksToRender[0].onDebug.value = true
                                        } else {
                                            LightTheme()
                                            light.value = true
                                            if (blocksToRender.size >= 1) blocksToRender[debugBlockNumber].onDebug.value =
                                                false
                                            debugBlockNumber = 0
                                        }
                                    }
                                    offsetX = 0f
                                },
                            )
                            { change, dragAmount ->
                                change.consume()
                                if (offsetX + dragAmount.x in 0f..90f) offsetX += dragAmount.x
                            }
                        }
                        .border(
                            width = 3.dp,
                            brush = Brush.linearGradient(
                                listOf(
                                    cycle_color_1,
                                    cycle_color_2
                                )
                            ),
                            shape = RoundedCornerShape(55)
                        ),
                    backgroundColor = Color.Black,
                    shape = RoundedCornerShape(50),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.compile),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                Button(
                    onClick = {
                        chooseNow.value  = "global"
                        blocksToAdd = blocksToRender
                        scope.launch { drawerState.open() }
                        if (blocksToRender.size >= 1 && !light.value) {
                            blocksToRender[0].onDebug.value = true
                            debugBlockNumber = 0
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 50.dp)
                        .size(60.dp, 60.dp)
                        .border(
                            width = 3.dp,
                            brush = Brush.linearGradient(
                                listOf(
                                    condition_color_1,
                                    condition_color_2
                                )
                            ),
                            shape = RoundedCornerShape(55)
                        ),
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    shape = RoundedCornerShape(50),
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}
@Composable
fun ModalContent(lines: MutableList<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .defaultMinSize(50.dp, 50.dp)
    )
    {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(0.5f),
            contentPadding = PaddingValues(vertical = 8.dp),
        )
        {
            items(lines)
            { line ->
                Text(text = line, textAlign = TextAlign.Start, fontFamily = FontFamily.SansSerif)
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp),
        )
        {
            items(lines)
            {
            }
            if (!light.value) {
                items(variables.toList())
                { line ->
                    Text(
                        text = "${line.first} = ${if (line.second.type.length<line.second.value.length) line.second.type else line.second.value}",
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.End,
                    )
                }
            }
        }
    }
}

//WindowContnet в котором работет скролл но вылетает при клике на консоль
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun WindowContent(
//    scope: CoroutineScope,
//    drawerState: DrawerState,
//) {
//    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
//    val coroutineScope = rememberCoroutineScope()
//    var lines = remember{ mutableStateListOf<String>() }
//    ModalBottomSheetLayout(
//        sheetState = sheetState,
//        sheetContent = { ModalContent(lines) }
//    )
//    {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .border(width = 3.dp, color = Color.Black)
//        )
//        {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight(0.9f)
//                    .background(Color(0xFFE7ECE6))
//                    .border(width = 3.dp, color = Color.Black)
//                    .onGloballyPositioned { coordinates ->
//                        height = (coordinates.size.height * 0.85).dp
//                        width = (coordinates.size.width * 0.8).dp
//                    },
//                verticalArrangement = Arrangement.spacedBy(10.dp)
//            )
//            {
//                items(blocksToRenderToRender) { block ->
//                    block.element()
//                }
//            }
//
//            Row(
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .fillMaxWidth()
//                    .background(color = Color(0xFF378AA3)),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceEvenly,
//            )
//            {
//                FloatingActionButton(
//                    onClick = {
//                        val finalTasks = blocksToRenderToRender.toList();
//                        lines.clear()
//                        for ((index, element, item) in finalTasks){
//                            start(item.value,lines)
//                        }
//
//                        // Отобразить модальное окно
//                        coroutineScope.launch {
//                            sheetState.show()
//                        }
//                    },
//                    modifier = Modifier
//                        .padding(15.dp)
//                        .size(100.dp, 60.dp),
//                    backgroundColor = Color.Black,
//                    shape = RoundedCornerShape(15),
//                ) {
//                    Text(text = "Консоль", color = Color(0xFFFFFFFF), fontSize = 12.sp)
//                }
//                Button(
//                    onClick = {
//                        blocksToRenderToAdd = blocksToRenderToRender
//                        scope.launch { drawerState.open() }
//                    },
//                    modifier = Modifier
//                        .padding(15.dp)
//                        .size(60.dp, 60.dp),
//                    colors = ButtonDefaults.buttonColors(Color(0xFF000000)),
//                    shape = RoundedCornerShape(15),
//                )
//                {
//                    Text(text = "+", color = Color(0xFFFFFFFF), fontSize = 24.sp)
//                }
//            }
//        }
//    }
//}