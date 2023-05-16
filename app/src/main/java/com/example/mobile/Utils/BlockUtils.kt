package com.example.mobile

import java.util.UUID

fun handleBlockDelete(blockId: UUID, blocks: MutableList<Block>) {
    println(blocks + " " + blockId)
    val deleteBlock = blocks.find { it.id == blockId }
    blocks.remove(deleteBlock)
}