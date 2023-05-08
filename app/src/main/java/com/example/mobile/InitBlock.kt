package com.example.mobile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InitBlock(variables: MutableMap<String, String>) {
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
            modifier = Modifier.padding(12.dp), // Уменьшаем значение padding
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
            Spacer(modifier = Modifier.height(12.dp)) // Уменьшаем значение height
        }
    }
}