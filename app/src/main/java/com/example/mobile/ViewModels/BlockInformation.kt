package com.example.mobile.Utils

import androidx.lifecycle.ViewModel
import com.example.mobile.Block
import java.util.UUID

class BlockInformation(newBlocks: MutableList<Block>, newId: UUID) : ViewModel() {
    val blocks = newBlocks
    val id = newId
}