package com.example.mobile.Utils

import com.example.mobile.RPS
import com.example.mobile.Variable
import com.example.mobile.variables
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@OptIn(ExperimentalSerializationApi::class)
fun start(item:String, lines:MutableList<String> = mutableListOf(), dictionary:MutableMap<String,Variable> = variables){
    when(item[0]){
        'i' -> {
            val taskNow = item.substring(1).split(";")
            dictionary[taskNow[1]] = Variable("", taskNow[0])
        }
        '=' -> {
            val (variable, expression) = item.substring(1).split("=", limit = 2);
            var name = ""; var index =  -1;
            if(variable.contains('[')){
                name = variable.split('[')[0]
                val temp =  variable.split('[')[1]
                index = RPS.calculate(dictionary, temp.substring(0, temp.length-1)).value.toInt()
            }
//            if(expression[0] == '['){
//                val elements = expression.substring(1,expression.length-1).split(",").toMutableList()
//
//                for((index, element) in elements.withIndex()){
//                    elements[index] = RPS.calculate(dictionary,element).value
//                }
//                val type = if(dictionary.containsKey(variable)) dictionary[variable]!!.type else "Array<${Translate.getType(elements[0])}>"
//                dictionary[variable] = Variable(Json.encodeToString(elements), type)
//            }
//            else{
//                if(index != -1){
//                    var listNow = Json.decodeFromString<MutableList<String>>(dictionary[name]!!.value)
//                    listNow[index] = RPS.calculate(dictionary, expression).value
//                    dictionary[name] = Variable(Json.encodeToString(listNow), "Array<${Translate.getType(listNow[0])}>")
//                }
//                else dictionary[variable] = RPS.calculate(dictionary, expression)
//            }
            if(index != -1){
                var listNow = Json.decodeFromString<MutableList<String>>(dictionary[name]!!.value)
                listNow[index] = RPS.calculate(dictionary, expression).value
                dictionary[name] = Variable(Json.encodeToString(listNow), "Array<${Translate.getType(listNow[0])}>")
            }
            else dictionary[variable] = RPS.calculate(dictionary, expression)
        }
        '?' -> {
            val taskNow = item.substring(1).split(":", limit=2).toMutableList()
            val(indexOfElse, condition) = taskNow[0].split(";")
            val newDictionary = dictionary.toMutableMap()
            if(indexOfElse.toInt() != -1){
                val ifActions = Json.decodeFromString<List<String>>(taskNow[1].substring(0,indexOfElse.toInt()-1))
                val elseActions = Json.decodeFromString<List<String>>(taskNow[1].substring(indexOfElse.toInt()))
                if(RPS.calculate(newDictionary, condition).value == "true"){
                    ifActions.forEach { action ->
                        start(action,lines,newDictionary)
                    }
                }
                else{
                    elseActions.forEach { action ->
                        start(action,lines,newDictionary)
                    }
                }
            }
            else{
                if(RPS.calculate(newDictionary, condition).value == "true"){
                    val ifActions = Json.decodeFromString<List<String>>(taskNow[1])
                    ifActions.forEach { action ->
                        start(action,lines,newDictionary)
                    }
                }
            }
            for((key, value) in newDictionary){
                if(dictionary.containsKey(key)) dictionary[key] = value;
            }
        }
        'w' -> {
            var taskNow = item.substring(1).split(":", limit = 2)
            val condition = taskNow[0];
            val actions = Json.decodeFromString<List<String>>(taskNow[1])
            val newDictionary = dictionary.toMutableMap()
            while(RPS.calculate(newDictionary, condition).value == "true"){
                for(i in actions){
                    start(i,lines,newDictionary)
                }
            }
            for((key, value) in newDictionary){
                if(dictionary.containsKey(key)) dictionary[key] = value;
            }
        }
        'f' -> {
            val taskNow = item.substring(1).split(":", limit = 2)
            val (variable, condition, action) = taskNow[0].split(";")
            val actions = Json.decodeFromString<List<String>>(taskNow[1])
            val newDictionary = dictionary.toMutableMap()
            start(variable,lines,newDictionary)
            while(RPS.calculate(newDictionary,condition).value == "true"){
                for(i in actions){
                    start(i,lines,newDictionary)
                }
                start(action,lines,newDictionary)
            }
            for((key, value) in newDictionary){
                if(dictionary.containsKey(key)) dictionary[key] = value;
            }
        }
        '*' -> {
            val taskNow = item.substring(1).split(";", limit = 3)
            val type = taskNow[0]
            val name = taskNow[1]
            val arguments = Json.decodeFromString<MutableList<String>>(taskNow[2].split(":",limit=2)[0])
            val actions = Json.decodeFromString<List<String>>(taskNow[2].split(":",limit=2)[1])

            for((key, item) in dictionary){
                arguments.add("=${key}=${item.value}")
            }

            dictionary[name] = Variable("${Json.encodeToString(arguments).length};${Json.encodeToString(arguments)}:${Json.encodeToString(actions)}",type)
            println("$type $name $arguments $actions $dictionary")
        }
        'r' -> {
            val taskNow = item.substring(1)
            dictionary["return"] = RPS.calculate(dictionary, taskNow)
        }
        '/' -> {
            val taskNow = item.substring(1)
            lines.add(RPS.calculate(dictionary,taskNow).value)
        }
    }
}