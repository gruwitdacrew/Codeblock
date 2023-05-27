package com.example.mobile.Utils

import androidx.lifecycle.ViewModel
import com.example.mobile.Block
import java.util.UUID

class BlockInformation(newBlocks: MutableList<Block>, newId: UUID, newNames: String = "", newChilds: MutableMap<String, MutableList<Block>> = mutableMapOf()) : ViewModel() {
    val blocks = newBlocks
    val id = newId
    var namesOfParentsBlocks = newNames
    var childs: MutableMap<String, MutableList<Block>> = newChilds
}