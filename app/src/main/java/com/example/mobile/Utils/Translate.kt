package com.example.mobile.Utils;

import com.example.mobile.Variable

class Translate {
    companion object{
        @JvmStatic
        public fun toBool(input: Variable): Boolean{
            if(input.type == "Int") return intToBoolean(input.value.toInt())
            else return input.value.toBoolean()
        }

        @JvmStatic
        public fun booleanToInt(input:Boolean):Int{
            return if (input) 1 else 0
        }

        @JvmStatic
        public fun intToBoolean(input:Int):Boolean{
            return input != 0
        }
        @JvmStatic
        public fun getType(input: String):String{
            if(input[0] == '"')return "String"
            else if(input == "true" || input == "false") return "Bool"
            else return "Int"
        }
        @JvmStatic
        public fun getVariable(input: String):Variable{
            if(input[0] == '"'){
                return Variable(input.substring(1,input.length-1),"String")
            }
            else if(input == "true" || input == "false"){
                return Variable(input,"Bool")
            }
            else return Variable(input,"Int")
        }
    }
}