package com.example.mobile

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.UUID


@Composable
fun PrintBlock(
    blockId: UUID,
    blocks:MutableList<Block>
) {
    var text by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.LightGray
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Print", modifier = Modifier.padding(14.dp))
                TextField(
                    value = text,
                    onValueChange = { newText ->
                        text = newText
//                        blocks[blockId].expression.value = "/$text"
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp)
                        .background(Color.Transparent)
                )
                IconButton(
                    onClick = {
                        print("-------------------------------------------------------\n")
                        println("$blockId= blockId $text")
                        val deleteBlock =  blocks.find{ it.id == blockId}
                        blocks.remove(deleteBlock)
//                        println(blocks[deleteBlock!!.id].element.toString())
//                        blocks.removeAt(deleteBlock!!.id)
                        println(blocks.count())

//                        blocks.forEachIndexed { index, block ->
//                            block.id = index
//                        }
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "delete")
                }
            }
        }
    }
}