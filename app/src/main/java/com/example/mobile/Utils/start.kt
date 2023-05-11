package com.example.mobile.Utils

import com.example.mobile.RPS
import com.example.mobile.variables

fun start(item:String, lines:MutableList<String>){
    when(item[0]){
        '=' -> {
            val taskNow = item.substring(1).split("=", limit = 2);
            variables[taskNow[0]] = RPS.fromRPS(variables, RPS.toRPS(taskNow[1]))
        }
        '?' -> {
            var taskNow = item.substring(1).split(":", limit=2).toMutableList()
            val indexOfElse = Regex("-?[0-9]+").find(taskNow[0])!!.value.toInt()
            val condition = Regex("-?[0-9]+").replaceFirst(taskNow[0], "")
            if(indexOfElse != -1){
                val ifActions = taskNow[1].substring(0,indexOfElse-1).split(",");
                val elseActions = taskNow[1].substring(indexOfElse).split(",")
                println("${ifActions} ${elseActions}")
                if(RPS.calculate(variables, condition) == "1"){
                    ifActions.forEach { action ->
                        start(action,lines)
                    }
                }
                else{
                    elseActions.forEach { action ->
                        start(action,lines)
                    }
                }
            }
            else{
                if(RPS.calculate(variables, condition) == "1"){
                    val ifActions = taskNow[1].split(",")
                    ifActions.forEach { action ->
                        start(action,lines)
                    }
                }
            }
        }
        '/' -> {
            val taskNow = item.substring(1)
            lines.add(RPS.calculate(variables,taskNow))
        }
    }
}