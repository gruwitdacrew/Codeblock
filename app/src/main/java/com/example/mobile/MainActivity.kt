package com.example.mobile

import android.os.Bundle
import android.os.ConditionVariable
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.mobile.Blocks.Initialization
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnLoad()
        }
    }
}
var height = 0.dp
var width = 0.dp


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OnLoad() {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { ModalContent() }
    ) {
        Column() {
            var countBlocks by remember { mutableStateOf (0) }
            InitBlock(variables = variables)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .background(Color.Gray)
                    .onGloballyPositioned { coordinates ->
                        // Set column height using the LayoutCoordinates
                        height = with(LocalDensity) { (coordinates.size.height * 0.9).dp }
                        width = with(LocalDensity) { coordinates.size.width.dp }
                    }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Spacer(Modifier.weight(1f))
                    FloatingActionButton(
                        onClick = {
                            // Отобразить модальное окно
                            coroutineScope.launch {
                                sheetState.show()
                            }
                        },
                        shape = MaterialTheme.shapes.medium,
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onPrimary
                    ) {
                        Text(text = "Консоль")
                    }
                }

                repeat(countBlocks) {
                    MakeBlock()
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            )

            {

                Button(onClick = {
                    countBlocks++
                    println("new block created")
                },
                    modifier = Modifier
                        .padding(15.dp)
                        .size(60.dp, 60.dp)
                )
                {
                    Text(text = "+")
                }

            }
        }
    }

}

@Composable
fun ModalContent() {
    var text by remember { mutableStateOf("") }
    Column(modifier = Modifier
        .padding(20.dp)
        .height(40.dp)
    ) {
        Text(text = "Вывод")
    }
}





@Composable
fun ButtonAdd()
{
    Button(onClick = {
        //countBlocks++
        println("new block created")
    },
        modifier = Modifier
            .padding(15.dp)
            .size(60.dp, 60.dp)
    )
    {
        Text(text = "+")
    }
}

var variables = mutableMapOf<String, String>()

@Composable
fun InitBlock(variables: MutableMap<String, String>) {
    var key by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("0") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = key,
            onValueChange = {
                key = it.trim() // Удаляем лишние пробелы в начале и конце
                if (key.isNotEmpty()) {
                    val oldKey = variables.keys.firstOrNull { it != key && it.startsWith(key) }
                    if (oldKey != null) {
                        variables.remove(oldKey)
                    }
                    variables[key] = "" // Сохраняем только название переменной, без значения

                }
                println(variables)
            },
            label = { Text("Название переменной") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}
