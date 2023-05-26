package com.example.mobile

import com.example.mobile.Utils.Translate
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class RPS {
    companion object {

        @JvmStatic
        fun calculate(dictionary: MutableMap<String, Variable>, input: String): Variable {
            return fromRPS(dictionary, toRPS(input))
        }

        @JvmStatic
        fun toRPS(input: String): String {
            var stack = ArrayDeque<String>()
            var answer = ""
            var charNow: Char
            for (i in 0 until input.length) {
                charNow = input[i]
                if (charNow.toString().matches("[a-zA-Z0-9\"\"]".toRegex())) answer += charNow
                else if (charNow != ' ' && !(input[i - 1].toString()
                        .matches("[<>=!]".toRegex()) && input[i] == '=')
                ) {
                    if (answer[answer.length - 1] != ' ') answer += " "
                    when (charNow) {
                        ')' -> {
                            while (stack.size > 0 && stack.last() != "(") {
                                answer += (stack.last() + " ")
                                stack.removeLast()
                            }
                            stack.removeLast()
                        }

                        ']' -> {
                            while (stack.size > 0 && stack.last() != "[") {
                                answer += (stack.last() + " ")
                                stack.removeLast()
                            }
                            stack.removeLast()
                            answer += "index"
                        }

                        '|', '&' -> {
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

        @JvmStatic
        fun fromRPS(dictionary: MutableMap<String, Variable>, input: String): Variable {
            val listOfActions = input.split(' ')
            var stack = ArrayDeque<Variable>()
            for (i in listOfActions) {
                if (i.matches("[+/*%><|&=!-]+|index".toRegex())) {
                    val first = stack.last()
                    stack.removeLast()
                    val second = stack.last()
                    stack.removeLast()
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
                                    (second.value.toInt() - first.value.toInt()).toString(),
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
                    }
                } else if (i != " ") {
                    if (dictionary.containsKey(i)) stack.addLast(dictionary[i]!!)
                    else stack.addLast(Variable(i, Translate.getType(i)))
                }
            }
            return if (dictionary.containsKey(stack.first().value)) dictionary[stack.first().value]!! else stack.first()

        }
    }
}