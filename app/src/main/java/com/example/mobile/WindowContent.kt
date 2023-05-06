package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WindowContent(
    scope: CoroutineScope,
    drawerState: DrawerState,
    countBlocks: MutableState<Int>,
    blocksToRender: MutableList<@Composable () -> Unit>
) {

    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { ModalContent() }
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .border(width = 3.dp, color = Color.Black)
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .background(Color(0xFFE7ECE6))
                    .border(width = 3.dp, color = Color.Black)
                    .onGloballyPositioned { coordinates ->
                        // Set column height using the LayoutCoordinates
                        height = (coordinates.size.height * 0.85).dp
                        width = (coordinates.size.width * 0.8).dp
                    },
            )
            {
                blocksToRender.forEach { block ->
                    block()
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFF378AA3)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            )
            {
                FloatingActionButton(
                    onClick = {
                        val temp = tasks.toList();
                        for (item in temp) {
                            println(item)
                            if (item.contains('=')) {
                                variables[item.split("=")[0]] =
                                    RPS.fromRPS(variables, RPS.toRPS(item.split("=")[1]))
                            }
                        }
                        for ((key, item) in variables) {
                            println("$key $item")
                        }
                        // Отобразить модальное окно
                        coroutineScope.launch {
                            sheetState.show()
                        }

                    },
                    modifier = Modifier
                        .padding(15.dp)
                        .size(100.dp, 60.dp),
                    backgroundColor = Color.Black,
                    shape = RoundedCornerShape(15),
                ) {
                    Text(text = "Консоль", color = Color(0xFFFFFFFF), fontSize = 12.sp)
                }
                Button(
                    onClick = { scope.launch { drawerState.open() } },
                    modifier = Modifier
                        .padding(15.dp)
                        .size(60.dp, 60.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF000000)),
                    shape = RoundedCornerShape(15),
                )
                {
                    Text(text = "+", color = Color(0xFFFFFFFF), fontSize = 24.sp)
                }
            }
        }
    }

}


@Composable
fun ModalContent() {
    var text by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .padding(20.dp)
            .height(40.dp)
    ) {
        Text(text = "Вывод")
    }
}