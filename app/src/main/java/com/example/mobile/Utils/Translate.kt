package com.example.mobile.Utils;

import com.example.mobile.Variable
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Translate {
    companion object{
        @JvmStatic
        public fun toBool(input: Variable): Boolean{
            if(input.type == "Int") return intToBoolean(input.value.toInt())
            else if(input.type == "String") return input.value.length > 0
            else return input.value.toBoolean()
        }
        @JvmStatic
        public fun intToBoolean(input:Int):Boolean{
            return input != 0
        }
        @OptIn(ExperimentalSerializationApi::class)
        @JvmStatic
        public fun getType(input: String):String{
            if("\"[^\"]+\"".toRegex().matchEntire(input)?.value != null) return "String"
            else if(input == "true" || input == "false") return "Bool"
            else if("\\[[\",\\w\\[\\]]+\\]".toRegex().matchEntire(input)?.value != null) return "Array<${getType(Json.decodeFromString<List<String>>(input)[0])}>"
            else if("-?[0-9]+".toRegex().matchEntire(input)?.value != null) return "Int"
            else return "Exception"
        }
        @JvmStatic
        public fun getVariable(input: String):Variable{
            val type = getType(input)
            if(type.matches("Exception".toRegex())) return Variable("WrongType", "Exception")
            return Variable(input, type)
        }
    }
}