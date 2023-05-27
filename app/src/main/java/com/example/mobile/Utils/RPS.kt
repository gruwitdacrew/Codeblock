package com.example.mobile

import com.example.mobile.Utils.Translate
import com.example.mobile.Utils.start
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RPS {
    companion object {
        @JvmStatic
        fun calculate(dictionary: MutableMap<String, Variable>, input: String): Variable {
            return fromRPS(dictionary, toRPS(input))
        }

        @JvmStatic
        fun toRPS(input: String): String {
            println(input)
            var stack = ArrayDeque<String>()
            var answer = ""
            var charNow: Char
            var isString = false
            var isFunc = false
            for (i in 0 until input.length) {
                charNow = input[i]
                if (charNow.toString().matches("[a-zA-Z0-9\"]".toRegex()) || isString) {
                    answer += charNow
                    if (charNow == '"') isString = !isString
                    if (i < input.length - 1 && input[i + 1] == '(') isFunc = true
                } else if (charNow != ' ' && !(i > 0 && input[i - 1].toString()
                        .matches("[<>=!]".toRegex()) && input[i] == '=')
                ) {
                    if (answer.length > 0 && answer[answer.length - 1] != ' ') answer += " "
                    when (charNow) {
                        ')' -> {
                            while (stack.size > 0 && stack.last() != "(") {
                                answer += (stack.last() + " ")
                                stack.removeLast()
                            }
                            if (isFunc) answer += "Function"
                            isFunc = false
                            stack.removeLast()
                        }//[[1,2],[3,4]][0]
                        ']' -> {
                            while (stack.size > 0 && (stack.last() != "Array" && stack.last() != "index")) {
                                answer += (stack.last() + " ")
                                stack.removeLast()
                            }
                            answer += stack.last()
                            stack.removeLast()
                        }

                        '|', '&', ',' -> {
                            while (stack.size > 0 && stack.last()
                                    .matches("[+/*%<>=|&-]+".toRegex())
                            ) {
                                answer += (stack.last() + " ")
                                stack.removeLast()
                            }
                            stack.addLast(charNow.toString())
                        }

                        '<', '>', '=', '!' -> {
                            while (stack.size > 0 && stack.last()
                                    .matches("[+/*%<>=-]+".toRegex())
                            ) {
                                answer += (stack.last() + " ")
                                stack.removeLast()
                            }
                            if (input[i + 1] == '=') stack.addLast(charNow + "=")
                            else stack.addLast(charNow.toString())
                        }

                        '+', '-' -> {
                            if (input[i - 1] == '(') {
                                answer += charNow
                            } else {
                                while (stack.size > 0 && stack.last()
                                        .matches("[+/*%-]".toRegex())
                                ) {
                                    answer += (stack.last() + " ")
                                    stack.removeLast()
                                }
                                stack.addLast(charNow.toString())
                            }
                        }

                        '[' -> {
                            if (i == 0 || input[i - 1].toString()
                                    .matches("[,\\(\\[]".toRegex())
                            ) stack.addLast("Array")
                            else stack.addLast("index")
                        }

                        else -> stack.addLast(charNow.toString())
                    }
                }
            }

            while (stack.size > 0) {
                if (answer[answer.length - 1] != ' ') answer += " "
                answer += (stack.last())
                stack.removeLast()
            }
            return answer
        }

        private fun booleanToInt(input: Boolean): Int {
            return if (input) 1 else 0
        }

        @OptIn(ExperimentalSerializationApi::class)
        @JvmStatic
        fun fromRPS(dictionary: MutableMap<String, Variable>, input: String): Variable {
            println(input)
            val listOfActions = input.split(' ')
            var stack = ArrayDeque<Variable>()
            for (i in listOfActions) {
                println("$stack $i")
                if (i.matches("([+/*%><|&=!,-]+)|(index)|(Function)|(Array)".toRegex())) {
                    val first = stack.last()
                    stack.removeLast()
                    var second = first
                    if (i != "Array") {
                        second = stack.last()
                        stack.removeLast()
                    }
                    when (i) {
                        "-" -> {
                            stack.addLast(
                                Variable(
                                    (second.value.toInt() - first.value.toInt()).toString(),
                                    "Int"
                                )
                            )
                        }

                        "+" -> {
                            if (first.type == "String" || second.type == "String") stack.addLast(
                                Variable((second.value + first.value), "String")
                            )
                            else stack.addLast(
                                Variable(
                                    (second.value.toInt() + first.value.toInt()).toString(),
                                    "Int"
                                )
                            )
                        }

                        "*" -> {
                            stack.addLast(
                                Variable(
                                    (second.value.toInt() * first.value.toInt()).toString(),
                                    "Int"
                                )
                            )
                        }

                        "/" -> {
                            stack.addLast(
                                Variable(
                                    (second.value.toInt() / first.value.toInt()).toString(),
                                    "Int"
                                )
                            )
                        }

                        "%" -> {
                            stack.addLast(
                                Variable(
                                    (second.value.toInt() % first.value.toInt()).toString(),
                                    "Int"
                                )
                            )
                        }

                        "|" -> {
                            stack.addLast(
                                Variable(
                                    (Translate.toBool(second) || Translate.toBool(
                                        first
                                    )).toString(), "Bool"
                                )
                            )
                        }

                        "&" -> {
                            stack.addLast(
                                Variable(
                                    (Translate.toBool(second) && Translate.toBool(
                                        first
                                    )).toString(), "Bool"
                                )
                            )
                        }

                        ">" -> {
                            if (second.type == "Int" && first.type == "Int") stack.addLast(
                                Variable(
                                    (second.value.toInt() > first.value.toInt()).toString(),
                                    "Bool"
                                )
                            )
                            else stack.addLast(
                                Variable(
                                    (second.value > first.value).toString(),
                                    "Bool"
                                )
                            )
                        }

                        "<" -> {
                            if (second.type == "Int" && first.type == "Int") stack.addLast(
                                Variable(
                                    (second.value.toInt() < first.value.toInt()).toString(),
                                    "Bool"
                                )
                            )
                            else stack.addLast(
                                Variable(
                                    (second.value < first.value).toString(),
                                    "Bool"
                                )
                            )
                        }

                        ">=" -> {
                            if (second.type == "Int" && first.type == "Int") stack.addLast(
                                Variable(
                                    (second.value.toInt() >= first.value.toInt()).toString(),
                                    "Bool"
                                )
                            )
                            else stack.addLast(
                                Variable(
                                    (second.value >= first.value).toString(),
                                    "Bool"
                                )
                            )
                        }

                        "<=" -> {
                            if (second.type == "Int" && first.type == "Int") stack.addLast(
                                Variable(
                                    (second.value.toInt() <= first.value.toInt()).toString(),
                                    "Bool"
                                )
                            )
                            else stack.addLast(
                                Variable(
                                    (second.value <= first.value).toString(),
                                    "Bool"
                                )
                            )
                        }

                        "==" -> stack.addLast(
                            Variable(
                                (second.value == first.value).toString(),
                                "Bool"
                            )
                        )

                        "!=" -> stack.addLast(
                            Variable(
                                (second.value != first.value).toString(),
                                "Bool"
                            )
                        )

                        "index" -> {
                            if (second.type == "String") stack.addLast(
                                Variable(
                                    second.value[first.value.toInt()].toString(),
                                    "String"
                                )
                            )
                            else stack.addLast(
                                Variable(
                                    Json.decodeFromString<List<String>>(second.value)[first.value.toInt()],
                                    "${"(?<=<)[A-Za-z]+(?=>)".toRegex().find(second.type)?.value}"
                                )
                            )
                        }

                        "," -> {
                            //нужно разделить массив и список аргументов
                            println(first)
                            if (first.type != "TempArray") {
                                stack.addLast(
                                    Variable(
                                        Json.encodeToString(
                                            mutableListOf(
                                                first.value,
                                                second.value
                                            )
                                        ), "TempArray"
                                    )
                                )
                            } else {
                                var value = Json.decodeFromString<MutableList<String>>(first.value)
                                value.add(second.value)
                                stack.addLast(Variable(Json.encodeToString(value), "TempArray"))
                            }
                        }

                        "Array" -> {
                            val value: List<String>
                            if (first.type == "TempArray") {
                                value = Json.decodeFromString<List<String>>(first.value).reversed()
                                stack.addLast(
                                    Variable(
                                        Json.encodeToString(value),
                                        "Array<${Translate.getType(value[0])}>"
                                    )
                                )
                            } else {
                                value = listOf(first.value)
                                stack.addLast(
                                    Variable(
                                        Json.encodeToString(value),
                                        "Array<${first.type}>"
                                    )
                                )
                            }

                        }

                        "Function" -> {
                            var values: List<String>
                            if (first.type == "TempArray") values =
                                Json.decodeFromString<MutableList<String>>(first.value).reversed()
                            else values = listOf(first.value)

                            val (indexOfActions, blocks) = second.value.split(";", limit = 2)
                            val arguments = Json.decodeFromString<List<String>>(
                                blocks.substring(
                                    0,
                                    indexOfActions.toInt()
                                )
                            )
                            val actions =
                                Json.decodeFromString<List<String>>(blocks.substring(indexOfActions.toInt() + 1))
                            val newDictionary = mutableMapOf<String, Variable>()
                            for (index in arguments.indices) {
                                if (index < values.size) {
                                    when (arguments[index][0]) {
                                        'i' -> {
                                            newDictionary[arguments[index].substring(1)
                                                .split(";")[1]] =
                                                Translate.getVariable(values[index])
                                        }

                                        '=' -> {
                                            newDictionary[arguments[index].substring(1)
                                                .split("=", limit = 2)[0]] =
                                                Translate.getVariable(values[index])
                                        }
                                    }
                                } else {
                                    val (variable, expression) = arguments[index].substring(1)
                                        .split("=", limit = 2)
                                    newDictionary[variable] = Translate.getVariable(expression)
                                }
                            }
                            for (item in actions) {
                                start(item, dictionary = newDictionary)
                                if (item[0] == 'r') break
                            }

                            if (newDictionary.containsKey("return")) stack.addLast(
                                Variable(
                                    newDictionary["return"]!!.value,
                                    second.type
                                )
                            )
                            else stack.addLast(Variable("Ты дебил?", "String"))
                        }
                    }
                } else if (i != " ") {
                    if (dictionary.containsKey(i)) stack.addLast(dictionary[i]!!)
                    else stack.addLast(Translate.getVariable(i))
                }
            }
            println(stack.first())
            return stack.first()
        }
    }
}