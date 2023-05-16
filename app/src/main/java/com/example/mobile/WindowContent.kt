package com.example.mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.Utils.start
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WindowContent(
    scope: CoroutineScope,
    drawerState: DrawerState,
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val lines = remember{ mutableStateListOf<String>() }
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { ModalContent(lines) }
    )
    {
//        Image(painter = painterResource(id = R.xml.), contentDescription = null,
//            modifier = Modifier
//                .height(160.dp)
//                .width(160.dp)
//                .padding(32.dp),
//        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(width = 3.dp, color = Color.Black),
        )
        {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .paint(
                        painter = painterResource(id = R.drawable.screen),
                        contentScale = ContentScale.FillBounds
                    )
                    .border(width = 3.dp, color = Color.Black)
            )
            {
                items(items = blocks, key = {it.id}) { block ->
                    block.element()
                    //  println(block)
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFA7C3FF),
                    )
                    .border(width = 3.dp, color = Color.Black),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            )
            {
                FloatingActionButton(
                    onClick = {
                        val finalTasks = blocks.toList();
                        lines.clear()

                        for ((index, element, item) in finalTasks){
                            if (item.value.length > 1){
                                start(item.value,lines)
                            }
                        }

                        // Отобразить модальное окно
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    },
                    modifier = Modifier
                        .padding(15.dp)
                        .size(100.dp, 60.dp),
                    backgroundColor = Color(0xFF000000),
                    shape = RoundedCornerShape(15),
                ) {
                    Image(painter = painterResource(id = R.drawable.compile), contentDescription = null, contentScale = ContentScale.Fit)
                }
                Button(
                    onClick = {
                        blocksToAdd = blocks
                        scope.launch { drawerState.open() }
                    },
                    modifier = Modifier
                        .padding(15.dp)
                        .size(60.dp, 60.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF000000)),
                    shape = RoundedCornerShape(55),
                )
                {
                    Image(painter = painterResource(id = R.drawable.add), contentDescription = null, contentScale = ContentScale.Fit)
                }
            }
        }
    }
}

@Composable
fun ModalContent(lines: MutableList<String>) {
    Box(modifier = Modifier
        .padding(20.dp)
        .defaultMinSize(50.dp, 50.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(lines){line->
                Text(text = line)
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
//                items(blocksToRender) { block ->
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
//                        val finalTasks = blocksToRender.toList();
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
//                        blocksToAdd = blocksToRender
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