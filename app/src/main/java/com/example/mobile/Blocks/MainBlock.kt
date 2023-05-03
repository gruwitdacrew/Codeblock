package com.example.aplication.Logic

interface MainBlock {
    fun start()
    var status: Boolean
    var ErrorString: String

    companion object {
        val consoleOutput = mutableListOf<String>()
        val variables = mutableMapOf<String, Int>()
        val listOfBlocks = mutableListOf<MainBlock>()
        val MapArray = mutableMapOf<String, Array<Int>>()
        var index = 0
    }

}