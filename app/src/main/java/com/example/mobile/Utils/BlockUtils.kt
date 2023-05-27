package com.example.mobile

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

fun handleBlockDelete(blockId: UUID, blocks: MutableList<Block>) {
//    val deleteBlock = blocks.find { it.id == blockId }
    var index = blocks.indexOf(blocks.find { it.id == blockId })
//    blocks.remove(deleteBlock)
    blocks[index].visibleState.targetState = false
}
fun putInPlace(offsetY: Dp, blockId: UUID, blocks: MutableList<Block>) {
    val index = blocks.indexOf(blocks.find { it.id == blockId })
    if (offsetY > 0.dp) {
        for (i in index + 1 until blocks.size) {
            if (blocks[index].offset.value <= blocks[i].offset.value) {
                blocks.add(i, blocks[index])
                blocks.removeAt(index)
                return
            }
        }
        blocks.add(blocks[index])
        blocks.removeAt(index)
    } else {
        for (i in index - 1 downTo 0) {
            if (blocks[index].offset.value >= blocks[i].offset.value) {
                blocks.add(i + 1, blocks[index])
                blocks.removeAt(index + 1)
                return
            }
        }
        blocks.add(0, blocks[index])
        blocks.removeAt(index + 1)
    }
    return
}
