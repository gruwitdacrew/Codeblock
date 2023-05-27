package com.example.mobile.Utils

import com.example.mobile.RPS
import com.example.mobile.Variable
import com.example.mobile.variables
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@OptIn(ExperimentalSerializationApi::class)
fun start(item:String, lines:MutableList<String> = mutableListOf(), dictionary:MutableMap<String,Variable> = variables): Variable?
{
    when(item[0]){
        'i' -> {
            val taskNow = item.substring(1).split(";")
            dictionary[taskNow[1]] = Variable("", taskNow[0])
        }
        '=' -> {
            val (variable, expression) = item.substring(1).split("=", limit = 2);
            var name = ""; var index =  -1
            if(variable.contains('[')){
                name = variable.split('[')[0]
                val temp =  variable.split('[')[1]
                index = RPS.calculate(dictionary, temp.substring(0, temp.length-1)).value.toInt() //easy
            }
            if(index != -1)
            {
                var listNow = Json.decodeFromString<MutableList<String>>(dictionary[name]!!.value)
                listNow[index] = RPS.calculate(dictionary, expression).value //easy
                dictionary[name] = Variable(Json.encodeToString(listNow), "Array<${Translate.getType(listNow[0])}>") //easy
                if (dictionary[name]?.type != "Exception")
                {
                    return dictionary[name]
                }
            }
            else dictionary[variable] = RPS.calculate(dictionary, expression) // easy
            if (dictionary[variable]?.type != "Exception")
            {
                return dictionary[variable]
            }
        }
        '?' -> {
            val taskNow = item.substring(1).split(":", limit=2).toMutableList()
            val(indexOfElse, condition) = taskNow[0].split(";")
            val newDictionary = dictionary.toMutableMap()
            if(indexOfElse.toInt() != -1){
                val ifActions = Json.decodeFromString<List<String>>(taskNow[1].substring(0,indexOfElse.toInt()-1))
                val elseActions = Json.decodeFromString<List<String>>(taskNow[1].substring(indexOfElse.toInt()))
                if(RPS.calculate(newDictionary, condition).value == "true"){ //medium
                    ifActions.forEach { action ->
                        start(action,lines,newDictionary) //easy
                    }
                }
                else{
                    elseActions.forEach { action ->
                        start(action,lines,newDictionary) //easy
                    }
                }
            }
            else{
                if(RPS.calculate(newDictionary, condition).value == "true"){
                    val ifActions = Json.decodeFromString<List<String>>(taskNow[1])
                    ifActions.forEach { action ->
                        start(action,lines,newDictionary) //easy
                    }
                }
            }
            for((key, value) in newDictionary){
                if(dictionary.containsKey(key)) dictionary[key] = value;
            }
        }
        'b' -> {
            dictionary["state"]!!.value = "break"
        }
        'c' -> {
            dictionary["state"]!!.value = "continue"
        }
        'w' -> {
            var taskNow = item.substring(1).split(":", limit = 2)
            val condition = taskNow[0];
            val actions = Json.decodeFromString<List<String>>(taskNow[1])
            val newDictionary = dictionary.toMutableMap()
            cycle@ while(RPS.calculate(newDictionary, condition).value == "true"){ //medium
                newDictionary["state"] = Variable("","String")
                for(i in actions){
                    start(i,lines,newDictionary) //easy
                    if(newDictionary["state"]!!.value == "break") break@cycle
                    if(newDictionary["state"]!!.value == "continue") break
                }
            }
            newDictionary.remove("state")
            for((key, value) in newDictionary){
                if(dictionary.containsKey(key) && key != "state") {
                    if (value.type == "Exception") {
                        return value
                    } else {
                        dictionary[key] = value
                    }
                }
            }
        }
        'f' -> {
            val taskNow = item.substring(1).split(":", limit = 2)
            val (variable, condition, action) = taskNow[0].split(";")
            val actions = Json.decodeFromString<List<String>>(taskNow[1])
            val newDictionary = dictionary.toMutableMap()
            start(variable,lines,newDictionary) //easy
            cycle@while(RPS.calculate(newDictionary,condition).value == "true"){ //medium
                newDictionary["state"] = Variable("","String")
                for(i in actions){
                    start(i,lines,newDictionary) //easy
                    if(newDictionary["state"]!!.value == "break") break@cycle
                    if(newDictionary["state"]!!.value == "continue") break
                }
                start(action,lines,newDictionary) //easy
            }
            for((key, value) in newDictionary){
                if(dictionary.containsKey(key))
                {
                    if (value.type == "Exception")
                    {
                        return value
                    }
                    else
                    {
                        dictionary[key] = value
                    }
                }
            }
        }
        '*' -> {
            val taskNow = item.substring(1).split(";", limit = 3)
            val type = taskNow[0]
            val name = taskNow[1]
            val arguments = Json.decodeFromString<MutableList<String>>(taskNow[2].split(":",limit=2)[0])
            val actions = Json.decodeFromString<List<String>>(taskNow[2].split(":",limit=2)[1])

            for((key, item) in dictionary)
            {
                if (item.type == "Exception")
                {
                    return item
                }
                else
                {
                    arguments.add("=${key}=${item.value}")
                }
            }

            dictionary[name] = Variable("${Json.encodeToString(arguments).length};${Json.encodeToString(arguments)}:${Json.encodeToString(actions)}",type)
            println("$type $name $arguments $actions $dictionary")
        }
        'r' -> {
            val taskNow = item.substring(1)
            dictionary["return"] = RPS.calculate(dictionary, taskNow) //easy
        }
        '/' -> {
            val taskNow = item.substring(1)
            lines.add(RPS.calculate(dictionary,taskNow).value) //easy
        }
    }
    return Variable("", "Success")
}