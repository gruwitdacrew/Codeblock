package com.example.mobile.Utils

import com.example.mobile.Block
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
fun getExpression(
    forBlocks: MutableList<Block>,
    condition: String,
    variable: String,
    action: String,
    value: String,
): String {
    val actions = mutableListOf<String>()
    for (i in forBlocks) {
        actions.add(i.expression.value)
    }
    if (actions.size > 0) return "f=${variable}=${value};${condition};=${variable}=${action}:${
        Json.encodeToString(
            actions
        )
    }"
    else return ""
}

@OptIn(ExperimentalSerializationApi::class)
fun getExpression(
    name:String,
    type: String,
    functionBlocks:MutableList<Block>,
    argumentBlocks:MutableList<Block>,
):String{
    val actions = mutableListOf<String>(); val arguments = mutableListOf<String>()
    for(i in functionBlocks){
        actions.add(i.expression.value)
    }
    for(i in argumentBlocks){
        arguments.add(i.expression.value)
    }
    if(actions.size > 0 ) return "*${type};${name};${Json.encodeToString(arguments)}:${Json.encodeToString(actions)}"
    else return "";
}

@OptIn(ExperimentalSerializationApi::class)
fun getExpression(
    whileBlocks: MutableList<Block>,
    condition: String
): String {
    val actions = mutableListOf<String>()
    for (i in whileBlocks) {
        actions.add(i.expression.value)
    }
    if (actions.size > 0) return "w${condition}:${Json.encodeToString(actions)}"
    else return ""
}


@OptIn(ExperimentalSerializationApi::class)
fun getExpression(
    ifBlocks: MutableList<Block>,
    elseBlocks: MutableList<Block>,
    condition: String
): String {
    val ifActions = mutableListOf<String>()
    val elseActions = mutableListOf<String>()
    for (i in ifBlocks) {
        ifActions.add(i.expression.value)
    }
    for (i in elseBlocks) {
        elseActions.add(i.expression.value)
    }
    if (ifActions.size > 0 && elseActions.size > 0) {
        return "?${Json.encodeToString(ifActions).length};${condition}:${Json.encodeToString(ifActions)}:${
            Json.encodeToString(
                ifActions
            )
        }"
    } else if (ifActions.size > 0) {
        return "?${-1};${condition}:${Json.encodeToString(ifActions)}"
    } else return ""
}