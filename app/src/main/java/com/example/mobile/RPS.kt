package com.example.mobile

class RPS {
    companion object {
        fun toRPS(input: String): String {
            var stack = ArrayDeque<Char>()
            var answer = "";
            for (i in input) {
                if (i.toString().matches("[a-zA-Z0-9]".toRegex())) {
                    answer += i;
                } else if (i != ' ') {
                    if (answer[answer.length - 1] != ' ') answer += " ";
                    when (i) {
                        ')' -> {
                            while (stack.size > 0 && stack.last() != '(') {
                                answer += stack.last()
                                stack.removeLast()
                            }
                            stack.removeLast();
                        }

                        '+' -> {
                            while (stack.size > 0 && stack.last().toString()
                                    .matches("[+/*%-]".toRegex())
                            ) {
                                answer += (stack.last() + " ");
                                stack.removeLast()
                            }
                            stack.addLast('+')
                        }

                        '-' -> {
                            while (stack.size > 0 && stack.last().toString()
                                    .matches("[+/*%-]".toRegex())
                            ) {
                                answer += (stack.last() + " ");
                                stack.removeLast()
                            }
                            stack.addLast('-')
                        }

                        else -> stack.addLast(i)
                    }
                }
            }

            while (stack.size > 0) {
                if (answer[answer.length - 1] != ' ') answer += " ";
                answer += (stack.last())
                stack.removeLast()
            }
            return answer + " ";
        }

        fun fromRPS(dictionary: MutableMap<String, String>, input: String): String {
            var stack = ArrayDeque<String>()
            var numberNow = "";
            for (i in input) {
                if (i.toString().matches("[+/*-]".toRegex())) {
                    var first = stack.last();
                    if (first.matches("[a-zA-Z]".toRegex())) first = dictionary[first].toString()
                    stack.removeLast();

                    var second = stack.last();
                    if (second.matches("[a-zA-Z]".toRegex())) second = dictionary[second].toString()
                    stack.removeLast();

                    when (i) {
                        '-' -> stack.addLast((second.toInt() - first.toInt()).toString());
                        '+' -> stack.addLast((second.toInt() + first.toInt()).toString());
                        '*' -> stack.addLast((second.toInt() * first.toInt()).toString());
                        '/' -> stack.addLast((second.toInt() / first.toInt()).toString());
                        '%' -> stack.addLast((second.toInt() % first.toInt()).toString());
                    }
                } else {
                    if (i == ' ' && numberNow.length > 0) {
                        stack.addLast(numberNow)
                        numberNow = "";
                    };
                    else numberNow += i;

                }
            }
            return stack.first();
        }
    }
}