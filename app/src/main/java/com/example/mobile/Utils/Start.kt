package com.example.mobile.Utils

import com.example.mobile.RPS
import com.example.mobile.Variable
import com.example.mobile.lines
import com.example.mobile.variables
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@OptIn(ExperimentalSerializationApi::class)
fun start(item:String, dictionary:MutableMap<String,Variable> = variables):Variable{
    when(item[0]){
        'i' -> {
            val taskNow = item.substring(1).split(";")
            for(name in taskNow[1].split(",")){
                if(dictionary.containsKey(name)) return Variable("variable ${name} has already initialized","Exception")
                dictionary[name] = Variable("", taskNow[0])
            }
        }
        '=' -> {
            val (variable, expression) = item.substring(1).split("=", limit = 2)
            var name = ""; var indexes =  mutableListOf<Int>()
            var tempVariable:Variable;
            if(variable.contains('[')){
                name = variable.split('[')[0]
                val temp =  variable.split('[')
                for(i in 1 until temp.size){
                    tempVariable = RPS.calculate(dictionary,temp[i].substring(0,temp[i].length-1))
                    if(tempVariable.type == "Exception") return tempVariable
                    if(tempVariable.type != "Int") return Variable("required Int but got ${tempVariable.type}", "Exception")
                    indexes.add(tempVariable.value.toInt())
                    if(indexes[indexes.size-1] < 0) return Variable("Index out of range","Exception")
                }
            }
            tempVariable = RPS.calculate(dictionary, expression)
            if(tempVariable.type == "Exception") return tempVariable
            if(indexes.size > 0){
                var arrays = mutableListOf<String>()
                var listNow = Json.decodeFromString<MutableList<String>>(dictionary[name]!!.value)
                for(i in 0 until indexes.size){
                    arrays.add(Json.encodeToString(listNow))
                    if(listNow.size <= indexes[i]) return Variable("Index out of range","Exception")
                    if(i < indexes.size-2) listNow = Json.decodeFromString(Json.decodeFromString<List<String>>(arrays[arrays.size-1])[indexes[i]])
                }

                listNow[indexes[indexes.size-1]] = tempVariable.value

                for(i in indexes.size-1 downTo  0){
                    if(i == indexes.size-1) arrays[i] = Json.encodeToString(listNow)
                    else{
                        val temp = Json.decodeFromString<MutableList<String>>(arrays[i+1])
                        temp[indexes[i]] = arrays[i+1]
                        arrays[i] = Json.encodeToString(temp)
                    }
                }
                dictionary[name] = Translate.getVariable(arrays[0])
            }
            else dictionary[variable] = tempVariable
        }
        '?' -> {
            val taskNow = item.substring(1).split(":", limit=2).toMutableList()
            val(indexOfElse, condition) = taskNow[0].split(";")
            val newDictionary = dictionary.toMutableMap()
            if(RPS.calculate(newDictionary, condition).type == "Exception") return RPS.calculate(newDictionary, condition)
            if(indexOfElse.toInt() != -1){
                println("$indexOfElse ${taskNow[1]}")
                val ifActions = Json.decodeFromString<List<String>>(taskNow[1].substring(0,indexOfElse.toInt()))
                val elseActions = Json.decodeFromString<List<String>>(taskNow[1].substring(indexOfElse.toInt()+1))
                if(RPS.calculate(newDictionary, condition).value == "true"){
                    ifActions.forEach { action ->
                        val result = start(action,newDictionary)
                        if(result.type == "Exception") return result
                    }
                }
                else{
                    elseActions.forEach { action ->
                        val result = start(action,newDictionary)
                        if(result.type == "Exception") return result
                    }
                }
            }
            else{
                if(RPS.calculate(newDictionary, condition).value == "true"){
                    val ifActions = Json.decodeFromString<List<String>>(taskNow[1])
                    ifActions.forEach { action ->
                        val result = start(action,newDictionary)
                        if(result.type == "Exception") return result
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
            if(RPS.calculate(newDictionary, condition).type == "Exception") return RPS.calculate(newDictionary, condition)
            cycle@ while(RPS.calculate(newDictionary, condition).value == "true"){ //medium
                newDictionary["state"] = Variable("","String")
                for(i in actions){
                    val result = start(i,newDictionary)
                    if(result.type == "Exception") return result

                    if(newDictionary["state"]!!.value == "break") break@cycle
                    if(newDictionary["state"]!!.value == "continue") break
                }
            }
            newDictionary.remove("state")
            for((key, value) in newDictionary){
                if(dictionary.containsKey(key) && key != "state") dictionary[key] = value
            }
        }
        'f' -> {
            val taskNow = item.substring(1).split(":", limit = 2)
            val (variable, condition, action) = taskNow[0].split(";")
            val actions = Json.decodeFromString<List<String>>(taskNow[1])
            val newDictionary = dictionary.toMutableMap()
            var result = start(variable,newDictionary) //easy
            if(result.type == "Exception") return result
            if(RPS.calculate(newDictionary, condition).type == "Exception") return RPS.calculate(newDictionary, condition)
            cycle@while(RPS.calculate(newDictionary,condition).value == "true"){ //medium
                newDictionary["state"] = Variable("","String")
                for(i in actions){
                    result = start(i,newDictionary)
                    if(result.type == "Exception") return result

                    if(newDictionary["state"]!!.value == "break") break@cycle
                    if(newDictionary["state"]!!.value == "continue") break
                }
                result = start(action,newDictionary)
                if(result.type == "Exception") return result
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
        'a' -> {
            val (name, expression) = item.substring(1).split(";", limit = 2)
            if(!dictionary.containsKey(name)) return Variable("there is no $name variable", "Exception")
            if(!dictionary[name]!!.type.matches("Array<[a-zA-Z<>]+>".toRegex())) return Variable("$name is not iterated", "Exception")

            val listNow = Json.decodeFromString<MutableList<String>>(dictionary[name]!!.value)

            val variableNow = RPS.calculate(dictionary,expression)
            if(variableNow.type == "Exception") return variableNow
            if("${"(?<=<)[A-Za-z<>]+(?=>)".toRegex().find(dictionary[name]!!.type)?.value}" != variableNow.type) return Variable("arguments of array must have one type", "Exception")

            listNow.add(variableNow.value)
            dictionary[name] = Variable(Json.encodeToString(listNow), dictionary[name]!!.type)
        }
        'p' -> {
            val (name, expression) = item.substring(1).split(";", limit = 2)
            if(!dictionary.containsKey(name)) return Variable("there is no $name variable", "Exception")
            if(!dictionary[name]!!.type.matches("Array<[a-zA-Z<>]+>".toRegex())) return Variable("$name is not iterated", "Exception")

            val listNow = Json.decodeFromString<MutableList<String>>(dictionary[name]!!.value)

            val indexNow = RPS.calculate(dictionary,expression)
            if(indexNow.type == "Exception") return indexNow
            if(indexNow.type == "Int" && indexNow.value.toInt()>-1 && indexNow.value.toInt()<listNow.size){
                listNow.removeAt(indexNow.value.toInt())
                dictionary[name] = Variable(Json.encodeToString(listNow), dictionary[name]!!.type)
            }
            else return Variable("index out of range", "Exception")
        }
        'v' -> {
            val key = item.substring(1)
            val variableNow = RPS.calculate(dictionary,key)
            if(variableNow.type == "Exception") return variableNow
        }
        'r' -> {
            val taskNow = item.substring(1)
            val tempVariable = RPS.calculate(dictionary, taskNow)
            if(tempVariable.type == "Exception") return tempVariable
            dictionary["return"] = tempVariable
        }
        '/' -> {
            val taskNow = item.substring(1)
            val tempVariable = RPS.calculate(dictionary, taskNow)
            if(tempVariable.type == "Exception") return tempVariable
            lines.add(tempVariable.value)
        }
    }
    return Variable("", "Success")
}