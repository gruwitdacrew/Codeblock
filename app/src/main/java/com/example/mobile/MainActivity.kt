package com.example.mobile

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


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
@Composable
fun OnLoad() {
    Column() {
        var countBlocks by remember { mutableStateOf (0) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .background(Color.Gray)
                .onGloballyPositioned { coordinates ->
                    // Set column height using the LayoutCoordinates
                    height = with(LocalDensity) { (coordinates.size.height*0.9).dp }
                    width = with(LocalDensity) { coordinates.size.width.dp }
                },

        )
        {
            repeat(countBlocks)
            {
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
