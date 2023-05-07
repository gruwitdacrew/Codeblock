package com.example.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun PrintBlock(String: String) {
    var text by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.LightGray
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Print", modifier = Modifier.padding(16.dp))
            TextField(
                value = text,
                onValueChange = { newText ->
                    text = newText
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .background(Color.Transparent)
            )
        }
    }
}
