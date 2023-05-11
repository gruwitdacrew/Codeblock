package com.example.mobile

import java.util.UUID

fun handleBlockDelete(blockId: UUID, blocks: MutableList<Block>) {
    val deleteBlock = blocks.find { it.id == blockId }
    blocks.remove(deleteBlock)
}