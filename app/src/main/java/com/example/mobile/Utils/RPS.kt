package com.example.mobile

import com.example.mobile.Utils.Translate
import com.example.mobile.Utils.start
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RPS {
    companion object{
        @JvmStatic
        public fun calculate(dictionary: MutableMap<String, Variable>, input: String): Variable{
            return fromRPS(dictionary, toRPS(input))
        }
        @JvmStatic
        public fun toRPS(input: String): String{
            println(input)
            var stack = ArrayDeque<String>()
            var answer = ""
            var charNow:Char; var isString = false; var isFunc = false
            for(i in 0 until input.length){
                charNow = input[i]
                if(charNow.toString().matches("[a-zA-Z0-9\"]".toRegex()) || isString){
                    answer+=charNow
                    if(charNow == '"')  isString = !isString
                    if(i < input.length-1 && input[i+1] == '(') isFunc = true
                }
                else if(charNow != ' ' && !(i > 0 && input[i-1].toString().matches("[<>=!]".toRegex()) && input[i] == '=')){
                    if(answer.length >0 && answer[answer.length-1] != ' ') answer+=" "
                    when(charNow){
                        ')' -> {
                            while(stack.size > 0 && stack.last() != "("){
                                answer+=(stack.last() + " ")
                                stack.removeLast()
                            }
                            if(isFunc) answer += "Function"
                            isFunc = false
                            stack.removeLast();
                        }
                        ']' -> {
                            while(stack.size > 0 && (stack.last() != "Array" && stack.last() != "index")){
                                answer+=(stack.last() + " ")
                                stack.removeLast()
                            }
                            answer += stack.last()
                            stack.removeLast();
                        }
                        '|', '&', ',' -> {
                            while(stack.size > 0 && stack.last().matches("[+/*%<>=|&-]+".toRegex())) {
                                answer+=(stack.last() + " ")
                                stack.removeLast()
                            }
                            stack.addLast(charNow.toString())
                        }
                        '<','>','=','!' -> {
                            while(stack.size > 0 && stack.last().matches("[+/*%<>=-]+".toRegex())) {
                                answer+=(stack.last() + " ")
                                stack.removeLast()
                            }
                            if(input[i+1] == '=') stack.addLast(charNow + "=")
                            else stack.addLast(charNow.toString())
                        }
                        '+', '-' -> {
                            if(input[i-1] == '('){
                                answer+=charNow;
                            }
                            else{
                                while(stack.size > 0 && stack.last().matches("[+/*%-]".toRegex())) {
                                    answer+=(stack.last() + " ")
                                    stack.removeLast()
                                }
                                stack.addLast(charNow.toString())
                            }
                        }
                        '[' -> {
                            if(i==0 || input[i-1].toString().matches("[,\\(\\[]".toRegex())) stack.addLast("Array")
                            else stack.addLast("index")
                        }
                        else -> stack.addLast(charNow.toString())
                    }
                }
            }

            while(stack.size > 0){
                if(answer[answer.length-1] != ' ') answer+=" "
                answer+=(stack.last())
                stack.removeLast()
            }
            return answer
        }

        private fun booleanToInt(input:Boolean):Int{
            return if (input) 1 else 0
        }

        @OptIn(ExperimentalSerializationApi::class)
        private fun operation(item: String, firstVariable: Variable, secondVariable: Variable = Variable("null","")):Variable{
            when(item){
                "-" ->{
                    return Variable((secondVariable.value.toInt() - firstVariable.value.toInt()).toString(), "Int")
                }
                "+" -> {
                    if(firstVariable.type == "String" || secondVariable.type == "String") return Variable((secondVariable.value + firstVariable.value), "String")
                    else if(firstVariable.type == "Int" && secondVariable.type == "Int") return Variable((secondVariable.value.toInt() + firstVariable.value.toInt()).toString(), "Int")
                    else return Variable("there is no + operation between ${firstVariable.type} ${secondVariable.type}","Exception")
                }
                "*" ->{
                    if(firstVariable.type == "Int" && secondVariable.type == "Int") return Variable((secondVariable.value.toInt() * firstVariable.value.toInt()).toString(), "Int")
                    return Variable("there is no * operation between ${firstVariable.type} ${secondVariable.type}","Exception")
                }
                "/" ->{
                    if(firstVariable.type == "Int" && secondVariable.type == "Int") return Variable((secondVariable.value.toInt() / firstVariable.value.toInt()).toString(), "Int")
                    return Variable("there is no / operation between ${firstVariable.type} ${secondVariable.type}","Exception")
                }
                "%" ->{
                    if(firstVariable.type == "Int" && secondVariable.type == "Int") return Variable((secondVariable.value.toInt() % firstVariable.value.toInt()).toString(), "Int")
                    return Variable("there is no % operation between ${firstVariable.type} ${secondVariable.type}","Exception")
                }
                "|" ->{
                    if(firstVariable.type.contains("Array") || secondVariable.type.contains("Array")) return Variable("there is no | operation between ${firstVariable.type} ${secondVariable.type}","Exception")
                    return Variable((Translate.toBool(secondVariable) || Translate.toBool(firstVariable)).toString(), "Bool")
                }
                "&" ->{
                    if(firstVariable.type.contains("Array") || secondVariable.type.contains("Array")) return Variable("there is no & operation between ${firstVariable.type} ${secondVariable.type}","Exception")
                    return Variable((Translate.toBool(secondVariable) || Translate.toBool(firstVariable)).toString(), "Bool")
                }
                ">" ->{
                    if(secondVariable.type == "String" && firstVariable.type == "String") return Variable((secondVariable.value > firstVariable.value).toString(), "Bool")
                    if(secondVariable.type == "Int" && firstVariable.type == "Int") return Variable((secondVariable.value.toInt() > firstVariable.value.toInt()).toString(), "Bool")
                    return Variable("there is no > operation between ${firstVariable.type} ${secondVariable.type}","Exception")
                }
                "<" ->{
                    if(secondVariable.type == "String" && firstVariable.type == "String") return Variable((secondVariable.value < firstVariable.value).toString(), "Bool")
                    if(secondVariable.type == "Int" && firstVariable.type == "Int") return Variable((secondVariable.value.toInt() < firstVariable.value.toInt()).toString(), "Bool")
                    return Variable("there is no < operation between ${firstVariable.type} ${secondVariable.type}","Exception")
                }
                ">=" ->{
                    if(secondVariable.type == "String" && firstVariable.type == "String") return Variable((secondVariable.value >= firstVariable.value).toString(), "Bool")
                    if(secondVariable.type == "Int" && firstVariable.type == "Int") return Variable((secondVariable.value.toInt() >= firstVariable.value.toInt()).toString(), "Bool")
                    return Variable("there is no >= operation between ${firstVariable.type} ${secondVariable.type}","Exception")
                }
                "<=" ->{
                    if(secondVariable.type == "String" && firstVariable.type == "String") return Variable((secondVariable.value <= firstVariable.value).toString(), "Bool")
                    if(secondVariable.type == "Int" && firstVariable.type == "Int") return Variable((secondVariable.value.toInt() <= firstVariable.value.toInt()).toString(), "Bool")
                    return Variable("there is no <= operation between ${firstVariable.type} ${secondVariable.type}","Exception")
                }
                "==" -> {
                    if(secondVariable.type == firstVariable.type) return Variable((secondVariable.value == firstVariable.value).toString(), "Bool")
                    return Variable("there is no == operation between ${firstVariable.type} ${secondVariable.type}","Exception")
                }
                "!=" -> {
                    if(secondVariable.type == firstVariable.type) return Variable((secondVariable.value == firstVariable.value).toString(), "Bool")
                    return Variable("there is no != operation between ${firstVariable.type} ${secondVariable.type}","Exception")
                }
                "index" ->{
                    if(secondVariable.type == "String" && firstVariable.type == "Int" && firstVariable.value.toInt()>-1 && firstVariable.value.toInt()<secondVariable.value.length)return Variable(secondVariable.value[firstVariable.value.toInt()].toString(),"String")
                    if(secondVariable.type.contains("Array") && firstVariable.type == "Int" && firstVariable.value.toInt()>-1 && firstVariable.value.toInt()<Json.decodeFromString<List<String>>(secondVariable.value).size) return Variable(Json.decodeFromString<List<String>>(secondVariable.value)[firstVariable.value.toInt()],"${"(?<=<)[A-Za-z<>]+(?=>)".toRegex().find(secondVariable.type)?.value}")
                    if(secondVariable.type == "String" || secondVariable.type.contains("Array")) return Variable("index out of range", "Exception")
                    return Variable("${secondVariable.type} is not iterated", "Exception")
                }
                "," -> {
                    if(firstVariable.type != "TempArray"){
                        return Variable(Json.encodeToString(mutableListOf(firstVariable.value, secondVariable.value)),"TempArray")
                    }
                    else{
                        var value = Json.decodeFromString<MutableList<String>>(firstVariable.value)
                        value.add(secondVariable.value)
                        return Variable(Json.encodeToString(value),"TempArray")
                    }
                }
                "Array" -> {
                    val value:List<String>
                    if(firstVariable.type == "TempArray"){
                        value = Json.decodeFromString<List<String>>(firstVariable.value).reversed()
                        var type = ""
                        for(i in value){
                            if(type.isEmpty()) type = Translate.getType(i)
                            else{
                                if(Translate.getType(i) != type) return Variable("arguments of array must have one type", "Exception")
                            }
                        }
                        return Variable(Json.encodeToString(value),"Array<$type>")
                    }
                    else{
                        value = listOf(firstVariable.value)
                        return Variable(Json.encodeToString(value),"Array<${firstVariable.type}>")
                    }
                }
                "Function" -> {
                    var values:List<String>
                    if(firstVariable.type == "TempArray") values = Json.decodeFromString<MutableList<String>>(firstVariable.value).reversed()
                    else values = listOf(firstVariable.value)
                    val(indexOfActions, blocks) = secondVariable.value.split(";", limit = 2)
                    val arguments = Json.decodeFromString<List<String>>(blocks.substring(0,indexOfActions.toInt()))
                    val actions = Json.decodeFromString<List<String>>(blocks.substring(indexOfActions.toInt()+1))
                    val newDictionary = mutableMapOf<String, Variable>()
                    for(index in arguments.indices){
                        if(index<values.size){
                            val variableNow = Translate.getVariable(values[index])
                            when(arguments[index][0]){
                                'i' -> {
                                    if(arguments[index].substring(1).split(";")[0] != variableNow.type){
                                        return Variable("Required ${arguments[index].substring(1).split(";")[0]} but got ${variableNow.type}", "Exception")
                                    }
                                    newDictionary[arguments[index].substring(1).split(";")[1]] = variableNow
                                }
                                '=' -> {
                                    newDictionary[arguments[index].substring(1).split("=", limit = 2)[0]] = variableNow
                                }
                            }
                        }
                        else{
                            val (variable, expression) = arguments[index].substring(1).split("=", limit = 2)
                            newDictionary[variable] = Translate.getVariable(expression)
                        }
                    }

                    for(item in actions){
                        start(item, newDictionary)
                        if(item[0] == 'r') break
                    }

                    if(newDictionary.containsKey("return")) return Variable(newDictionary["return"]!!.value, secondVariable.type)
                    else return Variable("This function doesn't have return", "Exception")
                }
            }
            return Variable("empty", "Exception")
        }
        @OptIn(ExperimentalSerializationApi::class)
        @JvmStatic
        public fun fromRPS(dictionary: MutableMap<String, Variable>, input: String) : Variable{
            println(input)
            val listOfActions = input.split(' ')
            var stack = ArrayDeque<Variable>()
            for(i in listOfActions){
                println("$stack $i")
                if(i.matches("([+/*%><|&=!,-]+)|(index)|(Function)|(Array)".toRegex())){
                    if(stack.size == 0 && i!="Function") return Variable("${i} must have more arguments","Exception")
                    var first = Variable("", "")
                    if(stack.size != 0){
                        first = stack.last()
                        stack.removeLast()
                    }
                    var second = first
                    if(i != "Array" && stack.size>0){
                        second = stack.last()
                        stack.removeLast()
                    }
                    stack.addLast(operation(i,first,second))
                    if(stack.last().type == "Exception") break
                }
                else if(i != " ") {
                    if (dictionary.containsKey(i)) stack.addLast(dictionary[i]!!)
                    else{
                        if(Translate.getVariable(i).type == "Exception") return Translate.getVariable(i)
                        stack.addLast(Translate.getVariable(i))
                    }
                }
            }
            return stack.first()
        }
    }
}