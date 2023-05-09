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
fun InitBlock(
    variables: MutableMap<String, String>,
    blockId: Int,
) {
    var key by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("0") }

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
                        key = it.trim()
                        if (key.isNotEmpty()) {
                            val oldKey = variables.keys.firstOrNull { it != key && it.startsWith(key) }
                            if (oldKey != null) {
                                variables.remove(oldKey)
                            }
                            variables[key] = ""
                        }
                        println(variables)
                    },
                    label = { Text("Название переменной") },
                    modifier = Modifier.weight(1f) // Занимает всю доступную ширину
                )
                IconButton(
                    onClick = {
                        print("-------------------------------------------------------\n")
                        println("$blockId= blockId ")
                        val deleteBlock = blocksToRender.find { it.id == blockId }
                        println(blocksToRender[deleteBlock!!.id ].content.toString())
                        blocksToRender.removeAt(deleteBlock!!.id )
                        println(blocksToRender.count())

                        blocksToRender.forEachIndexed { index, block ->
                            block.id = index
                            println(block.id.toString() + " " + block.content.toString())
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