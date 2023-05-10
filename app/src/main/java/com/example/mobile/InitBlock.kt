package com.example.mobile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InitBlock(index: Int, blocks:MutableList<Block>,
) {
    var key by remember { mutableStateOf("") }

    Card(
        modifier = Modifier

            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.LightGray
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) { // Используем Row для размещения в одной строке
                TextField(
                    value = key,
                    onValueChange = {
                        key = it.trim() // Удаляем лишние пробелы в начале и конце
                        blocks[index].expression.value = "=$key=0"
                    },
                    label = { Text("Название переменной") },
                    modifier = Modifier.weight(1f) // Занимает всю доступную ширину
                )
                IconButton(
                    onClick = {
                        print("-------------------------------------------------------\n")
                        println("$index= blockId ")
                        val deleteBlock = blocks.find { it.id == index }
                        println(blocks[deleteBlock!!.id ].element.toString())
                        blocks.removeAt(deleteBlock!!.id )
                        println(blocksToRender.count())

                        blocksToRender.forEachIndexed { index, block ->
                            block.id = index
                            println(block.id.toString() + " " + block.element.toString())
                        }
                    },
                    modifier = Modifier.padding(start = 8.dp) // Изменяем отступ с помощью start
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "delete")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}