package com.example.mobile

class RPS {
    companion object{

        @JvmStatic
        public fun calculate(dictionary: MutableMap<String, String>, input: String): String{
            return fromRPS(dictionary, toRPS(input))
        }
        @JvmStatic
        public fun toRPS(input: String): String{
            var stack = ArrayDeque<String>()
            var answer = "";
            var charNow:Char;
            for(i in 0 until input.length){
                charNow = input[i]
                if(charNow.toString().matches("[a-zA-Z0-9]+".toRegex())) answer+=charNow;
                else if(charNow != ' ' && !(input[i-1].toString().matches("[<>=]".toRegex()) && input[i] == '=')){
                    if(answer[answer.length-1] != ' ') answer+=" ";
                    when(charNow){
                        ')' -> {
                            while(stack.size > 0 && stack.last() != "("){
                                answer+=stack.last()
                                stack.removeLast()
                            }
                            stack.removeLast();
                        }
                        '|', '&' -> {
                            while(stack.size > 0 && stack.last().matches("[+/*%<>=|&-]+".toRegex())) {
                                answer+=(stack.last() + " ");
                                stack.removeLast()
                            }
                            stack.addLast(charNow.toString())
                        }
                        '<','>','=' -> {
                            while(stack.size > 0 && stack.last().matches("[+/*%<>=-]+".toRegex())) {
                                answer+=(stack.last() + " ");
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
                                    answer+=(stack.last() + " ");
                                    stack.removeLast()
                                }
                                stack.addLast(charNow.toString())
                            }
                        }
                        else -> stack.addLast(charNow.toString())
                    }
                }
            }

            while(stack.size > 0){
                if(answer[answer.length-1] != ' ') answer+=" ";
                answer+=(stack.last())
                stack.removeLast()
            }
            return answer;
        }

        private fun booleanToInt(input:Boolean):Int{
            return if (input) 1 else 0;
        }

        @JvmStatic
        public fun fromRPS(dictionary: MutableMap<String, String>, input: String) : String{
            val listOfActions = input.split(' ');
            var stack = ArrayDeque<String>()
            for(i in listOfActions){
                if(i.matches("[+/*%><|&=-]+".toRegex())){
                    var first = stack.last();
                    if(first.matches("[a-zA-Z]+".toRegex())) first = dictionary[first].toString()
                    stack.removeLast();

                    var second = stack.last();
                    if(second.matches("[a-zA-Z]+".toRegex())) second = dictionary[second].toString()
                    stack.removeLast();
                    when(i){
                        "-" -> stack.addLast((second.toInt() - first.toInt()).toString());
                        "+" -> stack.addLast((second.toInt() + first.toInt()).toString());
                        "*" -> stack.addLast((second.toInt() * first.toInt()).toString());
                        "/" -> stack.addLast((second.toInt() / first.toInt()).toString());
                        "%" -> stack.addLast((second.toInt() % first.toInt()).toString());
                        "|" -> stack.addLast(booleanToInt(second.toInt() != 0 || first.toInt() != 0).toString());
                        "&" -> stack.addLast(booleanToInt(second.toInt() != 0 && first.toInt() != 0).toString());
                        ">" -> stack.addLast(booleanToInt(second.toInt() > first.toInt()).toString());
                        "<" -> stack.addLast(booleanToInt(second.toInt() < first.toInt()).toString());
                        ">=" -> stack.addLast(booleanToInt(second.toInt() >= first.toInt()).toString());
                        "<=" -> stack.addLast(booleanToInt(second.toInt() <= first.toInt()).toString());
                        "==" -> stack.addLast(booleanToInt(second.toInt() == first.toInt()).toString());
                    }
                }
                else stack.addLast(i)
            }
            return if (variables.containsKey(stack.first())) variables[stack.first()]!! else stack.first()

        }
    }
}